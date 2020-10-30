package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.dragonboatgame.entity.Boat;

/**
 * Represents an NPC. Each NPC has a difficulty rating which will affect their performance
 *  with moving and controlling their boat
 *
 * @author Devon
 */
public class NPC extends Team {

    private int difficulty;

    public NPC(String name, Color color, int difficulty) {
        super(name, color);
        this.difficulty = difficulty;
    }

    public NPC(String name, Color color, Boat boat, int difficulty) {
        super(name, color, boat);
        this.difficulty = difficulty;
    }

    @Override
    public void tick() {
        this.boat.tick();
        this.boat.accelerate(true, false, false, false);
    }


}
