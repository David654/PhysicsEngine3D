package engine.physics.body;

import engine.math.vector.Vector3;

import java.util.ArrayList;

public class BodyHandler extends ArrayList<Body>
{
    public BodyHandler()
    {

    }

    public int getNumActiveBodies()
    {
        int numActiveBodies = 0;

        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            if(body.isActive())
            {
                numActiveBodies++;
            }
        }
        return numActiveBodies;
    }

    public Body[] getActiveBodies()
    {
        Body[] bodies = new Body[getNumActiveBodies()];
        int index = 0;

        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            if(body.isActive())
            {
                bodies[index] = body;
                index++;
            }
        }
        return bodies;
    }

    public float[] getBodyPositions()
    {
        float[] floatArray = new float[1000 * 1000];
        Body[] bodies = getActiveBodies();
        int index = 0;

        for(int i = 0; i < bodies.length; i++)
        {
            Body body = bodies[i];
            Vector3 position = body.getPosition().multiply(0.000001);
            floatArray[index] = (float) position.getX();
            floatArray[index + 1] = (float) position.getX();
            floatArray[index + 2] = (float) position.getX();
            index += 3;
        }

        return floatArray;
    }

    public synchronized void update()
    {


        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            if(body.isVisible())
            {
                body.checkCollisions();
            }
        }

        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            if(body.isVisible() && !body.collides())
            {
                body.updateForces();
            }
        }

        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            if(body.isVisible())
            {
                body.updatePosition();
            }
        }
    }
}