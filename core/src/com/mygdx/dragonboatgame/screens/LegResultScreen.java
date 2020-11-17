package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.game.Team;

import java.text.DecimalFormat;


public class LegResultScreen extends AbstractScreen {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private SpriteBatch batch;
    private BitmapFont font;
    private float teamSepY;
    private Team[] teams;


    public LegResultScreen(final GameManager gameManager) { //int legNumber, Team[] teamsArray
        super(gameManager);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        this.teamSepY = 0;
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
        batch.begin();
        batch.draw(Boat.texture, Game.WIDTH / 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        batch.draw(Boat.texture, Game.WIDTH *4.5f/ 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        font.draw(batch, "Time", Game.WIDTH * 0.3f, Game.HEIGHT / 1.3f);
        font.draw(batch, "Penalty", Game.WIDTH * 0.45f, Game.HEIGHT / 1.3f);
        font.draw(batch, "Result", Game.WIDTH * 0.68f, Game.HEIGHT / 1.3f);
        font.draw(batch, "Leg " + Game.leg, Game.WIDTH / 2.3f, Game.HEIGHT * 0.95f);
        font.draw(batch, "Press ENTER to continue", Game.WIDTH / 4f, Game.HEIGHT * 0.08f);

        for (int i = 0; i < teams.length; i++) {
            Team team = teams[i];
            float height = Game.HEIGHT * 0.2f + (teamSepY * i);

            font.draw(batch, team.name, Game.WIDTH * 0.14f, height);

            float legTime = team.getLegTime(Game.leg); // Leg time INKL Penalty
            float penalty = team.getPenalty();

            if (legTime == Float.MAX_VALUE) {
                font.draw(batch, "DNF", Game.WIDTH * 0.29f, height);
                font.draw(batch, "DNF", Game.WIDTH * 0.46f, height);
                font.draw(batch, "DNF", Game.WIDTH * 0.7f, height);
            } else {
                font.draw(batch, DECIMAL_FORMAT.format(legTime - penalty) + "s", Game.WIDTH * 0.29f, height);
                font.draw(batch, DECIMAL_FORMAT.format(penalty) + "s", Game.WIDTH * 0.46f, height);
                font.draw(batch, DECIMAL_FORMAT.format(legTime) + "s", Game.WIDTH * 0.7f, height);
            }
        }
        batch.end();
    }



    @Override
    public void show() {
        this.teamSepY = (float) ((Game.HEIGHT * 0.6) / Game.getAllTeams().length);
        this.teams = Game.getAllTeams();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        this.teams = new Team[0];
        this.teamSepY = 0;
    }

}