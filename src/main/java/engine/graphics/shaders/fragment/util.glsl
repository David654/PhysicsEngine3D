uvec4 R_STATE;

vec3 rotateX(in vec3 v, float theta)
{
    float sin = sin(theta);
    float cos = cos(theta);
    return v *= mat3(1, 0, 0, 0, cos, -sin, 0, sin, cos);
}

vec3 rotateY(in vec3 v, float theta)
{
    float sin = sin(theta);
    float cos = cos(theta);
    return v *= mat3(cos, 0, sin, 0, 1, 0, -sin, 0, cos);
}

vec3 rotateZ(in vec3 v, float theta)
{
    float sin = sin(theta);
    float cos = cos(theta);
    return v *= mat3(cos, -sin, 0, sin, cos, 0, 0, 0, 1);
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

bool isMonochromatic(vec3 color)
{
    vec3 red = vec3(1, 0, 0);
    vec3 orange = vec3(1, 0.65, 0);
    vec3 yellow = vec3(1, 1, 0);
    vec3 green = vec3(0, 1, 0);
    vec3 cyan = vec3(0, 1, 1);
    vec3 blue = vec3(0, 0, 1);
    vec3 indigo = vec3(0.21, 0.11, 0.46);
    vec3 violet = vec3(0.13, 0.07, 0.3);

    if(all(equal(color, red)) || all(equal(color, orange)) || all(equal(color, yellow)) || all(equal(color, green)) || all(equal(color, cyan)) || all(equal(color, blue)) || all(equal(color, indigo)) || all(equal(color, violet)))
    {
        return true;
    }
    return false;
}

int getBodyID(int index)
{
    return ids[index];
}

vec3 getBodyPosition(int index)
{
    return vec3(positions[index * 3], positions[index * 3 + 1], positions[index * 3 + 2]);
}

vec3 getBodyDimensions(int index)
{
    //return vec3(dimensions[index * 3], dimensions[index * 3 + 1], dimensions[index * 3 + 2]);
    return vec3(0.5, 0.5, 0.5);
}

vec3 getBodyColor(int index)
{
    return vec3(colors[index * 3], colors[index * 3 + 1], colors[index * 3 + 2]);
}

float getBodyDiffuse(int index)
{
    return diffuses[index];
}

float getBodyRefraction(int index)
{
    return refractions[index];
}