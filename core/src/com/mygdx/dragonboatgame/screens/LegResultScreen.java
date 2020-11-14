package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;


public class LegResultScreen extends AbstractScreen {


    private SpriteBatch batch;
    private BitmapFont font;
    private int leg;
    private float time = 0;


    public LegResultScreen(final GameManager gameManager) { //int legNumber, Team[] teamsArray
        super(gameManager);
        leg = 1; //TODO change this
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
    }



    @Override
    public void tick(float delta) {
        time += delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && time > 1.5f) {
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.Finalists);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(Boat.texture, Game.WIDTH / 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        batch.draw(Boat.texture, Game.WIDTH *4.5f/ 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        font.draw(batch, "Time", Game.WIDTH / 2.8f, Game.HEIGHT / 1.3f);
        font.draw(batch, "Penalty", Game.WIDTH / 1.9f, Game.HEIGHT / 1.3f);
        font.draw(batch, "Result", Game.WIDTH / 1.36f, Game.HEIGHT / 1.3f);
        font.draw(batch, "Leg "+leg, Game.WIDTH / 2.3f, Game.HEIGHT / 1.1f);
        font.draw(batch, "Press ENTER to continue", Game.WIDTH / 4f, Game.HEIGHT / 5.5f);
        font.draw(batch, "Twisty", Game.WIDTH / 7.65f, Game.HEIGHT / 1.5f);
        font.draw(batch, "BotSpeedy", Game.WIDTH / 7.65f, Game.HEIGHT / 2f);
        font.draw(batch, "Faggy", Game.WIDTH / 7.65f, Game.HEIGHT / 3f);
        font.draw(batch, "46.35", Game.WIDTH / 2.8f, Game.HEIGHT / 1.5f);
        font.draw(batch, "112.12", Game.WIDTH / 2.8f, Game.HEIGHT / 2f);
        font.draw(batch, "47.35", Game.WIDTH / 2.8f, Game.HEIGHT / 3f);
        font.draw(batch, "46.35", Game.WIDTH / 1.9f, Game.HEIGHT / 1.5f);
        font.draw(batch, "112.12", Game.WIDTH / 1.9f, Game.HEIGHT / 2f);
        font.draw(batch, "47.35", Game.WIDTH / 1.9f, Game.HEIGHT / 3f);
        font.draw(batch, "46.35", Game.WIDTH / 1.36f, Game.HEIGHT / 1.5f);
        font.draw(batch, "212.12", Game.WIDTH / 1.36f, Game.HEIGHT / 2f);
        font.draw(batch, "47.35", Game.WIDTH / 1.36f, Game.HEIGHT / 3f);
        batch.end();
    }



    @Override
    public void show() {

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