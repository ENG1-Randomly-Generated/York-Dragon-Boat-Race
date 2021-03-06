package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.game.Team;

import java.util.ArrayList;
import java.util.Arrays;


public class MedalScreen extends AbstractScreen {

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Team[] positions;
    private boolean render;

    private GlyphLayout resultsText;
    private GlyphLayout enterText;


    public MedalScreen(final GameManager gameManager) {
        super(gameManager);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
        this.render = false;

        this.resultsText = registerText(font, "The results are in!");
        this.enterText = registerText(font, "Press ENTER to exit");
    }



    @Override
    public void tick(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.StartMenu);
        }
    }


    @Override
    public void render(float delta) {
        if (!render) return;

        super.render(delta);

        batch.begin();

        batch.draw(Boat.TEXTURE, Game.WIDTH / 6, Game.HEIGHT / 1.25f, Boat.SIZE.x, Boat.SIZE.y);
        batch.draw(Boat.TEXTURE, Game.WIDTH *4.5f/ 6, Game.HEIGHT / 1.25f, Boat.SIZE.x, Boat.SIZE.y);
        font.draw(batch, resultsText, Game.WIDTH / 2 - resultsText.width / 2, Game.HEIGHT * 0.8f);
        font.draw(batch, enterText, Game.WIDTH / 2 - enterText.width / 2, Game.HEIGHT * 0.1f);

        font.draw(batch, positions[0].name, Game.WIDTH / 2.4f, Game.HEIGHT / 1.5f);
        font.draw(batch, positions[1].name, Game.WIDTH / 2.4f, Game.HEIGHT / 2f);
        font.draw(batch, positions[2].name, Game.WIDTH / 2.4f, Game.HEIGHT / 3f);

        batch.end();


        shapeRenderer.begin();

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.circle(Game.WIDTH / 2.8f, Game.HEIGHT / 1.55f, 30);
        shapeRenderer.setColor(Color.LIGHT_GRAY );
        shapeRenderer.circle(Game.WIDTH / 2.8f, Game.HEIGHT / 2.1f, 30);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.circle(Game.WIDTH / 2.8f, Game.HEIGHT / 3.15f, 30);

        shapeRenderer.end();
    }



    @Override
    public void show() {
        // Calculate the positions of each team, and put em on the screen

        this.positions = new Team[Math.min(3, Game.getAllTeams().length)];

        ArrayList<Team> teams = new ArrayList<Team>(Arrays.asList(Game.getAllTeams()));

        int count = 0;
        while (count < this.positions.length) {
            float minTime = Float.MAX_VALUE;
            Team minTeam = null;
            for (Team team : teams) {
                if (team == null) continue;
                if (!team.hasQualified()) continue;
                if (team.getLegTime(Game.leg) <= minTime) {
                    minTime = team.getLegTime(Game.leg);
                    minTeam = team;
                }
            }
            if (minTeam != null) {
                this.positions[count++] = minTeam;
                teams.remove(minTeam);
            }
        }

        this.render = true;
    }



    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        this.render = false;
    }

}