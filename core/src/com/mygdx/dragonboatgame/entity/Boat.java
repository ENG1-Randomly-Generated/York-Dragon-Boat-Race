package com.mygdx.dragonboatgame.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.dragonboatgame.entity.obstacle.Obstacle;
import com.mygdx.dragonboatgame.game.Game;
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

    private ShapeRenderer shapeRenderer;

    public Boat(String name, float max_speed, float maneuverability, float max_robustness) {
        super(new Texture(Gdx.files.internal("entity/boat.png")), new Vector(0, 0), new Vector(50,100));

        this.name = name;
        this.max_speed = max_speed;
        this.maneuverability = maneuverability;
        this.max_robustness = max_robustness;
        this.robustness = max_robustness;
        this.playing = false;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
    }

    public void accelerate(boolean up, boolean right, boolean down, boolean left) {
        if (this.robustness == 0) return; // Cannot accelerate if dead

        float dx = 0;
        float dy = 0;

        if (up) {
            dy += maneuverability / 10;
        }
        if (right) {
            dx += maneuverability / 10;
        }
        if (down) {
            dy -= maneuverability / 10;
        }
        if (left) {
            dx -= maneuverability / 10;
        }

        this.addAcceleration(dx, dy);
    }

    public void damage(float damage) {
        this.robustness -= damage;
        if (this.robustness < 0) {
            this.robustness = 0;
        }
    }

    /**
     * Return the damage modifier in the case of a collision with this boat
     *  e.g. take into account the velocity, acceleration, size, etc
     *      TODO: Maybe robustness can make an affect?
     *      TODO: Add other modifiers, rn we only care about velocity
     *      TODO: Change with difficulty ???
     *
     * @return double modifier
     */
    public float getDamageModifier() {
        return (this.getVelocity().getMagnitude() / 30) + 1;
    }

    @Override
    public void move() {
        if (!playing) return;
        super.move();

        if (this.velocity.getMagnitude() >= this.max_speed) {
            this.setAcceleration(0, 0);
        }
    }

    @Override
    public void onCollide(Entity other) {
    }

    @Override
    public void tick() {
        if (!playing) return;
        super.tick();

    }

    /**
     *  Overriding draw to draw energy and HP bars
     */
    @Override
    public void draw(Camera camera) {
        if (!playing) return;
        super.draw(camera);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // HP bar
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(this.getPos().x, this.getPos().y + (float)(this.getSize().y * 1.1), this.getSize().x, 10);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(this.getPos().x, this.getPos().y + (float)(this.getSize().y * 1.1), this.getSize().x * (robustness/max_robustness), 10);

        shapeRenderer.end();
    }

    /**
     * Create a clone of this boat
     *
     * @return Boat object with exact same attributes
     */
    public Boat clone() {
        return new Boat(this.name, this.max_speed, this.maneuverability, this.max_robustness);
    }

    public boolean isPlaying() { return playing; }
    public void setPlaying(boolean playing) { this.playing = playing; }
}
