package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.dragonboatgame.game.Game;


public class TeamNameInputScreen extends AbstractScreen{

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private TextField textField;
    private Skin skin;
    private float time = 0;


    public TeamNameInputScreen(final GameManager gameManager){
        super(gameManager); //TODO code here
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        textField = new TextField("", skin);
    }


    @Override
    public void tick(float delta) {
        time +=delta;
        if (getEnter() && time>1.5f){
            //TODO set textField to be equal to teamname
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.LegResult);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        font.draw(batch, "ENTER TEAM NAME", Game.WIDTH/3.5f, Game.HEIGHT/1.5f);  //TODO make sure this looks good on all screens and aspect ratios
        font.draw(batch, "Press ENTER to continue", Game.WIDTH / 4f, Game.HEIGHT / 5f);
        batch.end();
        stage.act(delta);
        stage.draw();

    }

    private boolean getEnter() {
        return Gdx.input.isKeyPressed(Input.Keys.ENTER);
    }

    @Override
    public void show() {
        textField.setPosition(Game.WIDTH/2.8f, Game.HEIGHT/2.2f);
        textField.setSize(Game.WIDTH/4,Game.HEIGHT/10);
        textField.setMaxLength(9);
        stage.addActor(textField);
        stage.setKeyboardFocus(textField);
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