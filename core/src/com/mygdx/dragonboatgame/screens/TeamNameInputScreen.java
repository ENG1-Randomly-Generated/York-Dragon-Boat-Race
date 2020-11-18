package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

    private GlyphLayout enterTeamNameText;
    private GlyphLayout pressEnterText;


    public TeamNameInputScreen(final GameManager gameManager){
        super(gameManager);
        stage = new Stage();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        textField = new TextField("", skin);

        textField.setSize(Game.WIDTH * 0.33f,Game.HEIGHT * 0.05f);
        textField.setPosition(Game.WIDTH / 2 - textField.getWidth() / 2, Game.HEIGHT * 0.4f);
        textField.setMaxLength(5);
        stage.addActor(textField);

        enterTeamNameText = registerText(font, "Enter your team name:");
        pressEnterText = registerText(font, "Press ENTER to continue");
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
        font.draw(batch, enterTeamNameText, Game.WIDTH / 2 - enterTeamNameText.width / 2, Game.HEIGHT * 0.6f);
        font.draw(batch, pressEnterText, Game.WIDTH / 2 - pressEnterText.width / 2, Game.HEIGHT * 0.1f);
        batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void show() {
        stage.setKeyboardFocus(textField);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

}