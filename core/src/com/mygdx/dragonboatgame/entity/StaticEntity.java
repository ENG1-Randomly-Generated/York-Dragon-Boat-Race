package com.mygdx.dragonboatgame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents a stationary entity within the game
 *
 * @author Devon
 */
public abstract class StaticEntity extends Entity {


    public StaticEntity(Texture texture, Vector pos, Vector size) {
        super(texture, pos, size);
    }


    public abstract void onCollide(Entity other);

    public void tick() {
        Entity touching = this.getTouching();
        if (touching != null) {
            this.onCollide(touching);
        }
    }
}
