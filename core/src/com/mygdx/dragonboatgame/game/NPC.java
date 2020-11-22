package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.util.Vector;

/**
 * Represents an NPC. Each NPC has a difficulty rating which will affect their performance
 *  with moving and controlling their boat
 *
 * @author Devon
 */
public class NPC extends Team {

    private int difficulty;
    private long accelerate_cooldown;
    private Float[] dividers;

    public NPC(String name, Color color, int difficulty) {
        super(name, color);
        this.difficulty = difficulty;
        this.accelerate_cooldown = 0;
    }

    public NPC(String name, Color color, Boat boat, int difficulty) {
        super(name, color, boat);
        this.difficulty = difficulty;
    }

    /**
     * Initialise this NPC for use in the Game
     *  This should be called at the start of every leg
     */
    public void init() {
        this.dividers = Game.getLaneDividers(this); // Update our dividers
    }

    /**
     * Return whether the current forward is clear for the NPC
     *  This is effected by difficulty
     *
     * @return Boolean whether forward is clear
     */
    private boolean isForwardClear() {
        int foresight = (300 * difficulty/10);

        Vector boatPos = this.boat.getPos();
        Vector boatSize = this.boat.getSize();

        for (Entity entity : Game.getEntities()) {
            Vector entityPos = entity.getPos();
            if (entityPos.x < boatPos.x + boatSize.x + 20 && entityPos.x > boatPos.x - 20) {
                if (entityPos.y > boatPos.y && entityPos.y < boatPos.y + boatSize.y + foresight) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Whether to dodge right (or left)
     *  Penalises either side based on the number of obstacles there are, and how
     *      close the boat is to the divider
     *
     * @return Whether to dodge right
     */
    private boolean dodgeRight() {
        int foresight = (300 * difficulty/10);

        Vector boatPos = this.boat.getPos();
        Vector boatSize = this.boat.getSize();

        float right = dividers[1] - boatPos.x;
        float left = boatPos.x - dividers[0];
        for (Entity entity : Game.getEntities()) {
            Vector entityPos = entity.getPos();
            if (entityPos.x > boatPos.x + boatSize.x && entityPos.x < boatPos.x + foresight) {
                if (entityPos.y > boatPos.y && entityPos.y < boatPos.y + boatSize.y + 100) {
                    right -= 5;
                }
            }
            if (entityPos.x < boatPos.x && entityPos.x > boatPos.x + foresight) {
                if (entityPos.y > boatPos.y && entityPos.y < boatPos.y + boatSize.y + 100) {
                    left -= 5;
                }
            }
        }


        return right > left;
    }


    /**
     * Calculate the next move for this NPC
     *  Current algorithm is as such:
     *      If energy > 0 and forward is clear of obstacles:
     *          Accelerate forward, and left or right to try and stay in the middle of the lane
     *      If energy > 0 BUT forward is not clear:
     *          Accelerate right or left depending on dodgeRight()
     *      If energy <= 0 then wait 4 seconds to regen energy
     *
     * @param delta The frame's delta time
     */
    private void calculateMove(float delta) {
        if (accelerate_cooldown > System.currentTimeMillis()) return; // Do not make a move, we are on cooldown

        Vector boatPos = this.boat.getPos();
        Vector boatSize = this.boat.getSize();

        if (this.boat.energy > 0) {

            if (isForwardClear()) {
                this.boat.accelerate(true, boatPos.x < dividers[0], false, boatPos.x + boatSize.x > dividers[1], delta);
            } else {
                if (dodgeRight()) {
                    this.boat.accelerate(true, true, false, false, delta);
                } else {
                    this.boat.accelerate(true, false, false, true, delta);
                }
            }
        } else {
            accelerate_cooldown = System.currentTimeMillis() + 4000;
        }
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        calculateMove(delta);
    }


}
