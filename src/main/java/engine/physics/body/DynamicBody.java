package engine.physics.body;

import engine.math.vector.Vector3;
import engine.physics.collider.Collider;

public class DynamicBody extends CollisionBody
{
    private Vector3 position;
    private Vector3 velocity;
    private double mass;
    private Vector3 force;

    public DynamicBody(Collider collider)
    {
        super(collider);
    }

    public void update()
    {

    }
}