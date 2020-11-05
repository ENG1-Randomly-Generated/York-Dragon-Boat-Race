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
        this.acceleration.add(x, y);
    }

    public void addAcceleration(Vector acceleration) {
        this.acceleration.add(acceleration);
    }

    public Vector getAcceleration() {
        return this.acceleration;
    }


    /**
     * Accelerate the current entity by our acceleration
     */
    private void accelerate(float delta) {
        this.velocity.add(this.acceleration.multiply(delta));
        this.addAcceleration(this.acceleration.multiply(-1f * delta));
    }

    private void decelerate(float delta) {
        this.addVelocity(this.velocity.multiply(-1f * delta));
    }

    /**
     * Moves the entity based on it's current velocity
     */
    public void move(float delta) {

        this.accelerate(delta);
        if (this.velocity.isZero()) return; // No movement needed if velocity is 0

        // Check bounds with water
        if (this.getPos().x < Game.GRASS_BORDER_WIDTH) {
            this.setVelocity(Math.max(this.getVelocity().x, 0), this.getVelocity().y);
        }
        if (this.getPos().x + this.getSize().x > Game.WIDTH - Game.GRASS_BORDER_WIDTH) {
            this.setVelocity(Math.min(this.getVelocity().x, 0), this.getVelocity().y);
        }


        this.pos.x += this.velocity.x * delta;
        this.pos.y += this.velocity.y * delta;

        this.decelerate(delta);
    }


    public abstract void onCollide(Entity other);


    public void tick(float delta) {
        if (!this.isActive()) return;

        this.move(delta);
        Entity touching = this.getTouching();
        if (touching != null) {
            this.onCollide(touching);
        }
    }


}
