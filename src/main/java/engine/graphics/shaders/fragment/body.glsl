const int SPHERE = 1;
const int BOX = 2;

struct Material
{
    vec3 color;
    float diffuse;
    float refraction;
};

struct Body
{
    int id;
    int lightID;
    vec3 position;
    vec3 dimensions;
    Material material;
};