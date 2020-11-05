package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.util.Vector;

public class Rock extends Obstacle {

    public Rock() {
        super(new Texture(Gdx.files.internal("entity/rock.png")), new Vector(0, 0), new Vector(25,25));
    }
    public Rock(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/rock.png")), pos, new Vector(25,25));
    }


    @Override
    public void onCollide(Entity other) {
        super.onCollide(other);
        if (other instanceof Boat) {
            Boat boat = (Boat) other;
            boat.damage(0.03f * boat.getDamageModifier());
            boat.setVelocity(0, 0);
        }
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
    }

    @Override
    public void move(float delta) {
        super.move(delta);
    }
}
