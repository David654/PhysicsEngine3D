#version 330

out vec4 fragColor;

uniform vec2 uResolution;
uniform float uTime;

void main()
{
    vec2 uv = gl_FragCoord.xy / uResolution.xy;

    vec3 col = 0.5 + 0.5 * cos(uTime + uv.xyx + vec3(0, 2, 4));

    fragColor = vec4(col, 1.0);
}