package com.mygdx.dragonboatgame.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.util.Vector;


/**
 * Represents a boat, with given attributes changing certain characteristics for diversity.
 *
 * @author Devon
 */
public class Boat extends DynamicEntity {

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

    @Override
    public void move() {
    }

    @Override
    public void tick() {
    }
}
