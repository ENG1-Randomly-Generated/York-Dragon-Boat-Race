package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;

public class FinalistsScreen extends AbstractScreen {


    private SpriteBatch batch;
    private BitmapFont font;
    private float time = 0;


    public FinalistsScreen(final GameManager gameManager) { //int legNumber, Team[] teamsArray
        super(gameManager);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
    }



    @Override
    public void tick(float delta) {
        time += delta;
        if (getEnter() && time > 1.5f) {
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.Medals);
        }
    }

    private boolean getEnter() {
        return Gdx.input.isKeyPressed(Input.Keys.ENTER);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(Boat.texture, Game.WIDTH / 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        batch.draw(Boat.texture, Game.WIDTH *4.5f/ 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        font.draw(batch, "The Finalists are", Game.WIDTH / 3.5f, Game.HEIGHT / 1.1f);
        font.draw(batch, "Press ENTER to continue", Game.WIDTH / 4f, Game.HEIGHT / 5.5f);
        font.draw(batch, "Twisty", Game.WIDTH / 2.4f, Game.HEIGHT / 1.5f);
        font.draw(batch, "Speedy", Game.WIDTH / 2.4f, Game.HEIGHT / 2f);
        font.draw(batch, "Faggy", Game.WIDTH / 2.4f, Game.HEIGHT / 3f);
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