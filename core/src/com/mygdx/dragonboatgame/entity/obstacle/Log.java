package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents a Log Obstacle
 *  A Log has moderate hardness, but only moves slowly downstream
 *
 * @author Devon
 */
public class Log extends Obstacle {

    private static Texture TEXTURE = new Texture(Gdx.files.internal("entity/log.png"));
    private static Texture BROKEN_TEXTURE = new Texture(Gdx.files.internal("entity/broken_log.png"));
    public static Vector SIZE = new Vector(30,30);

    private int velocity;

    public Log(Vector pos) {
        super(TEXTURE, pos, SIZE);
        this.setBreakable(true);
        this.setBrokenTexture(BROKEN_TEXTURE);
        this.setHardness(0.4f);
        this.velocity = 5 + (Game.leg * 5);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
    }

    @Override
    public void move(float delta) {
        super.move(delta);
        this.setVelocity(0, -velocity);
    }
}
