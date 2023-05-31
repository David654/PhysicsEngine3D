const float PI = 3.1415926;
const float MAX_DIST = 999999;
const int MAX_BODY_COUNT = 10;

vec3 lightPosition = vec3(0, 0, 0);


uniform Body bodies[MAX_BODY_COUNT];

/**
= Body[](
    //Body(SPHERE, vec3(-3, 1.2, 0), vec3(1, 1, 1), vec3(1, 0, 0)),
    //Body(SPHERE, vec3(0, 1.2, 0), vec3(1, 1, 1), vec3(0, 1, 0)),
    //Body(SPHERE, vec3(3, 1.2, 0), vec3(1, 1, 1), vec3(0, 0, 1)),
    //Body(BOX, vec3(0, 0, 0), vec3(4, 0.2, 2), vec3(0.3, 0.3, 0.3)),
    //Body(BOX, vec3(-2, -2, 0), vec3(4, 0.2, 2), vec3(0.3, 0.3, 0.3))
    Body(BOX, vec3(-7, 0, 0), vec3(4, 4, 2), vec3(0.86, 0.34, 0.34)),
    Body(BOX, vec3(4, 0, 0), vec3(0.5, 4, 2), vec3(0.86, 0.34, 0.34)),
    Body(SPHERE, vec3(-5, 0, -3), vec3(1, 1, 1), vec3(0.41, 0.86, 0.34))
);
*/

uniform vec2 uResolution;
uniform float uTime;
uniform vec2 uMousePosition;

uniform vec3 uPosition;
uniform float uFOV;
uniform float uMaxDist;

uniform sampler2D uBackgroundTexture;
uniform sampler2D uPreviousFrame;

uniform vec2 uSeed1;
uniform vec2 uSeed2;