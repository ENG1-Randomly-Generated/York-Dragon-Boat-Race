package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.util.Vector;
import com.mygdx.dragonboatgame.entity.Boat;

import java.util.*;

public class BoatSelectionScreen extends AbstractScreen {


    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    private float lastValidMove = 0;
    private int currentBoatIndex;
    private String currentBoatName;
    private int[] currentBoatStats;
    private Object[] boatNames;
    private int[] maxStats = {0, 0, 0};
    private float timeSinceMove = 0.0f;
    private static HashMap<String, int[]> BOATS;


    public BoatSelectionScreen(final GameManager gameManager) {
        super(gameManager); //TODO code here
        currentBoatIndex = 0;
        camera = new OrthographicCamera(GameManager.WIDTH, GameManager.HEIGHT);
        camera.setToOrtho(false);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        //Gdx.files.internal("entity/boat.png")
    }


    public static Boat getBoat(String name) {
        if (!BOATS.containsKey(name)) throw new NullPointerException();
        int[] attrs = BOATS.get(name);
        return new Boat(name, attrs[0], attrs[1], attrs[2]);
    }

    @Override
    public void tick(float delta) {
        checkInput(delta);


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Boat.texture, GameManager.WIDTH / 6, GameManager.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        batch.draw(Boat.texture, GameManager.WIDTH *4.5f/ 6, GameManager.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        font.draw(batch, currentBoatName, GameManager.WIDTH / 2.5f, GameManager.HEIGHT / 1.2f);
        font.draw(batch, "Speed", GameManager.WIDTH / 6, GameManager.HEIGHT / 1.5f);
        font.draw(batch, "manovourability", GameManager.WIDTH / 6, GameManager.HEIGHT / 2f);
        font.draw(batch, "robustness", GameManager.WIDTH / 6, GameManager.HEIGHT / 3f);
        font.draw(batch, "Previous: S", GameManager.WIDTH / 6, GameManager.HEIGHT / 10f);
        font.draw(batch, "Next: D", GameManager.WIDTH / 1.5f, GameManager.HEIGHT / 10f);
        font.draw(batch, "Pick Your Boat", GameManager.WIDTH / 3.2f, GameManager.HEIGHT / 1.05f);
        font.draw(batch, "Press ENTER to select", GameManager.WIDTH / 4f, GameManager.HEIGHT / 5f);
        batch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); //42 32 15
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(GameManager.WIDTH / 1.8f, GameManager.HEIGHT / 1.6f, GameManager.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.rect(GameManager.WIDTH / 1.8f, GameManager.HEIGHT / 2.2f, GameManager.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.rect(GameManager.WIDTH / 1.8f, GameManager.HEIGHT / 3.4f, GameManager.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(GameManager.WIDTH / 1.8f, GameManager.HEIGHT / 1.6f, (GameManager.WIDTH / 3.5f) * ((float) currentBoatStats[0] / maxStats[0]), font.getCapHeight());
        shapeRenderer.rect(GameManager.WIDTH / 1.8f, GameManager.HEIGHT / 2.2f, (GameManager.WIDTH / 3.5f) * ((float) currentBoatStats[1] / maxStats[1]), font.getCapHeight());
        shapeRenderer.rect(GameManager.WIDTH / 1.8f, GameManager.HEIGHT / 3.4f, (GameManager.WIDTH / 3.5f) * ((float) currentBoatStats[2] / maxStats[2]), font.getCapHeight());
        shapeRenderer.end();
    }

    private void checkInput(float delta) {
        timeSinceMove += delta;
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean enter = Gdx.input.isKeyPressed(Input.Keys.ENTER);
        if (timeSinceMove >0.2f){
            if (left){
            timeSinceMove = 0;
            previousBoat();
            }
            if (right){
                timeSinceMove = 0;
                nextBoat();
            }
            if (enter){
                gameManager.sm.setScreen(ScreenManager.GAMESTATE.DragonBoatGame);
            }

        }
    }

    @Override
    public void show() {
        BOATS = gameManager.getAllBoats();
        boatNames= BOATS.keySet().toArray();
        currentBoatName = (String) boatNames[currentBoatIndex];
        currentBoatStats = BOATS.get(currentBoatName);
        maxStatsCalc();
    }
    private void previousBoat() {
        currentBoatIndex -= 1;
        if (currentBoatIndex == -1) {
            currentBoatIndex = boatNames.length - 1;
        }
        currentBoatName = (String) boatNames[currentBoatIndex];
        currentBoatStats = BOATS.get(currentBoatName);
    }
    private void nextBoat(){
        currentBoatIndex += 1;
        if (currentBoatIndex == boatNames.length){
            currentBoatIndex = 0;
        }
        currentBoatName = (String) boatNames[currentBoatIndex];
        currentBoatStats = BOATS.get(currentBoatName);
    }
    private void maxStatsCalc(){  //gets the highest possible specs of all the boats
        maxStats[0] = currentBoatStats[0];
        maxStats[1] = currentBoatStats[1];
        maxStats[2] = currentBoatStats[2];
        while (currentBoatIndex != boatNames.length -1){
            nextBoat();
            if (maxStats[0] < currentBoatStats[0]){
                maxStats[0]=currentBoatStats[0];
            }
            if (maxStats[1] < currentBoatStats[1])
            {maxStats[1]=currentBoatStats[1];
            }
            if (maxStats[2] < currentBoatStats[2]){
                maxStats[2]=currentBoatStats[2];
            }
        }
        nextBoat();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {



    }

    @Override
    public void hide() {

    }

}