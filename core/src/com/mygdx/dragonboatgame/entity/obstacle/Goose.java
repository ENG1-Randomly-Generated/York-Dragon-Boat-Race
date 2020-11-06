package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

public class Goose extends Obstacle {

    private static Texture TEXTURE = new Texture(Gdx.files.internal("entity/goose.png"));
    private static Texture BROKEN_TEXTURE = new Texture(Gdx.files.internal("entity/dead_goose.png"));
    private long randomMoveCooldown;

    public Goose(Vector pos) {
        super(TEXTURE, pos, new Vector(20,20));
        this.setBreakable(true);
        this.setHardness(0.5f);
        this.setBrokenTexture(BROKEN_TEXTURE);
        this.randomMoveCooldown = 0;
    }

    @Override
    public void move(float delta) {
        super.move(delta);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        if (randomMoveCooldown < System.currentTimeMillis()) {
            this.setAcceleration(Game.random.nextInt(100) - 50, Game.random.nextInt(100) - 50);
            randomMoveCooldown = System.currentTimeMillis() + 500;
        }
    }
}
