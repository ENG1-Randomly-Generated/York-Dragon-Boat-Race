package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.game.Team;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class LegResultScreen extends AbstractScreen {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private SpriteBatch batch;
    private BitmapFont font;
    private float teamSepY;
    private Team[] teams;

    private GlyphLayout legText;
    private GlyphLayout timeText;
    private GlyphLayout penaltyText;
    private GlyphLayout resultText;
    private GlyphLayout pressEnterText;

    public LegResultScreen(final GameManager gameManager) {
        super(gameManager);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        this.teamSepY = 0;

        this.legText = registerText(font, "Leg: undefined");
        this.timeText = registerText(font, "Time");
        this.penaltyText = registerText(font, "Penalty");
        this.resultText = registerText(font, "Result");
        this.pressEnterText = registerText(font, "Press ENTER to continue");
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

        font.draw(batch, legText, Game.WIDTH / 2 - legText.width / 2, Game.HEIGHT * 0.9f);
        font.draw(batch, timeText, Game.WIDTH * 0.33f - timeText.width / 2, Game.HEIGHT * 0.75f);
        font.draw(batch, penaltyText, Game.WIDTH * 0.55f - penaltyText.width / 2, Game.HEIGHT * 0.75f);
        font.draw(batch, resultText, Game.WIDTH * 0.75f - resultText.width / 2, Game.HEIGHT * 0.75f);
        font.draw(batch, pressEnterText, Game.WIDTH / 2 - pressEnterText.width / 2, Game.HEIGHT * 0.1f);

        for (int i = 0; i < teams.length; i++) {
            Team team = teams[i];
            float height = Game.HEIGHT * 0.65f - (teamSepY * i);

            font.draw(batch, team.name, Game.WIDTH * 0.14f, height);

            float legTime = team.getLegTime(Game.leg); // Leg time INKL Penalty
            float penalty = team.getPenalty();

            if (legTime == Float.MAX_VALUE) {
                font.draw(batch, "DNF", Game.WIDTH * 0.29f, height);
                font.draw(batch, "DNF", Game.WIDTH * 0.46f, height);
                font.draw(batch, "DNF", Game.WIDTH * 0.7f, height);
            } else {
                font.draw(batch, DECIMAL_FORMAT.format(legTime - penalty) + "s", Game.WIDTH * 0.29f, height);
                font.draw(batch, DECIMAL_FORMAT.format(penalty) + "s", Game.WIDTH * 0.47f, height);
                font.draw(batch, DECIMAL_FORMAT.format(legTime) + "s", Game.WIDTH * 0.69f, height);
            }
        }
        batch.end();
    }



    @Override
    public void show() {
        this.legText = registerText(font, "Leg: " + Game.leg); // Redefined our text
        this.teamSepY = (float) ((Game.HEIGHT * 0.6) / Game.getAllTeams().length);

        // Sort teams in order of time
        ArrayList<Team> teams = new ArrayList<Team>(Arrays.asList(Game.getAllTeams()));
        Collections.sort(teams, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                return Float.compare(o1.getLegTime(Game.leg), o2.getLegTime(Game.leg));
            }
        });
        this.teams = new Team[teams.size()];
        this.teams = teams.toArray(this.teams);
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