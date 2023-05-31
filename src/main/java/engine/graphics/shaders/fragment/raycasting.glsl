vec3 castRay(vec3 ro, vec3 rd)
{
    vec2 minIntersection = vec2(MAX_DIST);
    vec2 intersection;
    vec3 intersectionPoint;
    float index = -1;

    vec3 outColor = vec3(-1);

    for(int i = 0; i < bodies.length(); i++)
    {
        Body body = bodies[i];
        vec3 n;

        if(body.id == SPHERE)
        {
            intersection = sphereIntersect(ro - body.position, rd, body.dimensions.x);
        }

        if(body.id == BOX)
        {
            intersection = boxIntersection(ro - body.position, rd, body.dimensions, n);
        }

        if(intersection.x > 0.0 && intersection.x < minIntersection.x)
        {
            minIntersection = intersection;
            intersectionPoint = ro + rd * intersection.x;
            index = i;

            if(body.id == SPHERE)
            {
                n = normalize(intersectionPoint - body.position);
            }

            //outColor = getLight(intersectionPoint, rd, n, lightPosition, body.color, step(castRay(intersectionPoint + n * 0.001, normalize(lightPosition - intersectionPoint)).y, 0));

            //break;
        }
    }

    return vec3(minIntersection, index);
}