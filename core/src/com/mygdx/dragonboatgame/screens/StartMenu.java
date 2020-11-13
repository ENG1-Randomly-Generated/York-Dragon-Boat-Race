package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.game.Game;


public class StartMenu extends AbstractScreen{

    private SpriteBatch batch;
    private BitmapFont font;


    public StartMenu(final GameManager gameManager){
        super(gameManager); //TODO code here
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
    }


    @Override
    public void tick(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.BoatSelection);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        font.draw(batch, "PRESS ENTER TO PLAY", Game.WIDTH/3.5f, Game.HEIGHT/2);  //TODO make sure this looks good on all screens and aspect ratios
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