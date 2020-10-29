package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

public class Duck extends MovableObstacle {

    private long randomMoveCooldown;

    public Duck(Vector pos) {
        super(new Texture(Gdx.files.internal("entity/duck.png")), pos, new Vector(30,30));
        this.randomMoveCooldown = 0;
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void onCollide(Entity other) {
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
