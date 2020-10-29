package com.mygdx.dragonboatgame.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.util.Vector;


/**
 * Represents a boat, with given attributes changing certain characteristics for diversity.
 *
 * @author Devon
 */
public class Boat extends DynamicEntity {

    private static final float RESISTANCE = 1;

    protected String name;

    protected float max_speed;
    protected float maneuverability;
    protected float max_robustness;

    protected float robustness;
    protected boolean playing;

    public Boat(String name, float max_speed, float maneuverability, float max_robustness) {
        super(new Texture(Gdx.files.internal("entity/boat.png")), new Vector(0, 0), new Vector(100,100));

        this.name = name;
        this.max_speed = max_speed;
        this.maneuverability = maneuverability;
        this.max_robustness = max_robustness;
        this.robustness = max_robustness;
        this.playing = false;
    }

    public void accelerate(boolean up, boolean right, boolean down, boolean left) {
        float dx = 0;
        float dy = 0;

        if (up) {
            dy += maneuverability;
        }
        if (right) {
            dx += maneuverability;
        }
        if (down) {
            dy -= maneuverability;
        }
        if (left) {
            dx -= maneuverability;
        }

        this.setAcceleration(dx, dy);
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void onCollide(Entity other) {
    }

    @Override
    public void tick() {
        super.tick();
    }
}
