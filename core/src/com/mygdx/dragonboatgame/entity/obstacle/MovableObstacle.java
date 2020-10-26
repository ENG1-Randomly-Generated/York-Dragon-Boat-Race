package com.mygdx.dragonboatgame.entity.obstacle;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragonboatgame.entity.DynamicEntity;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents a movable obstacle in the game
 *  TODO: Add damage / collisions here as this is a general case amongst all obstacles
 *
 * @author Devon
 */
public abstract class MovableObstacle extends DynamicEntity {


    public MovableObstacle(Texture texture, Vector pos, Vector size) {
        super(texture, pos, size);
    }

}
