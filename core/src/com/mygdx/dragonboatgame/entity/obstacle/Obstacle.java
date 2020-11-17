package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents a movable obstacle in the game
 *
 * @author Devon
 */
public abstract class Obstacle extends DynamicEntity {

    private boolean breakable;
    private Texture brokenTexture;
    private float hardness;

    public Obstacle(Texture texture, Vector pos, Vector size) {
        super(texture, pos, size);
        this.breakable = false;
        this.brokenTexture = null;
        this.hardness = 0;
    }

    public boolean isBreakable() { return breakable; }
    public void setBreakable(boolean breakable) { this.breakable = breakable; }
    public Texture getBrokenTexture() { return brokenTexture; }
    public void setBrokenTexture(Texture brokenTexture) { this.brokenTexture = brokenTexture; }
    public float getHardness() { return hardness; }
    public void setHardness(float hardness) { this.hardness = hardness; }

    @Override
    public void onCollide(Entity other) {
        if (other instanceof Boat) {
            Boat boat = (Boat) other;
            boat.damage(this.hardness * boat.getDamageModifier());
            boat.setVelocity(boat.getVelocity().x * this.hardness, boat.getVelocity().y * this.hardness);

            if (this.breakable) {
                this.setActive(false);
            }
            if (this.brokenTexture != null) {
                this.setTexture(this.brokenTexture);
            }
        }
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.brokenTexture != null) {
            this.brokenTexture.dispose();
        }
    }
}
