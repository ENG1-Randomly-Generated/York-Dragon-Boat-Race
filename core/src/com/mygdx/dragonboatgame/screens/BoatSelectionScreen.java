package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;

import java.util.*;

public class BoatSelectionScreen extends AbstractScreen {


    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    private int currentBoatIndex;
    private String currentBoatName;
    private int[] currentBoatStats;
    private String[] boatNames;
    private int[] maxStats = {0, 0, 0};
    private static HashMap<String, int[]> BOATS;


    public BoatSelectionScreen(final GameManager gameManager) {
        super(gameManager); //TODO code here
        currentBoatIndex = 0;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        //Gdx.files.internal("entity/boat.png")
    }


    @Override
    public void tick(float delta) {
        checkInput();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        
        batch.draw(Boat.texture, Game.WIDTH / 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        batch.draw(Boat.texture, Game.WIDTH *4.5f/ 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        font.draw(batch, currentBoatName, Game.WIDTH / 2.5f, Game.HEIGHT / 1.2f);
        font.draw(batch, "Speed", Game.WIDTH / 6, Game.HEIGHT / 1.5f);
        font.draw(batch, "Manovourability", Game.WIDTH / 6, Game.HEIGHT / 2f);
        font.draw(batch, "Robustness", Game.WIDTH / 6, Game.HEIGHT / 3f);
        font.draw(batch, "Previous: S", Game.WIDTH / 6, Game.HEIGHT / 10f);
        font.draw(batch, "Next: D", Game.WIDTH / 1.5f, Game.HEIGHT / 10f);
        font.draw(batch, "Pick Your Boat", Game.WIDTH / 3.2f, Game.HEIGHT / 1.05f);
        font.draw(batch, "Press ENTER to select", Game.WIDTH / 4f, Game.HEIGHT / 5f);
        
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); //42 32 15
        
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT / 1.6f, Game.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT / 2.2f, Game.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT / 3.4f, Game.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT / 1.6f, (Game.WIDTH / 3.5f) * ((float) currentBoatStats[0] / maxStats[0]), font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT / 2.2f, (Game.WIDTH / 3.5f) * ((float) currentBoatStats[1] / maxStats[1]), font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT / 3.4f, (Game.WIDTH / 3.5f) * ((float) currentBoatStats[2] / maxStats[2]), font.getCapHeight());
        
        shapeRenderer.end();
    }

    private void checkInput() {
        boolean right = Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT);
        boolean left = Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT);
        boolean enter = Gdx.input.isKeyJustPressed(Input.Keys.ENTER);

        if (left) {
            previousBoat();
        } else if (right) {
            nextBoat();
        } else if (enter) {
            // Set the boat of the player here
            Game.player.setBoat(Game.getNewBoat(currentBoatName));
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.DragonBoatGame);
        }
    }

    @Override
    public void show() {
        BOATS = Game.getBoats();
        boatNames = BOATS.keySet().toArray(new String[BOATS.keySet().size()]);
        currentBoatName = boatNames[currentBoatIndex];
        currentBoatStats = BOATS.get(currentBoatName);
        this.maxStatsCalc();
    }

    private void previousBoat() {
        currentBoatIndex -= 1;
        if (currentBoatIndex == -1) {
            currentBoatIndex = boatNames.length - 1;
        }
        currentBoatName = boatNames[currentBoatIndex];
        currentBoatStats = BOATS.get(currentBoatName);
    }

    private void nextBoat(){
        currentBoatIndex += 1;
        if (currentBoatIndex == boatNames.length){
            currentBoatIndex = 0;
        }
        currentBoatName = boatNames[currentBoatIndex];
        currentBoatStats = BOATS.get(currentBoatName);
    }

    private void maxStatsCalc() {  //gets the highest possible specs of all the boats
        maxStats[0] = currentBoatStats[0];
        maxStats[1] = currentBoatStats[1];
        maxStats[2] = currentBoatStats[2];
        while (currentBoatIndex != boatNames.length - 1){
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