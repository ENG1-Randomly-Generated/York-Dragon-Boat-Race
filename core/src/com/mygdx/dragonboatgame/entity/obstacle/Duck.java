package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

public class Duck extends Obstacle {

    private static Texture TEXTURE = new Texture(Gdx.files.internal("entity/duck.png"));
    public static Vector SIZE = new Vector(20,20);
    private static Texture BROKEN_TEXTURE = new Texture(Gdx.files.internal("entity/dead_duck.png"));

    private long randomMoveCooldown;
    private int maxAcceleration;
    private int cooldown;

    public Duck(Vector pos) {
        super(TEXTURE, pos, SIZE);
        this.setBreakable(true);
        this.setBrokenTexture(BROKEN_TEXTURE);
        this.setHardness(0.4f);
        this.randomMoveCooldown = 0;
        this.maxAcceleration = 50 + (Game.leg * 25);
        this.cooldown = 500 - (100 * Game.leg);
    }

    @Override
    public void move(float delta) {
        super.move(delta);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        if (randomMoveCooldown < System.currentTimeMillis()) {
            this.setAcceleration(Game.random.nextInt(maxAcceleration) - (maxAcceleration/2f), Game.random.nextInt(maxAcceleration) - (maxAcceleration/2f));
            randomMoveCooldown = System.currentTimeMillis() + cooldown;
        }
    }

}
