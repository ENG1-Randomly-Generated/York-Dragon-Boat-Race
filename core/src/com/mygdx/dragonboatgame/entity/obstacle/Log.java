package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.util.Vector;

public class Log extends Obstacle {


    public Log(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/log.png")), pos, new Vector(30,30));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void onCollide(Entity other) {
        super.onCollide(other);
    }

    @Override
    public void move() {
        super.move();
    }
}
