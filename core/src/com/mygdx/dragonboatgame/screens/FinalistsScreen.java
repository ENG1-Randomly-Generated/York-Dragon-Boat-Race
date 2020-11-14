package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.game.Team;

public class FinalistsScreen extends AbstractScreen {


    private SpriteBatch batch;
    private BitmapFont font;
    private float teamSepY;


    public FinalistsScreen(final GameManager gameManager) { //int legNumber, Team[] teamsArray
        super(gameManager);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        this.teamSepY = 0;
    }



    @Override
    public void tick(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (Game.player.hasQualified()) {
                gameManager.sm.setScreen(ScreenManager.GAMESTATE.DragonBoatGame);
            } else {
                System.out.println("You did not qualify"); // TODO: Screen for this :)
                System.exit(0);
            }
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(Boat.texture, Game.WIDTH / 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        batch.draw(Boat.texture, Game.WIDTH *4.5f/ 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        font.draw(batch, "The Finalists are", Game.WIDTH / 3.5f, Game.HEIGHT * 0.95f);
        font.draw(batch, "Press ENTER to continue", Game.WIDTH / 4f, Game.HEIGHT * 0.08f);

        int count = 0;
        for (Team team : Game.getAllTeams()) {
            if (!team.hasQualified()) continue; // Sorry bud ;(
            font.draw(batch, team.name, Game.WIDTH / 2.4f, Game.HEIGHT * 0.4f + (teamSepY * count++));
        }

        batch.end();
    }



    @Override
    public void show() {
        this.teamSepY = (float) ((Game.HEIGHT * 0.6) / Game.getAllTeams().length);
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