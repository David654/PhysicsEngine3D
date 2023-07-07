#version 420

in vec3 position;

out vec3 pos;
out vec3 color;

uniform mat4 transformationMatrix;

void main()
{
    gl_Position = transformationMatrix * vec4(position, 1);
    pos = gl_Position.xyz;
    color = vec3(position.x, 1, position.y);
}