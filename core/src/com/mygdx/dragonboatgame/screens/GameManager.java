package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.game.NPC;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameManager extends Game {

    public ScreenManager sm;


    @Override
    public void create() {
        sm = new ScreenManager(this);
        sm.setScreen(ScreenManager.GAMESTATE.StartMenu);
        //TODO ADD ESSETS, BATCH and SHAPE BACTCH (shape render) here

        //called when application (game is an extension of application is first created???
    }

    public void tick() {
        float delta = Gdx.graphics.getDeltaTime();
        sm.getScreen().tick(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        sm.dispose();
    }


}
