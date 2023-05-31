package engine.physics.collider;

import engine.math.vector.Vector3;
import engine.physics.shape.Box;

public class OBBCollider extends Box
{

    public OBBCollider(Vector3 position, Vector3 dimensions)
    {
        super(position, dimensions);
    }
}