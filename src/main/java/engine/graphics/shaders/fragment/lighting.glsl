vec3 getNormal(vec3 ro, vec3 rd, vec3 intersectionPoint, Body body)
{
    vec3 n;

    if(body.id == SPHERE)
    {
        n = normalize(intersectionPoint - body.position);
    }

    if(body.id == BOX)
    {
        vec2 i = boxIntersection(ro - body.position, rd, body.dimensions, n);
    }

    return n;
}

vec3 getLight(vec3 ro, vec3 rd, vec3 lightPos, vec3 rayCast)
{
    vec2 intersection = rayCast.xy;
    vec3 intersectionPoint = ro + rd * intersection.x;
    int index = int(rayCast.z);
    Body body = bodies[index];

    vec3 outColor = vec3(0, 0, 0);
    vec3 V = -rd;
    vec3 N = getNormal(ro, rd, intersectionPoint, body);

    vec3 L = normalize(lightPos);
    vec3 R = reflect(-L, N);
    vec3 H = normalize(L + V);

    vec3 ambient = body.material.color * 0.1;
    vec3 diffuse = body.material.color * clamp(dot(L, N), 0.0, 1.0);
    vec3 specColor = vec3(0.1);
    vec3 specular = specColor * pow(clamp(dot(R, V), 0.0, 1.0), 256.0); // clamp(dot(R, V), 0.0, 1.0)

    // shadows
    float shadow = step(castRay(intersectionPoint + N * 0.001, L).z, 0);

    // ambient occlusion
    float occ = 1;

    // reflections
    vec3 back = 0.05 * body.material.color * clamp(dot(N, -L), 0.0, 1.0);

    //finalCol = mix(finalCol, (ambient + fresnel + back) * occ + (diffuse + specular * occ), 1); // * shadow
    outColor += (ambient + back) * occ + (diffuse + specular * occ) * 1; // * shadow

    if(dot(N, L) > 0)
    {
        if(castRay(intersectionPoint, L).z != -1)
        {
            outColor *= 0.5;
        }
    }

    return body.material.color;
}

vec3 gammaCorrection(vec3 col)
{
    return pow(col, vec3(0.4545));
}