package com.mygdx.dragonboatgame.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.dragonboatgame.util.Vector;

import java.util.ArrayList;

/**
 * Represents an Entity within the game
 *  Each entity has it's own position, size and texture
 *  Entity also handles collisions between entities
 *
 * @author Devon
 */
public abstract class Entity {

    public static float COLLISION_OFFSET = 5;

    public static ArrayList<Entity> entities = new ArrayList<Entity>();

    protected Vector pos;
    private Vector size;
    protected SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;
    protected Texture texture;
    protected boolean isVisible;
    protected boolean active;
    protected float rotation;


    public Entity(Texture texture, Vector pos, Vector size) {
        this.texture = texture;
        this.batch = new SpriteBatch();
        this.pos = pos;
        this.size = size;
        this.isVisible = true;
        this.active = true;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.rotation = 0;

        entities.add(this); // Register this entity in global list
    }

    /**
     * Returns whether the two entities are touching
     *
     * @param other Other entity
     * @return Boolean whether entities are touching
     */
    public boolean isTouching(Entity other) {
        return (this.pos.x < other.pos.x + other.size.x - COLLISION_OFFSET)
                && (other.pos.x < this.pos.x + this.size.x - COLLISION_OFFSET)
                && (this.pos.y < other.pos.y + other.size.y - COLLISION_OFFSET)
                && (other.pos.y < this.pos.y + this.size.y - COLLISION_OFFSET);
    }

    /**
     * Returns the entity this entity is touching, or null
     *
     * @return Entity the Entity touching this one, or null if nothing touching
     */
    public Entity getTouching() {
        for (Entity e : Entity.entities) {
            if (e == this) continue;
            if (!e.isActive()) continue;

            if (this.isTouching(e)) {
                return e;
            }
        }
        return null;
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
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Texture getTexture() { return texture; }
    public void setTexture(Texture texture) { this.texture = texture; }
    public float getRotation() { return rotation; }
    public void setRotation(float rotation) { this.rotation = rotation; }

    /**
     * Draw this Entity on the screen at it's given position, size and rotation
     *
     * @param camera Camera used to render the screen
     */
    public void draw(Camera camera) {
        if (!isVisible) return;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture, pos.x, pos.y, size.x/2, size.y/2, size.x, size.y, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        batch.end();
    }


    public abstract void tick(float delta);


    /**
     * Dispose of the current entity
     */
    public void dispose() {
        this.texture.dispose();
        this.batch.dispose();
        this.shapeRenderer.dispose();
    }

}
