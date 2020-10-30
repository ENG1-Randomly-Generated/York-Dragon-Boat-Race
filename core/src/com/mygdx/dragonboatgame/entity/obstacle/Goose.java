package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.util.Vector;

public class Goose extends Obstacle {


    public Goose(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/goose.png")), pos, new Vector(30,30));
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void tick() {
        super.tick();
    }
}
