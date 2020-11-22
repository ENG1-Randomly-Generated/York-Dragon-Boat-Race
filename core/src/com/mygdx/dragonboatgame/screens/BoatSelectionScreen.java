package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
    private int[] maxStats = {0, 0, 0, 0};
    private static HashMap<String, int[]> BOATS;

    private GlyphLayout pickYourBoatText;
    private GlyphLayout boatNameText;
    private GlyphLayout pressEnterText;


    public BoatSelectionScreen(final GameManager gameManager) {
        super(gameManager);
        currentBoatIndex = 0;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));

        this.pickYourBoatText = registerText(font, "Pick Your Boat");
        this.boatNameText = registerText(font, "Undefined");
        this.pressEnterText = registerText(font, "Press ENTER to select");
    }


    @Override
    public void tick(float delta) {
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
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.Controls);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        
        batch.draw(Boat.TEXTURE, Game.WIDTH / 6, Game.HEIGHT / 1.25f, Boat.SIZE.x, Boat.SIZE.y);
        batch.draw(Boat.TEXTURE, Game.WIDTH *4.5f/ 6, Game.HEIGHT / 1.25f, Boat.SIZE.x, Boat.SIZE.y);
        font.draw(batch, pickYourBoatText, Game.WIDTH / 2 - pickYourBoatText.width / 2, Game.HEIGHT / 1.05f);
        font.draw(batch, boatNameText, Game.WIDTH / 2 - boatNameText.width / 2, Game.HEIGHT / 1.2f);
        font.draw(batch, "Speed", Game.WIDTH / 6, Game.HEIGHT * 0.7f);
        font.draw(batch, "Manoeuvrability", Game.WIDTH / 6, Game.HEIGHT * 0.6f);
        font.draw(batch, "Robustness", Game.WIDTH / 6, Game.HEIGHT * 0.5f);
        font.draw(batch, "Acceleration", Game.WIDTH / 6, Game.HEIGHT * 0.4f);
        font.draw(batch, "Previous: A", Game.WIDTH / 6, Game.HEIGHT / 10f);
        font.draw(batch, "Next: D", Game.WIDTH / 1.5f, Game.HEIGHT / 10f);
        font.draw(batch, pressEnterText, Game.WIDTH / 2 - pressEnterText.width / 2, Game.HEIGHT / 5f);
        
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT * 0.7f - font.getCapHeight(), Game.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT * 0.6f - font.getCapHeight(), Game.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT * 0.5f - font.getCapHeight(), Game.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT * 0.4f - font.getCapHeight(), Game.WIDTH / 3.5f, font.getCapHeight());
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT * 0.7f - font.getCapHeight(), (Game.WIDTH / 3.5f) * ((float) currentBoatStats[0] / maxStats[0]), font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT * 0.6f - font.getCapHeight(), (Game.WIDTH / 3.5f) * ((float) currentBoatStats[1] / maxStats[1]), font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT * 0.5f - font.getCapHeight(), (Game.WIDTH / 3.5f) * ((float) currentBoatStats[2] / maxStats[2]), font.getCapHeight());
        shapeRenderer.rect(Game.WIDTH / 1.8f, Game.HEIGHT * 0.4f - font.getCapHeight(), (Game.WIDTH / 3.5f) * ((float) currentBoatStats[3] / maxStats[3]), font.getCapHeight());
        
        shapeRenderer.end();
    }


    /**
     * Sets the currently displayed boat to the one at given index
     * @param index Index of boat
     */
    private void displayBoat(int index) {
        currentBoatName = boatNames[index];
        currentBoatStats = BOATS.get(currentBoatName);
        boatNameText = registerText(font, currentBoatName);
    }

    @Override
    public void show() {
        BOATS = Game.getBoats();
        boatNames = BOATS.keySet().toArray(new String[BOATS.keySet().size()]);
        displayBoat(currentBoatIndex);
        this.maxStatsCalc();
    }

    private void previousBoat() {
        currentBoatIndex -= 1;
        if (currentBoatIndex == -1) {
            currentBoatIndex = boatNames.length - 1;
        }
        displayBoat(currentBoatIndex);
    }

    private void nextBoat(){
        currentBoatIndex += 1;
        if (currentBoatIndex == boatNames.length){
            currentBoatIndex = 0;
        }
        displayBoat(currentBoatIndex);
    }


    /**
     * Calculate the maximum of each attribute for the boats
     *  i.e. find what the maximum speed is among all boats, so we can display the stat bars relatively
     *
     */
    private void maxStatsCalc() {
        maxStats = new int[] {0,0,0,0};
        for (Map.Entry<String, int[]> entry : Game.getBoats().entrySet()) {
            int[] vals = entry.getValue();
            for (int i = 0; i < 4; i++) {
                maxStats[i] = Math.max(maxStats[i], vals[i]);
            }
        }
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