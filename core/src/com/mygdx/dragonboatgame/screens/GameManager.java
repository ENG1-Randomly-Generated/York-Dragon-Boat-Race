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


    public static float WIDTH;
    public static float HEIGHT;
    public static float GRASS_BORDER_WIDTH;


    public ScreenManager sm;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private ArrayList<Entity> entities;
    private int leg;
    private float time;

    private static HashMap<String, int[]> BOATS = new HashMap<String, int[]>();

    /*
     * Declare our predefined boats here and append to the master HashMap
     * Name (String) : Boat (Boat)
     */
    static {
        // {max_speed, manovourability, max_robustness)
        BOATS.put("Speedy", new int[] {22, 14, 2});
        BOATS.put("Twisty", new int[] {20, 19, 4});
        BOATS.put("Tanky", new int[] {18, 15, 10});
    }
    //END OF WHAT MIGHT NEED TO MOVE


    //needs to be inside of game screen as menu's will have a different camera
    //private OrthographicCamera camera;


    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        GRASS_BORDER_WIDTH = WIDTH/8;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        sm = new ScreenManager(this);
        //TODO ADD ESSETS, BATCH and SHAPE BACTCH (shape render) here

        //called when application (game is an extension of application is first created???
    }

    public void tick() {
        float delta = Gdx.graphics.getDeltaTime();
        time += delta;
        sm.getScreen().tick(delta);
    }
    public HashMap<String, int[]> getAllBoats(){
        return BOATS;
    }
    @Override
    public void dispose() {
        super.dispose();
        sm.dispose();
    }
}
