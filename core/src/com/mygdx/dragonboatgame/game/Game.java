package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.dragonboatgame.DragonBoatGame;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.entity.obstacle.Duck;
import com.mygdx.dragonboatgame.entity.obstacle.Goose;
import com.mygdx.dragonboatgame.entity.obstacle.Log;
import com.mygdx.dragonboatgame.entity.obstacle.Rock;
import com.mygdx.dragonboatgame.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents a game instance, which holds entities, the player and the npcs and manages their drawing and input.
 *
 * @author Devon
 */
public class Game {

    public static final float WIDTH = Gdx.graphics.getWidth();
    public static final float HEIGHT = Gdx.graphics.getHeight();
    public static final float MAP_HEIGHT = 3000;
    public static final float GRASS_BORDER_WIDTH = WIDTH/8;

    private static HashMap<String, int[]> BOATS = new HashMap<String, int[]>();

    /*
     * Declare our predefined boats here and append to the master HashMap
     * Name (String) : Boat (Boat)
     */
    static {
        // {max_speed, manovourability, max_robustness)
        BOATS.put("Speedy", new int[] {42, 30, 10});
        BOATS.put("Twisty", new int[] {40, 32, 10});
        BOATS.put("Tanky", new int[] {38, 27, 15});
    }

    public static Random random = new Random();

    private int leg;
    private float time;

    private Player player;
    private ArrayList<NPC> npcs;
    private ArrayList<Entity> entities;
    private HashMap<Team, Float[]> laneDividers;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    private OrthographicCamera camera;

    /**
     * Constructor of a Game for the given Player
     * @param player Player of the game, the user
     */
    public Game(Player player) {
        this.leg = 0;
        this.time = 0;
        this.npcs = new ArrayList<NPC>(3);
        this.laneDividers = new HashMap<Team, Float[]>();
        this.entities = new ArrayList<Entity>();
        this.player = player;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();

        this.camera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        camera.setToOrtho(false);
    }

    /**
     * Start a leg
     */
    public void startLeg() {
        float seperation = ((Game.WIDTH - (2 * GRASS_BORDER_WIDTH)) / (npcs.size() + 1));
        player.boat.setPos(new Vector(GRASS_BORDER_WIDTH, 0));
        laneDividers.put(player, new Float[] {GRASS_BORDER_WIDTH, GRASS_BORDER_WIDTH + seperation});
        player.boat.setPlaying(true);
        for (int i = 0; i < npcs.size(); i++) {
            NPC npc = npcs.get(i);
            laneDividers.put(npc, new Float[] {GRASS_BORDER_WIDTH + (seperation * (i)), GRASS_BORDER_WIDTH + (seperation * (i+1))});
            npc.boat.setPos(new Vector(Game.GRASS_BORDER_WIDTH + (seperation * (i+1)), 0));
            npc.boat.setPlaying(true);
        }
        this.time = 0;
    }


    /**
     *  The main tick of the game is run periodically
     *  Calls all of it's known entities' ticks, as well as checks for win conditions
     */
    public void tick() {
        float delta = Gdx.graphics.getDeltaTime();
        time += delta;

        player.tick(delta);
        for (NPC npc : npcs) {
            npc.tick(delta);
        }
        for (Entity entity: entities) {
            entity.tick(delta);
        }

        // Check lane divisions & add penalties where necessary
        for (Map.Entry<Team, Float[]> entry : laneDividers.entrySet()) {
            Team team = entry.getKey();
            Boat boat = team.boat;
            Float[] aPos = entry.getValue();
            if (boat.getPos().x < aPos[0] || boat.getPos().x + boat.getSize().x > aPos[1]) {
                team.addPenalty(delta * 2); // TODO: Make this scalable properly with difficulty or something
            }
        }

        // Update camera
        camera.position.set(camera.position.x, Math.min(Math.max(player.boat.getPos().y, Game.HEIGHT/2), Game.MAP_HEIGHT - Game.HEIGHT/2), 0);
        camera.update();

        // Debug
        // TODO: Remove this
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            Entity.DEBUG_HITBOXES = !Entity.DEBUG_HITBOXES;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            Class cls = Boat.class;
            try {
                Field energy = cls.getDeclaredField("energy");
                energy.setAccessible(true);
                energy.set(player.boat, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     *  The main draw function for the game
     *
     *  Calls all of it's known entities' draw functions
     */
    public void draw() {

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();

        // Draw basic scene

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(0, 0, GRASS_BORDER_WIDTH, Game.MAP_HEIGHT);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(GRASS_BORDER_WIDTH, 0, WIDTH -  (2 * GRASS_BORDER_WIDTH), Game.MAP_HEIGHT);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(Game.WIDTH - GRASS_BORDER_WIDTH, 0, GRASS_BORDER_WIDTH, Game.MAP_HEIGHT);

        // Draw lane dividers

        for (Float[] aPos: laneDividers.values()) {
            boolean red = false;
            for (int y = 0; y < Game.MAP_HEIGHT; y+=50) {
                if (red) {
                    shapeRenderer.setColor(Color.RED);
                } else {
                    shapeRenderer.setColor(Color.WHITE);
                }
                shapeRenderer.rect(aPos[1] - 5, y, 10, 50);
                red = !red;
            }
        }

        shapeRenderer.end();


        batch.begin();
        this.font.draw(batch, "Leg: " + this.leg, 10, Game.HEIGHT - 5);
        this.font.draw(batch, "Time: " + Math.floor(this.time) + "s", 10, Game.HEIGHT - 20);
        this.font.draw(batch, "Penalty: " + Math.floor(this.player.getPenalty()) + "s", 10, Game.HEIGHT - 35);
        batch.end();



        for (Entity entity: entities) {
            entity.draw(camera);
        }

        // Draw boats last so they're top of entities
        player.boat.draw(camera);
        for (NPC npc: npcs) {
            npc.boat.draw(camera);
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
                case 0:
                    this.addEntity(new Duck(newPos));
                    break;
                case 1:
                    this.addEntity(new Goose(newPos));
                    break;
                case 2:
                    this.addEntity(new Log(newPos));
                    break;
                case 3:
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
