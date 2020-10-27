package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.util.Vector;

public class Duck extends MovableObstacle {


    public Duck(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/duck.png")), pos, new Vector(30,30));

        this.addVelocity(10, 10); // TODO: Remove this its just for testing
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
