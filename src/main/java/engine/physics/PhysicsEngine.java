package engine.physics;

import engine.math.vector.Vector3;
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
        BODY_HANDLER.add(new DynamicBody(new Vector3(0, 0, 4), new Vector3(1, 0, 3), 2, new SphereCollider(new Vector3(0, 0, 4), 1), new Sphere(new Vector3(0, 0, 4), 1), new Material( new Color(244, 113, 116), 1, 1.52)));
        BODY_HANDLER.add(new DynamicBody(new Vector3(4, 0, 0), new Vector3(3, 0, -4), 1, new AABBCollider(new Vector3(4, 0, 0), new Vector3(1.5)), new Box(new Vector3(4, 0, 0), new Vector3(1.5)), new Material(new Color(116, 244, 113), 0.5, 0)));
        BODY_HANDLER.add(new StaticBody(new Vector3(0, 0, -4), new SphereCollider(new Vector3(0, 0, -4), 2), new Sphere(new Vector3(0, 0, -4), 2), new Material(new Color(113, 190, 244), 0.7, 0)));

        double boxSize = 10;

        BODY_HANDLER.add(new StaticBody(new Vector3(0, -5, 0), new AABBCollider(new Vector3(0, -5, 0), new Vector3(boxSize, 0.2, boxSize)), new Box(new Vector3(0, -5, 0), new Vector3(boxSize, 0.2, boxSize)), new Material(new Color(64, 64, 64), 0, 0)));
        BODY_HANDLER.add(new StaticBody(new Vector3(boxSize, 4.8, 0), new AABBCollider(new Vector3(boxSize, 4.8, 0), new Vector3(0.2, boxSize, boxSize)), new Box(new Vector3(boxSize, 4.8, 0), new Vector3(0.2, boxSize, boxSize)), new Material(new Color(128, 128, 128), 1, 0)));
        BODY_HANDLER.add(new StaticBody(new Vector3(-boxSize, 4.8, 0), new AABBCollider(new Vector3(-boxSize, 4.8, 0), new Vector3(0.2, boxSize, boxSize)), new Box(new Vector3(-boxSize, 4.8, 0), new Vector3(0.2, boxSize, boxSize)), new Material(new Color(128, 128, 128), 1, 0)));
        BODY_HANDLER.add(new StaticBody(new Vector3(0, 4.8, boxSize), new AABBCollider(new Vector3(0, 4.8, boxSize), new Vector3(boxSize, boxSize, 0.2)), new Box(new Vector3(0, 4.8, boxSize), new Vector3(boxSize, boxSize, 0.2)), new Material(new Color(128, 128, 128), 0.5, 0)));
        BODY_HANDLER.add(new StaticBody(new Vector3(0, 4.8, -boxSize), new AABBCollider(new Vector3(0, 4.8, -boxSize), new Vector3(boxSize, boxSize, 0.2)), new Box(new Vector3(0, 4.8, -boxSize), new Vector3(boxSize, boxSize, 0.2)), new Material(new Color(200, 200, 200), 1, 0.0000000001)));
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
        double amountOfTicks = 60.0;
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