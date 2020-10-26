package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.util.Vector;

public class Rock extends StationaryObstacle {

    public Rock() {
        super(new Texture(Gdx.files.internal("entity/rock.png")), new Vector(0, 0), new Vector(30,30));
    }

    @Override
    public void tick() {
    }
}
