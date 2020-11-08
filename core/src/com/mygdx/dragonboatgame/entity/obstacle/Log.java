package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

public class Log extends Obstacle {

    private static Texture TEXTURE = new Texture(Gdx.files.internal("entity/log.png"));
    private static Texture BROKEN_TEXTURE = new Texture(Gdx.files.internal("entity/broken_log.png"));

    public Log(Vector pos) {
        super(TEXTURE, pos, new Vector(30,30));
        this.setBreakable(true);
        this.setBrokenTexture(BROKEN_TEXTURE);
        this.setHardness(0.2f);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
    }

    @Override
    public void move(float delta) {
        super.move(delta);
    }
}
