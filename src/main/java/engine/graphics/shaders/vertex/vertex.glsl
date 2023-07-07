#version 130

in vec2 position;

out vec3 color;

//uniform mat4 transformationMatrix;

void main()
{
    gl_Position = vec4(position, 0, 1.0);
    color = vec3(position.x + 0.5, 0, position.y + 0.5);
}