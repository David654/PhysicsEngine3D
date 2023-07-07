package engine.physics.body;

import engine.math.vector.Vector3;
import engine.physics.PhysicConstants;
import engine.physics.shape.Box;
import engine.physics.shape.Sphere;
import engine.util.ColorUtils;

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

    public int[] getBodyIDs()
    {
        int[] intArray = new int[PhysicConstants.MAX_BODY_NUM];
        Body[] bodies = getActiveBodies();

        for(int i = 0; i < bodies.length; i++)
        {
            Body body = bodies[i];
            intArray[i] = body.getShape() instanceof Sphere ? 1 : 2;
        }

        return intArray;
    }

    public float[] getBodyPositions()
    {
        float[] floatArray = new float[PhysicConstants.MAX_BODY_NUM * 3];
        Body[] bodies = getActiveBodies();

        for(int i = 0; i < bodies.length; i++)
        {
            Body body = bodies[i];
            Vector3 position = body.getPosition().multiply(PhysicConstants.WORLD_SCALE);;
            floatArray[i * 3] = (float) position.getX();
            floatArray[i * 3 + 1] = (float) position.getY();
            floatArray[i * 3 + 2] = (float) position.getZ();
        }

        return floatArray;
    }

    public float[] getBodyDimensions()
    {
        float[] floatArray = new float[PhysicConstants.MAX_BODY_NUM * 3];
        Body[] bodies = getActiveBodies();

        for(int i = 0; i < bodies.length; i++)
        {
            Body body = bodies[i];
            Vector3 dimensions = new Vector3();

            if(body.getShape() instanceof Sphere sphere)
            {
                dimensions = sphere.getDimensions();
            }
            else if(body.getShape() instanceof Box box)
            {
                dimensions = box.getDimensions();
            }

            dimensions = dimensions.multiply(PhysicConstants.WORLD_SCALE);
            floatArray[i * 3] = (float) dimensions.getX();
            floatArray[i * 3 + 1] = (float) dimensions.getY();
            floatArray[i * 3 + 2] = (float) dimensions.getZ();
        }

        return floatArray;
    }

    public float[] getBodyColors()
    {
        float[] floatArray = new float[PhysicConstants.MAX_BODY_NUM * 3];
        Body[] bodies = getActiveBodies();

        for(int i = 0; i < bodies.length; i++)
        {
            Body body = bodies[i];
            Vector3 color = ColorUtils.toGLColor(body.getMaterial().getColor());
            floatArray[i * 3] = (float) color.getX();
            floatArray[i * 3 + 1] = (float) color.getY();
            floatArray[i * 3 + 2] = (float) color.getZ();
        }

        return floatArray;
    }

    public float[] getBodyDiffuses()
    {
        float[] floatArray = new float[PhysicConstants.MAX_BODY_NUM];
        Body[] bodies = getActiveBodies();

        for(int i = 0; i < bodies.length; i++)
        {
            Body body = bodies[i];
            floatArray[i] = (float) body.getMaterial().getDiffuse();
        }

        return floatArray;
    }

    public float[] getBodyRefractions()
    {
        float[] floatArray = new float[PhysicConstants.MAX_BODY_NUM];
        Body[] bodies = getActiveBodies();

        for(int i = 0; i < bodies.length; i++)
        {
            Body body = bodies[i];
            floatArray[i] = (float) body.getMaterial().getRefraction();
        }

        return floatArray;
    }

    public synchronized void update()
    {
        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            if(body.isEnabled())
            {
                body.checkCollisions();
            }
        }

        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            if(body.isEnabled && !body.collides())
            {
                body.updateForces();
            }
        }

        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            if(body.isEnabled())
            {
                body.updatePosition();
            }
        }
    }
}