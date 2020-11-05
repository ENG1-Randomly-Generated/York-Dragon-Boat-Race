package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.util.Vector;

public class Log extends Obstacle {

    private Texture broken;

    public Log(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/log.png")), pos, new Vector(30,30));
        this.broken = new Texture(Gdx.files.internal("entity/broken_log.png"));
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
    }

    @Override
    public void onCollide(Entity other) {
        super.onCollide(other);
        if (other instanceof Boat) {
            Boat boat = (Boat) other;
            boat.damage(0.1f * boat.getDamageModifier());
            boat.setVelocity(boat.getVelocity().x / 10, boat.getVelocity().y / 10);
            this.setActive(false);
            this.setTexture(broken);
        }
    }

    @Override
    public void move(float delta) {
        super.move(delta);
    }
}
