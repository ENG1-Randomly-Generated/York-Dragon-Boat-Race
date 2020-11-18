package com.mygdx.dragonboatgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.util.Vector;

public class Spectator extends StaticEntity {

    public static Texture TEXTURE = new Texture(Gdx.files.internal("entity/spectator.png"));
    public static Vector SIZE = new Vector(50,50);

    public Spectator(Vector pos, boolean left) {
        super(TEXTURE, pos, SIZE);
        if (left) {
            super.setRotation(90f);
        } else {
            super.setRotation(270f);
        }
    }

    @Override
    public void onCollide(Entity other) {}
}
