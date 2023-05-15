package engine.physics.body;

import engine.physics.collider.Collider;

public abstract class CollisionBody
{
    private Collider collider;

    public CollisionBody(Collider collider)
    {
        this.collider = collider;
    }

    public Collider getCollider()
    {
        return collider;
    }

    public void setCollider(Collider collider)
    {
        this.collider = collider;
    }

    public abstract void update();
}