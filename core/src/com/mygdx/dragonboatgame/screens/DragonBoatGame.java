package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.game.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;



public class DragonBoatGame extends AbstractScreen{

    Game game;
    Player player;


    public DragonBoatGame(final GameManager gameManager){
        super(gameManager);
        player = new Player("Player", Color.BLUE, Game.getBoat("Speedy"));
        game = new Game(player);
        game.addRandomNPCs(3, 5);
        game.startLeg(1);
    }


    @Override
    public void tick(float delta) {}

    @Override
    public void render(float delta) {
        game.tick();
        game.draw();
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