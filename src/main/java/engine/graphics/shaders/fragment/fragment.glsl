#version 130

#include body.glsl
#include variables.glsl
#include util.glsl
#include primitives.glsl
#include raycasting.glsl
#include lighting.glsl

out vec4 fragColor;
in vec2 texCoord;

int samples = 100;
uvec4 R_STATE;

vec3 mouseControl(in vec3 rd)
{
    rd = rotateY(rd, uMousePosition.y);
    rd = rotateZ(rd, uMousePosition.x);
    return rd;
}

mat3 getCam(vec3 ro, vec3 lookAt)
{
    vec3 camF = normalize(vec3(lookAt - ro));
    vec3 camR = normalize(cross(vec3(0, 1, 0), camF));
    vec3 camU = cross(camF, camR);
    return mat3(camR, camU, camF);
}

vec3 getSky(vec3 rd)
{
    rd = rotateX(rd, PI / 2);
    rd = rotateZ(rd, 3 * PI / 2);

    vec2 skyUV = vec2(atan(rd.x, rd.y), asin(rd.z) * 2.0) / PI * 0.5 + 0.5;

    vec3 col = texture(uBackgroundTexture, skyUV).rgb; //0.13, 0.13, 0.13
    vec3 sun = vec3(0.95, 0.9, 1.0);
    sun *= max(0.0, pow(dot(rd, normalize(lightPosition)), 256));
    return clamp(sun + col, 0.0, 1.0);
}

uint TausStep(uint z, int S1, int S2, int S3, uint M)
{
    uint b = (((z << S1) ^ z) >> S2);
    return (((z & M) << S3) ^ b);
}

uint LCGStep(uint z, uint A, uint C)
{
    return (A * z + C);
}

vec2 hash22(vec2 p)
{
    p += uSeed1.x;
    vec3 p3 = fract(vec3(p.xyx) * vec3(.1031, .1030, .0973));
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.xx + p3.yz) * p3.zy);
}

float random()
{
    R_STATE.x = TausStep(R_STATE.x, 13, 19, 12, uint(4294967294));
    R_STATE.y = TausStep(R_STATE.y, 2, 25, 4, uint(4294967288));
    R_STATE.z = TausStep(R_STATE.z, 3, 11, 17, uint(4294967280));
    R_STATE.w = LCGStep(R_STATE.w, uint(1664525), uint(1013904223));
    return 2.3283064365387e-10 * float((R_STATE.x ^ R_STATE.y ^ R_STATE.z ^ R_STATE.w));
}

vec3 randomOnSphere()
{
    vec3 rand = vec3(random(), random(), random());
    float theta = rand.x * 2.0 * 3.14159265;
    float v = rand.y;
    float phi = acos(2.0 * v - 1.0);
    float r = pow(rand.z, 1.0 / 3.0);
    float x = r * sin(phi) * cos(theta);
    float y = r * sin(phi) * sin(theta);
    float z = r * cos(phi);
    return vec3(x, y, z);
}

vec3 traceRay(inout vec3 ro, inout vec3 rd, vec3 color)
{
    for(int i = 0; i < samples; i++)
    {
        vec3 second = castRay(ro, rd);
        vec2 intersection = second.xy;
        vec3 intersectionPoint = ro + rd * intersection.x;
        int index = int(second.z);

        if(index == -1)
        {
            return color * getSky(rd);
        }

        Body body = bodies[index];

        vec3 n = getNormal(ro, rd, intersectionPoint, body);

        vec3 rand = randomOnSphere();
        vec3 spec = reflect(rd, n);
        vec3 diff = normalize(rand * dot(rand, n));

        if(body.material.refraction > 0)
        {
            float fresnel = 1.0 - abs(dot(-rd, n));
            float angle = asin(1.000273 / body.material.refraction);

            if(fresnel >= angle)
            {
                rd = spec;
            }
            else
            {
                ro += rd * (intersection.y + 0.001);
                rd = mix(diff, spec, body.material.diffuse);
                rd = refract(rd, n, 1.0 / (1.0 + body.material.refraction));
            }
            //color * getLight(ro, rd, lightPosition, second);
        }
        else
        {
            ro += rd * (intersection.x - 0.001);
            //rd = reflect(rd, n);

            rd = mix(diff, spec, body.material.diffuse);
        }

        color *= getLight(ro, rd, lightPosition, second);
    }

    return vec3(0);
}

vec3 render(in vec2 uv)
{
    vec3 ro = uPosition;

    //vec3 lookAt = vec3(0);
    //vec3 rd = getCam(ro, lookAt) * normalize(vec3(uv, uFOV));
    vec3 rd = normalize(vec3(uFOV, uv));
    rd = mouseControl(rd).yzx;

    //lightPosition.x = sin(uTime) * 10;
    //lightPosition.y = cos(uTime) * 10;

    vec3 outColor;
    vec3 rayCast = castRay(ro, rd);
    vec2 intersection = rayCast.xy;
    vec3 intersectionPoint = ro + rd * intersection.x;
    int index = int(rayCast.z);
    Body body = bodies[index];

    if(index == -1)
    {
        return getSky(rd);
    }

    //outColor = getLight(ro, rd, lightPosition, rayCast);

    vec2 uvRes = hash22(uv + 1.0) * uResolution + uResolution;
    R_STATE.x = uint(uSeed1.x + uvRes.x);
    R_STATE.y = uint(uSeed1.y + uvRes.x);
    R_STATE.z = uint(uSeed2.x + uvRes.y);
    R_STATE.w = uint(uSeed2.y + uvRes.y);

    outColor = traceRay(ro, rd, body.material.color);

    vec3 sampleCol = texture(uPreviousFrame, rd.xy).rgb;
    outColor = mix(sampleCol, outColor, 1);

    return outColor;
}

vec2 getUV(vec2 offset)
{
    return (2.0 * gl_FragCoord.xy + offset - uResolution.xy) / uResolution.y;
}

vec3 renderAAx1()
{
    return render(getUV(vec2(0)));
}

vec3 renderAAx2()
{
    float bxy = int(gl_FragCoord.x + gl_FragCoord.y) & 1;
    float nbxy = 1. - bxy;
    vec3 colAA = (render(getUV(vec2(0.33 * nbxy, 0.))) + render(getUV(vec2(0.33 * bxy, 0.66))));
    return colAA / 2.0;
}

vec3 renderAAx3()
{
    float bxy = int(gl_FragCoord.x + gl_FragCoord.y) & 1;
    float nbxy = 1. - bxy;
    vec3 colAA = (render(getUV(vec2(0.66 * nbxy, 0.))) + render(getUV(vec2(0.66 * bxy, 0.66))) + render(getUV(vec2(0.33, 0.33))));
    return colAA / 3.0;
}

vec3 renderAAx4()
{
    vec4 e = vec4(0.125, -0.125, 0.375, -0.375);
    vec3 colAA = render(getUV(e.xz)) + render(getUV(e.yw)) + render(getUV(e.wx)) + render(getUV(e.zy));
    return colAA /= 4.0;
}

void main()
{
    vec3 col = renderAAx4();
    col = gammaCorrection(col);

    fragColor = vec4(col, 1.0);
}