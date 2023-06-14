package engine.physics;

import engine.math.vector.Vector3;
import engine.physics.body.Body;
import engine.physics.body.BodyHandler;
import engine.physics.body.DynamicBody;
import engine.physics.body.StaticBody;
import engine.physics.collider.AABBCollider;
import engine.physics.collider.SphereCollider;
import engine.physics.material.Material;
import engine.physics.shape.Box;
import engine.physics.shape.Sphere;

import java.awt.*;

public class PhysicsEngine implements Runnable
{
    public static BodyHandler BODY_HANDLER;

    private Thread thread;
    private boolean running = false;

    public PhysicsEngine()
    {
        BODY_HANDLER = new BodyHandler();

        initBodies();
    }

    public BodyHandler getBodyHandler()
    {
        return BODY_HANDLER;
    }

    private void initBodies()
    {
        double boxSize = 10;


       // BODY_HANDLER.add(new DynamicBody(new Vector3(4, 0, 0), new Vector3(3, 0, -4), 1, new AABBCollider(new Vector3(4, 0, 0), new Vector3(1.5)), new Box(new Vector3(4, 0, 0), new Vector3(1.5)), new Material(new Color(116, 244, 113), 0.5, 0)));
      //  BODY_HANDLER.add(new StaticBody(new Vector3(0, 0, -4), new SphereCollider(new Vector3(0, 0, -4), 2), new Sphere(new Vector3(0, 0, -4), 2), new Material(new Color(113, 190, 244), 0.7, 0)));

        double ballRadius = 0.057;
        double ballMass = 0.17;
        int rows = 4;
        double distFactor = 2.2;
        double y = -1;
        double z = 0.8;

        Vector3 position = new Vector3(0.1, y + 1, z + 0.1);
        BODY_HANDLER.add(new DynamicBody(position, new Vector3(0, -1, 0), ballMass, new SphereCollider(position, ballRadius), new Sphere(position, ballRadius), new Material( new Color(0, 255, 255), 1, 0, 0.06, 0.9)));
        //BODY_HANDLER.add(new DynamicBody(new Vector3(0, -0.8, -1.5), new Vector3(0, 0, 1), 1, new AABBCollider(new Vector3(0, -0.8, -5), new Vector3(ballRadius * 4)), new Box(new Vector3(0, -0.8, -5), new Vector3(ballRadius * 4)), new Material( new Color(244, 113, 116), 1, 0.5, 0.06, 0.5)));

        /*for(int i = 0; i < rows; i++)
        {
            double offset = ballRadius * distFactor * i / 2;

            for(int j = 0; j < i + 1; j++)
            {
                position = new Vector3(j * ballRadius * distFactor - offset, y, z + i * ballRadius * 2);

                double mass = ballMass;
                double radius = ballRadius;
                System.out.println(mass);
                BODY_HANDLER.add(new DynamicBody(position,
                        new Vector3(0, 0, 0), mass,
                        new SphereCollider(position, radius),
                        new Sphere(position, radius),
                        new Material(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)), 1, 0, 0.06, 0.9)));
                //new Color((int) (mass / 500 * 255), (int) ((500 - mass) / 500 * 255), 0)
            }
        }**/

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < rows; j++)
            {
                for(int k = 0; k < rows; k++)
                {
                    ballRadius = 0.057;
                    position = new Vector3(i * ballRadius * 2.2, y + j * ballRadius * 2.2, z + k * ballRadius * 2.2);
                    double mass = Math.pow(ballRadius, 3) * Math.PI * 4 / 3 * PhysicConstants.AIR_DENSITY;
                    double radius = ballRadius;
                    System.out.println(mass);

                    DynamicBody body = new DynamicBody(position,
                            new Vector3(0, 0, 0), mass,
                            new SphereCollider(position, radius),
                            new Sphere(position, radius),
                            new Material(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)), 1, 1.5, 0, 1));
                    body.applyGravity(false);
                    //BODY_HANDLER.add(body);
                }
            }
        }

//(int) MathUtils.getRandomValue(0, 255), (int) MathUtils.getRandomValue(0, 255), (int) MathUtils.getRandomValue(0, 255)

       BODY_HANDLER.add(new StaticBody(new Vector3(0, -2, 0), new AABBCollider(new Vector3(0, -2, 0), new Vector3(1.27, 0.2, 2.54)), new Box(new Vector3(0, -2, 0), new Vector3(1.27, 0.2, 2.54)), new Material(new Color(128, 128, 128), 1, 0, 0.02, 0.384)));
      // BODY_HANDLER.add(new StaticBody(new Vector3(-1.27, -1.2 - ballRadius, 0), new AABBCollider(new Vector3(-1.27, -1.2 - ballRadius, 0), new Vector3(0.2, 1.27 / 2 - 0.2, 2.54)), new Box(new Vector3(-1.27, -1.2 - ballRadius, 0), new Vector3(0.2, 1.27 / 2 - 0.2, 2.54)), new Material(new Color(0, 128, 0), 1, 0, 0.02, 0.384)));
       //BODY_HANDLER.add(new StaticBody(new Vector3(1.27, -1.2 - ballRadius, 0), new AABBCollider(new Vector3(1.27, -1.2 - ballRadius, 0), new Vector3(0.2, 1.27 / 2 - 0.2, 2.54)), new Box(new Vector3(1.27, -1.2 - ballRadius, 0), new Vector3(0.2, 1.27 / 2 - 0.2, 2.54)), new Material(new Color(0, 128, 0), 1, 0, 0.02, 0.384)));
       //BODY_HANDLER.add(new StaticBody(new Vector3(0, y - ballRadius * 1.205, 0), new AABBCollider(new Vector3(0, y - ballRadius * 1.205, 0), new Vector3(boxSize, 0.2, boxSize * 4)), new Box(new Vector3(0, y - ballRadius * 1.205, 0), new Vector3(boxSize, 0.2, boxSize * 4)), new Material(new Color(64, 64, 64), 1, 0, 0.2, 0.384)));
       // BODY_HANDLER.add(new StaticBody(new Vector3(boxSize, 4.8, 0), new AABBCollider(new Vector3(boxSize, 4.8, 0), new Vector3(0.2, boxSize, boxSize * 4)), new Box(new Vector3(boxSize, 4.8, 0), new Vector3(0.2, boxSize, boxSize * 4)), new Material(new Color(128, 128, 128), 1, 0, 0.2, 0.48)));
       // BODY_HANDLER.add(new StaticBody(new Vector3(-boxSize, 4.8, 0), new AABBCollider(new Vector3(-boxSize, 4.8, 0), new Vector3(0.2, boxSize, boxSize * 4)), new Box(new Vector3(-boxSize, 4.8, 0), new Vector3(0.2, boxSize, boxSize * 4)), new Material(new Color(128, 128, 128), 1, 0, 0.2, 0.48)));
       // BODY_HANDLER.add(new StaticBody(new Vector3(0, 4.8, boxSize * 4), new AABBCollider(new Vector3(0, 4.8, boxSize * 4), new Vector3(boxSize, boxSize, 0.2)), new Box(new Vector3(0, 4.8, boxSize * 4), new Vector3(boxSize, boxSize, 0.2)), new Material(new Color(128, 128, 128), 0.5, 0, 0.2, 0.48)));
       // BODY_HANDLER.add(new StaticBody(new Vector3(0, 4.8, -boxSize), new AABBCollider(new Vector3(0, 4.8, -boxSize), new Vector3(boxSize, boxSize, 0.2)), new Box(new Vector3(0, 4.8, -boxSize), new Vector3(boxSize, boxSize, 0.2)), new Material(new Color(200, 200, 200), 1, 0.0000000001, 0.2, 0.48)));
    }

    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        BODY_HANDLER.update();
    }

    public void run()
    {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1)
            {
                update();
                delta--;
            }

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }
}