package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.util.Vector;

public class Rock extends Obstacle {

    public Rock() {
        super(new Texture(Gdx.files.internal("entity/rock.png")), new Vector(0, 0), new Vector(30,30));
    }
    public Rock(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/rock.png")), pos, new Vector(30,30));
    }

    @Override
    public void onCollide(Entity other) {
        super.onCollide(other);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void move() {
        super.move();
    }
}
