package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.dragonboatgame.game.Game;


public class TeamNameInputScreen extends AbstractScreen {

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private TextField textField;
    private Skin skin;


    public TeamNameInputScreen(final GameManager gameManager){
        super(gameManager);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        textField = new TextField("", skin);
    }


    @Override
    public void tick(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && textField.getText().length() > 0){
            Game.player.setName(textField.getText());
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.BoatSelection);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        font.draw(batch, "ENTER TEAM NAME", Game.WIDTH/3.5f, Game.HEIGHT/1.5f);
        font.draw(batch, "Press ENTER to continue", Game.WIDTH / 4f, Game.HEIGHT / 5f);
        batch.end();
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void show() {
        textField.setPosition(Game.WIDTH/2.8f, Game.HEIGHT/2.2f);
        textField.setSize(Game.WIDTH/4,Game.HEIGHT/10);
        textField.setMaxLength(8);
        stage.addActor(textField);
        stage.setKeyboardFocus(textField);
    }


    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

}