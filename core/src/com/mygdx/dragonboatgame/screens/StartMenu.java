package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;


public class StartMenu extends AbstractScreen{

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;


    public StartMenu(final GameManager gameManager){
        super(gameManager); //TODO code here
        camera = new OrthographicCamera(GameManager.WIDTH, GameManager.HEIGHT);
        camera.setToOrtho(false);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
    }


    @Override
    public void tick(float delta) {
        if (getEnter()){
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.BoatSelection);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        font.draw(batch, "PRESS ENTER TO PLAY", GameManager.WIDTH/3.5f, GameManager.HEIGHT/2);  //TODO make sure this looks good on all screens and aspect ratios
        batch.end();
    }

    private boolean getEnter() {
        return Gdx.input.isKeyPressed(Input.Keys.ENTER);
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