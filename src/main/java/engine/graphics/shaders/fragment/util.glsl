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