#version 420

in vec3 pos;
in vec3 color;

out vec4 fragColor;

void main()
{
    fragColor = vec4(vec3(pos.x, 1, pos.y), 1);
}