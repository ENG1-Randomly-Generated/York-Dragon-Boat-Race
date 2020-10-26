package com.mygdx.dragonboatgame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents an Entity within the game
 *  Each entity has it's own position, size and texture
 *
 * @author Devon
 */
public abstract class Entity {

    protected Vector pos;
    protected Vector size;
    protected SpriteBatch batch;
    protected Texture texture;
    protected boolean isVisible;


    public Entity(Texture texture, Vector pos, Vector size) {
        this.texture = texture;
        this.batch = new SpriteBatch();
        this.pos = pos;
        this.size = size;
        this.isVisible = true;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }
    public Vector getPos() {
        return this.pos;
    }
    public Vector getSize() { return size; }
    public void setSize(Vector size) { this.size = size; }
    public boolean isVisible() { return isVisible; }
    public void setVisible(boolean visible) { isVisible = visible; }


    public void draw() {
        if (!isVisible) return;

        batch.begin();
        batch.draw(texture, pos.x, pos.y, size.x, size.y);
        batch.end();
    }


    public abstract void tick();

}
