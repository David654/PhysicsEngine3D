vec3 castRay(Ray ray)
{
    vec2 minIntersection = vec2(MAX_DIST);
    vec2 intersection;
    vec3 intersectionPoint;
    float index = -1;

    vec3 outColor = vec3(-1);

    for(int i = 0; i < uNumBodies; i++)
    {
        Body body = bodies[i];
        vec3 n;
        int id = getBodyID(i);
        vec3 position = getBodyPosition(i);

        if(id == SPHERE)
        {
            intersection = sphereIntersect(ray.ro - position, ray.rd, getBodyDimensions(i).x);
        }

        if(id == BOX)
        {
            intersection = boxIntersection(ray.ro - position, ray.rd, getBodyDimensions(i), n);
        }

        if(intersection.x > 0.0 && intersection.x < minIntersection.x)
        {
            minIntersection = intersection;
            intersectionPoint = ray.ro + ray.rd * intersection.x;
            index = i;

            if(id == SPHERE)
            {
                n = normalize(intersectionPoint - position);
            }

            //outColor = getLight(intersectionPoint, ray.rd, n, lightPosition, body.color, step(castRay(intersectionPoint + n * 0.001, normalize(lightPosition - intersectionPoint)).y, 0));

            //break;
        }
    }

    return vec3(minIntersection, index);
}