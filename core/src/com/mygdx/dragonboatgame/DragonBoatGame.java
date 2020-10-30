package com.mygdx.dragonboatgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.dragonboatgame.game.Game;
import com.mygdx.dragonboatgame.game.Player;

// This is purely for testing and will be changed to a proper Screen system later
public class DragonBoatGame extends ApplicationAdapter {

	Game game;
	Player player;

	@Override
	public void create () {
		player = new Player("Player", Color.BLUE, Game.getBoat("Speedy"));
		game = new Game(player);
		game.addRandomNPCs(3, 5);
		game.generateObstacles(100);
		game.startLeg();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.tick();
		game.draw();
	}
	
	@Override
	public void dispose () {
	}
}
