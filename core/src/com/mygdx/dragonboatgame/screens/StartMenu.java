package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.dragonboatgame.game.Game;


public class StartMenu extends AbstractScreen {

    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private TextButton startButton;
    private Skin skin;

    private GlyphLayout dragonBoatGameText;


    public StartMenu(final GameManager gameManager){
        super(gameManager);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        this.stage = new Stage();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        startButton = new TextButton("Start!", skin);
        this.dragonBoatGameText = registerText(font, "York Dragon Boat Game");

        startButton.setSize(Game.WIDTH * 0.2f, Game.HEIGHT * 0.1f);
        startButton.setPosition(Game.WIDTH / 2 - this.startButton.getWidth() / 2, Game.HEIGHT * 0.35f);
        stage.addActor(startButton);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameManager.sm.setScreen(ScreenManager.GAMESTATE.TeamNameInput);
            }
        });
    }


    @Override
    public void tick(float delta) {}

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();

        font.draw(batch, dragonBoatGameText, Game.WIDTH / 2 - dragonBoatGameText.width / 2, Game.HEIGHT * 0.65f);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

}