package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.dragonboatgame.entity.Boat;

/**
 * Represents the player
 *
 * @author Devon
 */
public class Player extends Team {

    public Player(String name, Color color) {
        super(name, color);
    }

    public Player(String name, Color color, Boat boat) {
        super(name, color, boat);
    }

    @Override
    public void tick() {
    }

}
