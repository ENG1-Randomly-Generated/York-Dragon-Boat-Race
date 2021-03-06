package com.mygdx.dragonboatgame.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;


/**
 * Represents a boat, with given attributes changing certain characteristics for diversity.
 *
 * @author Devon
 */
public class Boat extends DynamicEntity {

    protected String name;
    protected String team_name;

    public static Texture TEXTURE = new Texture(Gdx.files.internal("entity/boat.png"));
    public static Vector SIZE = new Vector(50,100);


    public final float max_speed;
    public final float maneuverability;
    public final float max_robustness;
    public final float base_acceleration;

    public float robustness;
    public float energy;
    private int non_accelerating_ticks;

    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private SpriteBatch batch;

    public Boat(String name, float max_speed, float maneuverability, float max_robustness, float base_acceleration) {
        super(TEXTURE, new Vector(0, 0), SIZE);

        this.name = name;
        this.max_speed = max_speed;
        this.maneuverability = maneuverability;
        this.max_robustness = max_robustness;
        this.base_acceleration = base_acceleration;
        this.robustness = max_robustness;
        this.energy = 100;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.non_accelerating_ticks = 0;
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
        this.team_name = "";
    }


    /**
     * Reset the current boat for reuse
     *  Resets energy, HP and velocity/acceleration
     */
    public void reset() {
        this.energy = 100;
        this.robustness = max_robustness;
        this.non_accelerating_ticks = 0;
        this.acceleration.clamp(0);
        this.velocity.clamp(0);
    }

    /**
     * Accelerate the boat in the given directions
     *
     * @param up Whether to accelerate up
     * @param right Whether to accelerate right
     * @param down Whether to accelerate down
     * @param left Whether to accelerate left
     * @param delta Delta time for the frame this is called
     */
    public void accelerate(boolean up, boolean right, boolean down, boolean left, float delta) {
        if (this.robustness == 0) return; // Cannot accelerate if dead
        if (this.energy <= 0) return; // Cannot accelerate with no energy


        float dx = 0;
        float dy = 0;

        if (up) {
            dy += base_acceleration * delta;
        }
        if (right) {
            dx += maneuverability * delta;
        }
        if (down) {
            dy -= base_acceleration * delta;
        }
        if (left) {
            dx -= maneuverability * delta;
        }

        if (dx == 0 && dy == 0) return;

        // Remove energy for this acceleration
        this.energy -= 5 * delta;
        if (this.energy < 0) this.energy = 0;

        this.addAcceleration(dx, dy);
        this.non_accelerating_ticks = 0;
    }

    /**
     * Damage the boat by the given float amount
     * @param damage Damage to do to the boat
     */
    public void damage(float damage) {
        this.robustness -= damage;
        if (this.robustness < 0) {
            this.robustness = 0;
        }
    }

    /**
     * Return the damage modifier in the case of a collision with this boat
     *  e.g. take into account the velocity, acceleration, size, etc
     *  This can be changed to include more features into the damage system
     *
     * @return Double modifier of damage to boat
     */
    public float getDamageModifier() {
        return (this.getVelocity().getMagnitude() / (12 - (2 * Game.leg))) + 1;
    }

    @Override
    public void move(float delta) {

        super.move(delta);

        // Clamp our speed and acceleration to the maximum
        this.velocity.clamp(max_speed);
        this.acceleration.clamp(base_acceleration);
    }

    @Override
    public void onCollide(Entity other) {
    }

    @Override
    public void tick(float delta) {

        // If we haven't been accelerating for 1 second, start regaining energy
        if (this.non_accelerating_ticks > (1/delta)) {
            this.energy += 10 * delta;
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

        // Name of boat
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.draw(batch, this.team_name, this.getPos().x, this.getPos().y + (float)(this.getSize().y * 1.65));
        font.draw(batch, this.name, this.getPos().x, this.getPos().y + (float)(this.getSize().y * 1.5));

        batch.end();
    }

    /**
     * Create a clone of this boat
     *
     * @return Boat object with exact same attributes
     */
    public Boat clone() {
        return new Boat(this.name, this.max_speed, this.maneuverability, this.max_robustness, this.base_acceleration);
    }

    /**
     * Returns whether this boat is alive
     *  i.e. whether this boat's current robustness is not 0
     * @return Boolean whether boat is alive
     */
    public boolean isAlive() { return robustness > 0; }

    /**
     * Sets the team name for this boat that is displayed above it
     * @param team_name Team name
     */
    public void setTeam_name(String team_name) { this.team_name = team_name; }
}
