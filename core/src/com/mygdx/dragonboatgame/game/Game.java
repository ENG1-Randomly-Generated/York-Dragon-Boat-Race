package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.dragonboatgame.DragonBoatGame;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.entity.obstacle.Duck;
import com.mygdx.dragonboatgame.entity.obstacle.Goose;
import com.mygdx.dragonboatgame.entity.obstacle.Log;
import com.mygdx.dragonboatgame.entity.obstacle.Rock;
import com.mygdx.dragonboatgame.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Represents a game instance, which holds entities, the player and the npcs and manages their drawing and input.
 *
 * @author Devon
 */
public class Game {

    public static final float WIDTH = Gdx.graphics.getWidth();
    public static final float HEIGHT = Gdx.graphics.getHeight();
    public static final float MAP_HEIGHT = 6000;
    public static final float GRASS_BORDER_WIDTH = WIDTH/8;

    private static HashMap<String, int[]> BOATS = new HashMap<String, int[]>();

    /*
     * Declare our predefined boats here and append to the master HashMap
     * Name (String) : Boat (Boat)
     */
    static {
        // {max_speed, manovourability, max_robustness)
        BOATS.put("Speedy", new int[] {22, 14, 2});
        BOATS.put("Twisty", new int[] {20, 19, 4});
        BOATS.put("Tanky", new int[] {18, 15, 10});
    }

    public static Random random = new Random();

    private int leg;
    private float time;

    private Player player;
    private ArrayList<NPC> npcs;
    private ArrayList<Entity> entities;
    private ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;

    /**
     * Constructor of a Game for the given Player
     * @param player Player of the game, the user
     */
    public Game(Player player) {
        this.leg = 0;
        this.time = 0;
        this.npcs = new ArrayList<NPC>(3);
        this.entities = new ArrayList<Entity>();
        this.player = player;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);

        this.camera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        camera.setToOrtho(false);
    }

    /**
     * Start a leg
     */
    public void startLeg() {
        float seperation = ((Game.WIDTH - (2 * GRASS_BORDER_WIDTH)) / (npcs.size() + 1));
        player.boat.setPos(new Vector(GRASS_BORDER_WIDTH, 0));
        player.boat.setPlaying(true);
        for (int i = 0; i < npcs.size(); i++) {
            NPC npc = npcs.get(i);
            npc.boat.setPos(new Vector(Game.GRASS_BORDER_WIDTH + (seperation * (i+1)), 0));
            npc.boat.setPlaying(true);
        }
    }


    /**
     *  The main tick of the game is run periodically
     *  Calls all of it's known entities' ticks, as well as checks for win conditions
     */
    public void tick() {
        player.tick();
        for (NPC npc : npcs) {
            npc.tick();
        }
        for (Entity entity: entities) {
            entity.tick();
        }

        // Update camera
        camera.position.set(camera.position.x, Math.min(Math.max(player.boat.getPos().y, Game.HEIGHT/2), Game.MAP_HEIGHT - Game.HEIGHT/2), 0);
        camera.update();
    }

    /**
     *  The main draw function for the game
     *      TODO: Here we shall draw the track
     *  Calls all of it's known entities' draw functions
     */
    public void draw() {

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(0, 0, GRASS_BORDER_WIDTH, Game.MAP_HEIGHT);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(GRASS_BORDER_WIDTH, 0, WIDTH -  (2 * GRASS_BORDER_WIDTH), Game.MAP_HEIGHT);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(Game.WIDTH - GRASS_BORDER_WIDTH, 0, GRASS_BORDER_WIDTH, Game.MAP_HEIGHT);
        shapeRenderer.end();


        player.boat.draw(camera);
        for (NPC npc: npcs) {
            npc.boat.draw(camera);
        }
        for (Entity entity: entities) {
            entity.draw(camera);
        }

    }


    public void addEntity(Entity entity) { this.entities.add(entity); }
    public void addNPC(NPC npc) {
        this.npcs.add(npc);
    }


    /**
     * Generates random obstacles across the course
     *
     * TODO: Random spread function, and actual implementation
     * @param n Number of obstacles to be placed (TODO: Remove, based on difficulty instead)
     */
    public void generateObstacles(int n) {
        for (int i = 0; i < n; i++) {
            Vector newPos = new Vector(random.nextInt((int)(Game.WIDTH - (2*Game.GRASS_BORDER_WIDTH))) + Game.GRASS_BORDER_WIDTH, random.nextInt((int)Game.MAP_HEIGHT));
            switch (Game.random.nextInt(4)) {
                case 1:
                    this.addEntity(new Duck(newPos));
                    break;
                case 2:
                    this.addEntity(new Goose(newPos));
                    break;
                case 3:
                    this.addEntity(new Log(newPos));
                    break;
                case 4:
                    this.addEntity(new Rock(newPos));
                    break;
            }
        }
    }

    /**
     * Generates n random NPCs, with random boats and random colours
     *
     * @param n Number of NPCs to create
     * @param difficulty Difficulty of NPCs out of 10 with 10 being very difficult
     */
    public void addRandomNPCs(int n, int difficulty) {
        for (int i = 0; i < n; i++) {
            NPC npc = new NPC("Bot" + npcs.size(), new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1), Game.getRandomBoat(), difficulty);
            this.npcs.add(npc);
        }
    }

    public void setPlayer(Player player) { this.player = player; }
    public Player getPlayer() { return this.player; }

    /**
     * Return a random predefined boat
     *
     * @return A random Boat
     */
    public static Boat getRandomBoat() {
        String randomBoat = (String) BOATS.keySet().toArray()[random.nextInt(BOATS.size() - 1)];
        int[] attrs = BOATS.get(randomBoat);
        return new Boat(randomBoat, attrs[0], attrs[1], attrs[2]);
    }

    public static Boat getBoat(String name) {
        if (!BOATS.containsKey(name)) throw new NullPointerException();
        int[] attrs = BOATS.get(name);
        return new Boat(name, attrs[0], attrs[1], attrs[2]);
    }



}
