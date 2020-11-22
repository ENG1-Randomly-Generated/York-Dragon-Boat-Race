package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents a Rock Obstacle
 *  A Rock has major hardness and cannot be broken, but is stationary
 *
 * @author Devon
 */
public class Rock extends Obstacle {

    private static Texture TEXTURE = new Texture(Gdx.files.internal("entity/rock.png"));
    public static Vector SIZE = new Vector(25,25);

    public Rock(Vector pos) {
        super(TEXTURE, pos, SIZE);
        this.setBreakable(false);
        this.setHardness((Game.leg / 4f));
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
