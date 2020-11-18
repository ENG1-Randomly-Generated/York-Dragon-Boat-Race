package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.mygdx.dragonboatgame.game.Game;

public abstract class AbstractScreen implements Screen {

    protected final GameManager gameManager;

    private ShapeRenderer shapeRenderer;

    public AbstractScreen(final GameManager gameManager) {
        this.gameManager = gameManager;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    /**
     * Returns a GlyphLayout for the given font and text
     *  This allows us to retrieve information such as width and height
     *  Render this with a batch and font with font.draw(batch, glyph)
     *
     *  DO NOT run this in the render() thread, keep the object as an attribute
     * @param font Font to be used to render text
     * @param text Text to be rendered
     * @return Glyphlayout to be stored
     */
    protected GlyphLayout registerText(BitmapFont font, String text) {
        return new GlyphLayout(font, text);
    }

    public abstract void tick(float delta);

    public void render(float delta) {
        // Draw background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin();

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Game.GRASS_COLOR);
        shapeRenderer.rect(0, 0, Game.GRASS_BORDER_WIDTH, Game.HEIGHT);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(Game.GRASS_BORDER_WIDTH, 0, Game.WIDTH -  (2 * Game.GRASS_BORDER_WIDTH), Game.HEIGHT);
        shapeRenderer.setColor(Game.GRASS_COLOR);
        shapeRenderer.rect(com.mygdx.dragonboatgame.game.Game.WIDTH - Game.GRASS_BORDER_WIDTH, 0, Game.GRASS_BORDER_WIDTH, Game.HEIGHT);

        shapeRenderer.end();

        tick(delta);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void dispose() {}
}
