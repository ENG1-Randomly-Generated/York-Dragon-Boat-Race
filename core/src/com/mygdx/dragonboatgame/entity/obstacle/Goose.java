package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents a Goose Obstacle
 *  A Goose moves randomly, has a moderate hardness, but moves more slowly
 *
 * @author Devon
 */
public class Goose extends Obstacle {

    private static Texture TEXTURE = new Texture(Gdx.files.internal("entity/goose.png"));
    private static Texture BROKEN_TEXTURE = new Texture(Gdx.files.internal("entity/dead_goose.png"));
    public static Vector SIZE = new Vector(20,20);

    private long randomMoveCooldown;
    private int maxAcceleration;
    private int cooldown;

    public Goose(Vector pos) {
        super(TEXTURE, pos, SIZE);
        this.setBreakable(true);
        this.setHardness(0.5f);
        this.setBrokenTexture(BROKEN_TEXTURE);
        this.randomMoveCooldown = 0;
        this.maxAcceleration = 40 + (Game.leg * 20);
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
