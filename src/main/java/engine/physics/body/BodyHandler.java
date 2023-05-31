package engine.physics.body;

import java.util.ArrayList;

public class BodyHandler extends ArrayList<Body>
{
    public BodyHandler()
    {

    }

    public void update()
    {
        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            body.updateForces();
        }

        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            body.checkCollisions();
        }

        for(int i = 0; i < this.size(); i++)
        {
            Body body = this.get(i);
            body.updatePosition();
        }
    }
}