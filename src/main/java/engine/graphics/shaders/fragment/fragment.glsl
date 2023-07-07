#version 420

#include ray.glsl
#include body.glsl
#include variables.glsl
#include util.glsl
#include primitives.glsl
#include raycasting.glsl
#include lighting.glsl

out vec4 fragColor;
in vec2 texCoord;

const int maxReflectionNum = 20;

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
    //return vec3(0, 1, 1);
}

vec3 traceRay(in Ray ray, vec3 color)
{
    for(int i = 0; i < maxReflectionNum; i++)
    {
        vec3 second = castRay(ray);
        vec2 intersection = second.xy;
        vec3 intersectionPoint = ray.ro + ray.rd * intersection.x;
        int index = int(second.z);

        if(index == -1)
        {
            return color * getSky(ray.rd);
        }

        if(bodies[index].lightID == 1)
        {
            return color;
        }

        //Body body = bodies[index];

        float diffuse = getBodyDiffuse(index);
        float refraction = getBodyRefraction(index);

        Body body = Body(getBodyID(index), 0, getBodyPosition(index), getBodyDimensions(index), Material(getBodyColor(index), getBodyDiffuse(index), getBodyRefraction(index)));

        vec3 n = getNormal(ray.ro, ray.rd, intersectionPoint, body);

        vec3 rand = randomOnSphere();
        vec3 spec = reflect(ray.rd, n);
        vec3 diff = normalize(rand * dot(rand, n));
        //vec3 diff = n;

        if(refraction > 0)
        {
            float fresnel = 1.0 - abs(dot(-ray.rd, n));
            float angle = asin(1.000273 / refraction);

            if(fresnel >= angle)
            {
                ray.rd = spec;
            }
            else
            {
                float rayRefraction = refraction;

                if(!isMonochromatic(color))
                {
                    float redRefraction = refraction * 1.00611333;
                    float greenRefraction = refraction * 1.01022667;
                    float blueRefraction = refraction * 1.01126667;

                    int index = int(random() * 3);
                    float[3] refractions = float[3](redRefraction, greenRefraction, blueRefraction);
                    rayRefraction = refractions[index];

                    vec3[3] colors = vec3[3](vec3(color.r, 0, 0), vec3(0, color.g, 0), vec3(0, 0, color.b));
                    //color = colors[index];
                }

                ray.ro += ray.rd * (intersection.y + 0.001);
                ray.rd = mix(diff, spec, diffuse);
                ray.rd = refract(ray.rd, n, 1.0 / (1.0 + rayRefraction));
            }
            //color * getLight(ro, rd, lightPosition, second);
        }
        else
        {
            ray.ro += ray.rd * (intersection.x - 0.001);
            //rd = reflect(rd, n);

            ray.rd = mix(diff, spec, diffuse);
        }

        color *= getLight(ray.ro, ray.rd, lightPosition, second);
    }

    return vec3(0);
}

void initBodies()
{
    for(int i = 0; i < uNumBodies; i++)
    {
        //bodies[i] = Body(ids[i], getBodyPosition(i), getBodyDimensions(i), Material(getBodyColor(i), diffuses[i], refractions[i]));
    }
}

vec3 render(in vec2 uv)
{
    vec3 ro = uPosition;

    //vec3 lookAt = vec3(0);
    //vec3 rd = getCam(ro, lookAt) * normalize(vec3(uv, uFOV));
    //vec3 rd = normalize(vec3(uFOV, uv));
    vec3 rd = normalize(vec3(uFOV, uv));

    rd = mouseControl(rd).yzx;

    float lensResolution = 10.0;
    float focalLenght = 20.0;
    float lensAperture = 0.6;
    float shiftIteration = 0.0;
    float inc = 1.0 / lensResolution;
    float start = inc / 2.0 - 0.5;

    vec3 focalPoint = ro + rd * focalLenght;

    initBodies();

    vec3 outColor;

   /* for (float stepX = start; stepX < 0.5; stepX += inc)
    {
        for (float stepY = start; stepY < 0.5; stepY += inc)
        {
            vec2 shiftedOrigin = vec2(stepX, stepY) * lensAperture;

            if(length(shiftedOrigin) < (lensAperture / 2.0))
            {
                vec3 shiftedRayOrigin = ro;
                shiftedRayOrigin.x += shiftedOrigin.x;
                shiftedRayOrigin.y += shiftedOrigin.y;
                vec3 shiftedRayDirection = normalize(focalPoint - shiftedRayOrigin);

                Ray ray = Ray(shiftedRayOrigin, shiftedRayDirection);
                vec3 rayCast = castRay(ray);
                vec2 intersection = rayCast.xy;
                vec3 intersectionPoint = ray.ro + ray.rd * intersection.x;
                int index = int(rayCast.z);
                Body body = bodies[index];

                if(index == -1)
                {
                    return getSky(ray.rd);
                }

                //outColor = getLight(ro, rd, lightPosition, rayCast);

                vec2 uvRes = hash22(uv + 1.0) * uResolution + uResolution;
                R_STATE.x = uint(uSeed1.x + uvRes.x);
                R_STATE.y = uint(uSeed1.y + uvRes.x);
                R_STATE.z = uint(uSeed2.x + uvRes.y);
                R_STATE.w = uint(uSeed2.y + uvRes.y);

                vec3 inColor = getBodyColor(index);

                if(body.id == 2)
                {
                    float chessboard = floor(intersectionPoint.x) + floor(intersectionPoint.y) + floor(intersectionPoint.z);
                    chessboard = fract(chessboard * 0.5);
                    chessboard *= 2;
                    //inColor = vec3(chessboard);
                }

                outColor += traceRay(ray, inColor);
                shiftIteration++;
            }
        }
    }

    outColor = sqrt(clamp(outColor / shiftIteration, 0, 1));**/

    Ray ray = Ray(ro, rd);
    vec3 rayCast = castRay(ray);
    vec2 intersection = rayCast.xy;
    vec3 intersectionPoint = ray.ro + ray.rd * intersection.x;
    int index = int(rayCast.z);
    Body body = bodies[index];

    if(index == -1)
    {
        return getSky(ray.rd);
    }

    //outColor = getLight(ro, rd, lightPosition, rayCast);

    vec2 uvRes = hash22(uv + 1.0) * uResolution + uResolution;
    R_STATE.x = uint(uSeed1.x + uvRes.x);
    R_STATE.y = uint(uSeed1.y + uvRes.x);
    R_STATE.z = uint(uSeed2.x + uvRes.y);
    R_STATE.w = uint(uSeed2.y + uvRes.y);

    vec3 inColor = getBodyColor(index);

    if(body.id == 2)
    {
        float chessboard = floor(intersectionPoint.x) + floor(intersectionPoint.y) + floor(intersectionPoint.z);
        chessboard = fract(chessboard * 0.5);
        chessboard *= 2;
        //inColor = vec3(chessboard);
    }

    outColor += traceRay(ray, inColor);

    if(int(uSelectedBodyIndex) == index)
    {
        outColor = mix(outColor, vec3(1, 0.46, 0.09), 0.5);
        //return vec3(1, 0.46, 0.09);
    }

    outColor = gammaCorrection(outColor);

    /*if(uMouseClick.x == uv.x)
    {
        if(index != -1)
        {
            //outColor = mix(outColor, vec3(1, 0.46, 0.09), 0.5);
        }
    }**/

    vec2 sampleUV = gl_FragCoord.xy / uResolution.xy;
    vec3 sampleCol = texture(uSample, sampleUV).rgb;
    outColor = mix(sampleCol, outColor, uSamplePart);

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
    vec3 col = renderAAx1();
    fragColor = vec4(col, 1.0);
}