package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragonboatgame.game.Game;

public class LostScreen extends AbstractScreen {

    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout youLostText;
    private GlyphLayout pressEnterText;

    public LostScreen(GameManager gameManager) {
        super(gameManager);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));

        this.youLostText = registerText(font, "You lost!");
        this.pressEnterText = registerText(font, "Press ENTER to continue");
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        font.draw(batch, youLostText, (Game.WIDTH / 2) - youLostText.width / 2, Game.HEIGHT/2);
        font.draw(batch, pressEnterText, (Game.WIDTH / 2) - pressEnterText.width / 2, Game.HEIGHT * 0.1f);
        batch.end();

    }

    @Override
    public void tick(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.StartMenu);
        }
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
