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

    public static Texture texture = new Texture(Gdx.files.internal("entity/boat.png"));
    public static Vector size = new Vector(50,100);


    protected float max_speed;
    protected float maneuverability;
    protected float max_robustness;

    protected float robustness;
    protected float energy;
    private int non_accelerating_ticks;

    private ShapeRenderer shapeRenderer;

    public Boat(String name, float max_speed, float maneuverability, float max_robustness) {
        super(texture, new Vector(0, 0), size);

        this.name = name;
        this.max_speed = max_speed;
        this.maneuverability = maneuverability;
        this.max_robustness = max_robustness;
        this.robustness = max_robustness;
        this.energy = 100;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.non_accelerating_ticks = 0;
    }


    /**
     * Reset the current boat for reuse
     *  Resets energy & HP
     */
    public void reset() {
        this.energy = 100;
        this.robustness = max_robustness;
        this.non_accelerating_ticks = 0;
    }

    /**
     * Accelerate the boat in the given directions
     *
     * @param up Whether to accelerate up
     * @param right Whether to accelerate right
     * @param down Whether to accelerate down
     * @param left Whether to accelerate left
     */
    public void accelerate(boolean up, boolean right, boolean down, boolean left, float delta) {
        if (this.robustness == 0) return; // Cannot accelerate if dead
        if (this.energy <= 0) return; // Cannot accelerate with no energy


        float dx = 0;
        float dy = 0;

        if (up) {
            dy += maneuverability * delta;
        }
        if (right) {
            dx += maneuverability * delta;
        }
        if (down) {
            dy -= maneuverability * delta;
        }
        if (left) {
            dx -= maneuverability * delta;
        }

        if (dx == 0 && dy == 0) return;

        this.energy -= 5 * delta; // TODO: Make this variable (difficulty.. etc)
        if (this.energy < 0) this.energy = 0;

        this.addAcceleration(dx, dy);
        this.non_accelerating_ticks = 0;
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
    public void move(float delta) {

        super.move(delta);

        this.velocity.clamp(max_speed);
        this.acceleration.clamp(maneuverability);
    }

    @Override
    public void onCollide(Entity other) {
    }

    @Override
    public void tick(float delta) {

        if (this.non_accelerating_ticks > (1/delta)) {
            this.energy += 10 * delta; // TODO: Change based on difficulty etc
            if (energy > 100) energy = 100;
        }

        this.non_accelerating_ticks++;
        super.tick(delta);
    }

    /**
     *  Overriding draw to draw energy and HP bars
     */
    @Override
    public void draw(Camera camera) {
        super.draw(camera);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // HP bar
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(this.getPos().x, this.getPos().y + (float)(this.getSize().y * 1.25), this.getSize().x, 10);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(this.getPos().x, this.getPos().y + (float)(this.getSize().y * 1.25), this.getSize().x * (robustness/max_robustness), 10);

        // Energy bar
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(this.getPos().x, this.getPos().y + (float)(this.getSize().y * 1.1), this.getSize().x, 10);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(this.getPos().x, this.getPos().y + (float)(this.getSize().y * 1.1), this.getSize().x * (energy/100), 10);

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

    public boolean isAlive() { return robustness > 0; }
}
