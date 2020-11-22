package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.game.Team;
import com.mygdx.dragonboatgame.util.Vector;

public class ControlsScreen extends AbstractScreen {

    private SpriteBatch batch;
    private BitmapFont font;
    private Boat exampleBoat;
    private OrthographicCamera camera;

    private GlyphLayout title;
    private GlyphLayout wasdInfo;
    private GlyphLayout energyInfo;
    private GlyphLayout robustnessInfo;
    private GlyphLayout pressEnter;

    public ControlsScreen(GameManager gameManager) {
        super(gameManager);
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        camera.setToOrtho(false);
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        this.title = registerText(font, "Controls");
        this.wasdInfo = registerText(font, "Use WASD to control your boat");
        this.energyInfo = registerText(font, "Energy is displayed by the yellow bar");
        this.robustnessInfo = registerText(font, "Robustness is displayed by the red bar");
        this.pressEnter = registerText(font, "Press ENTER to continue");
    }

    @Override
    public void tick(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.DragonBoatGame);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        exampleBoat.draw(camera);

        batch.begin();

        font.draw(batch, title, Game.WIDTH * 0.5f - title.width / 2, Game.HEIGHT * 0.95f);
        font.draw(batch, wasdInfo, Game.WIDTH * 0.55f - wasdInfo.width / 2, Game.HEIGHT * 0.55f);
        font.draw(batch, energyInfo, Game.WIDTH * 0.55f - energyInfo.width / 2, Game.HEIGHT * 0.45f);
        font.draw(batch, robustnessInfo, Game.WIDTH * 0.55f - robustnessInfo.width / 2, Game.HEIGHT * 0.35f);
        font.draw(batch, pressEnter, Game.WIDTH * 0.5f - pressEnter.width / 2, Game.HEIGHT * 0.1f);

        batch.end();
    }

    @Override
    public void show() {
        this.exampleBoat = new Boat("Example", 0, 0, 10, 0);
        this.exampleBoat.setSize(new Vector(Game.WIDTH * 0.1f, Game.HEIGHT * 0.4f));
        this.exampleBoat.setPos(new Vector(Game.WIDTH * 0.2f, Game.HEIGHT * 0.2f));
        this.exampleBoat.setTeam_name("Player");
        this.exampleBoat.setVisible(true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
