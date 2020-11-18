package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.util.Vector;

public class Rock extends Obstacle {

    private static Texture TEXTURE = new Texture(Gdx.files.internal("entity/rock.png"));
    public static Vector SIZE = new Vector(25,25);

    public Rock(Vector pos) {
        super(TEXTURE, pos, SIZE);
        this.setBreakable(false);
        this.setHardness(1f);
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
