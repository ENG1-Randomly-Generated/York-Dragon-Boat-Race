package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

public class Duck extends Obstacle {

    private long randomMoveCooldown;
    private Texture dead;

    public Duck(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/duck.png")), pos, new Vector(20,20));
        this.randomMoveCooldown = 0;
        this.dead = new Texture(Gdx.files.internal("entity/dead_duck.png"));
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void onCollide(Entity other) {
        super.onCollide(other);
        if (other instanceof Boat) {
            Boat boat = (Boat) other;
            boat.damage(0.05f * boat.getDamageModifier());
            boat.setVelocity(boat.getVelocity().x / 5, boat.getVelocity().y / 5);
            this.setActive(false);
            this.setTexture(dead);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (randomMoveCooldown < System.currentTimeMillis()) {
            this.setVelocity(new Vector(Game.random.nextInt(50) - 25, Game.random.nextInt(50) - 25));
            randomMoveCooldown = System.currentTimeMillis() + 1000;
        }
    }


}
