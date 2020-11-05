package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.util.Vector;

public class Goose extends Obstacle {

    private Texture dead;

    public Goose(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/goose.png")), pos, new Vector(20,20));
        this.dead = new Texture(Gdx.files.internal("entity/dead_goose.png"));
    }

    @Override
    public void move(float delta) {
        super.move(delta);
    }

    @Override
    public void onCollide(Entity other) {
        super.onCollide(other);
        if (other instanceof Boat) {
            Boat boat = (Boat) other;
            boat.damage(0.1f * boat.getDamageModifier());
            boat.setVelocity(boat.getVelocity().x / 5, boat.getVelocity().y / 5);
            this.setActive(false);
            this.setTexture(dead);
        }
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
    }
}
