package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.game.Game;


public class MedalScreen extends AbstractScreen {

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private float time = 0;


    public MedalScreen(final GameManager gameManager) { //int legNumber, Team[] teamsArray
        super(gameManager);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/largefont.fnt"));
    }



    @Override
    public void tick(float delta) {
        time += delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && time > 1.5f) {
            gameManager.sm.setScreen(ScreenManager.GAMESTATE.DragonBoatGame);
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(Boat.texture, Game.WIDTH / 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        batch.draw(Boat.texture, Game.WIDTH *4.5f/ 6, Game.HEIGHT / 1.25f, Boat.size.x, Boat.size.y);
        font.draw(batch, "The results are in!", Game.WIDTH / 3.5f, Game.HEIGHT / 1.1f);
        font.draw(batch, "Press ENTER to exit", Game.WIDTH / 4f, Game.HEIGHT / 5.5f);
        font.draw(batch, "BotTwisty", Game.WIDTH / 2.4f, Game.HEIGHT / 1.5f);
        font.draw(batch, "BotSpeedy", Game.WIDTH / 2.4f, Game.HEIGHT / 2f);
        font.draw(batch, "Faggy", Game.WIDTH / 2.4f, Game.HEIGHT / 3f);
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