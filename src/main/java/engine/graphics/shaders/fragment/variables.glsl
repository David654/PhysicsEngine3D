const float PI = 3.1415926;
const float MAX_DIST = 999999;
const int MAX_BODY_COUNT = 1000;

vec3 lightPosition = vec3(0, 0, 0);

layout(binding = 3) uniform uBodyIDBlock{int ids[MAX_BODY_COUNT];};
layout(binding = 4) uniform uBodyPositionBlock{float positions[MAX_BODY_COUNT * 3];};
layout(binding = 5) uniform uBodyDimensionBlock{float dimensions[MAX_BODY_COUNT * 3];};
layout(binding = 0) uniform uBodyColorBlock{float colors[MAX_BODY_COUNT * 3];};
layout(binding = 1) uniform uBodyDiffuseBlock{float diffuses[MAX_BODY_COUNT];};
layout(binding = 2) uniform uBodyRefractionBlock{float refractions[MAX_BODY_COUNT];};

uniform Body bodies[100];

uniform int uNumBodies;
uniform int lightID;

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
uniform vec2 uMouseClick;
uniform int uSelectedBodyIndex;

uniform vec3 uPosition;
uniform float uFOV;
uniform float uMaxDist;

uniform sampler2D uBackgroundTexture;
uniform sampler2D uSample;
uniform float uSamplePart;

uniform vec2 uSeed1;
uniform vec2 uSeed2;