package com.mygdx.dragonboatgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents an Entity with movement in the game
 *  Given entities have a velocity, as well as helper functions for manipulating this velocity
 *
 * @author Devon
 */
public abstract class DynamicEntity extends Entity {

    protected Vector velocity;
    protected Vector acceleration;

    public DynamicEntity(Texture texture, Vector pos, Vector size) {
        super(texture, pos, size);
        this.velocity = new Vector(0,0);
        this.acceleration = new Vector(0,0);
    }


    public void addVelocity(float x, float y) { this.velocity.add(x, y); }
    public void addVelocity(Vector velocity) {
        this.velocity.add(velocity);
    }
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
    public void setVelocity(float x, float y) { this.velocity.x = x; this.velocity.y = y;}
    public Vector getVelocity() {
        return this.velocity;
    }

    /**
     * Set acceleration to given x, y
     * @param x x axis acceleration
     * @param y y axis acceleration
     */
    public void setAcceleration(float x, float y) {
        this.acceleration.x = x;
        this.acceleration.y = y;
    }

    public void addAcceleration(float x, float y) {
        this.acceleration.x += x;
        this.acceleration.y += y;
    }

    public Vector getAcceleration() {
        return this.acceleration;
    }


    /**
     * Accelerate the current entity by our acceleration
     */
    private void accelerate() {
        this.velocity.add(this.acceleration);
        this.addAcceleration(-this.getAcceleration().x / 100, -this.getAcceleration().y / 100);
    }

    private void decelelerate() {
        this.addVelocity(-this.getVelocity().x / 100, -this.getVelocity().y / 100);
    }

    /**
     * Moves the entity based on it's current velocity
     */
    public void move() {
        this.accelerate();
        if (this.velocity.isZero()) return; // No movement needed if velocity is 0

        // Check bounds with water
        if (this.getPos().x < Game.GRASS_BORDER_WIDTH) {
            this.setVelocity(Math.max(this.getVelocity().x, 0), this.getVelocity().y);
        }
        if (this.getPos().x + this.getSize().x > Game.WIDTH - Game.GRASS_BORDER_WIDTH) {
            this.setVelocity(Math.min(this.getVelocity().x, 0), this.getVelocity().y);
        }

        float delta = Gdx.graphics.getDeltaTime();
        this.pos.x += this.velocity.x * delta;
        this.pos.y += this.velocity.y * delta;

        this.decelelerate();
    }


    public abstract void onCollide(Entity other);

    public void tick() {
        if (!this.isActive()) return;

        this.move();
        Entity touching = this.getTouching();
        if (touching != null) {
            this.onCollide(touching);
        }
    }


}
