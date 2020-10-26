package com.mygdx.dragonboatgame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents an Entity with movement in the game
 *  Given entities have a velocity, as well as helper functions for manipulating this velocity
 *
 * @author Devon
 */
public abstract class DynamicEntity extends Entity {

    protected Vector velocity;

    public DynamicEntity(Texture texture, Vector pos, Vector size) {
        super(texture, pos, size);
        this.velocity = new Vector(0,0);
    }


    public void addVelocity(float x, float y) { this.velocity.add(x, y); }
    public void addVelocity(Vector velocity) {
        this.velocity.add(velocity);
    }
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
    public Vector getVelocity() {
        return this.velocity;
    }


    // TODO: Change this for smoothness etc
    // LERP? We need to consider movement cannot change upon render rate
    public void move() {
        this.pos.x += this.velocity.x;
        this.pos.y += this.velocity.y;
    }

    public void tick() {
        this.move();
    }


}
