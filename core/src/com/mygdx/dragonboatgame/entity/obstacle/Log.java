package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.util.Vector;

public class Log extends MovableObstacle {


    public Log(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/log.png")), pos, new Vector(30,30));
    }

    @Override
    public void move() {
    }

    @Override
    public void tick() {
    }
}
