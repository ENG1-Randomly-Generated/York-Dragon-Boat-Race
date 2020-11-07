package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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


    private void checkInput(float delta) {
        boolean up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);

        this.boat.accelerate(up, right, down, left, delta);
    }


    @Override
    public void tick(float delta) {
        super.tick(delta);
        this.checkInput(delta);
    }

}
