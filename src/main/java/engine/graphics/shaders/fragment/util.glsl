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
    return vec3(dimensions[index * 3], dimensions[index * 3 + 1], dimensions[index * 3 + 2]);
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