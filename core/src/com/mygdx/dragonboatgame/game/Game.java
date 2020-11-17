package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.entity.obstacle.Duck;
import com.mygdx.dragonboatgame.entity.obstacle.Goose;
import com.mygdx.dragonboatgame.entity.obstacle.Log;
import com.mygdx.dragonboatgame.entity.obstacle.Rock;
import com.mygdx.dragonboatgame.screens.AbstractScreen;
import com.mygdx.dragonboatgame.screens.GameManager;
import com.mygdx.dragonboatgame.screens.ScreenManager;
import com.mygdx.dragonboatgame.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents a game instance, which holds entities, the player and the npcs and manages their drawing and input.
 *
 * @author Devon
 */
public class Game extends AbstractScreen {

    public static final float WIDTH = Gdx.graphics.getWidth();
    public static final float HEIGHT = Gdx.graphics.getHeight();
    public static final float MAP_HEIGHT = 1000;
    public static final float GRASS_BORDER_WIDTH = WIDTH/8;
    public static final Color GRASS_COLOR = new Color(36/255f, 115/255f, 35/255f, 1);

    public final static Player player = new Player("Player", Color.WHITE);
    private final static HashMap<String, int[]> BOATS = new HashMap<String, int[]>();
    public final static Random random = new Random();
    private final static ArrayList<NPC> npcs = new ArrayList<NPC>(3);;
    private final static ArrayList<Entity> entities = new ArrayList<Entity>();
    private final static HashMap<Team, Float[]> laneDividers = new HashMap<Team, Float[]>();

    public static int leg = 0;
    private static float time = 0;
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private static SpriteBatch batch = new SpriteBatch();
    private static BitmapFont font = new BitmapFont();
    private static OrthographicCamera camera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
    private static GameManager gameManager;

    /*
     * Declare our predefined boats here and append to the master HashMap
     * Name (String) : Boat (Boat)
     */
    static {
        // {max_speed, manovourability, max_robustness)
        BOATS.put("Speedy", new int[] {42, 30, 10});
        BOATS.put("Twisty", new int[] {40, 32, 10});
        BOATS.put("Tanky", new int[] {38, 27, 15});

        camera.setToOrtho(false);
        shapeRenderer.setAutoShapeType(true);
    }


    /**
     * Start a leg
     *  Here we handle difficulty, generating obstacles, placing boats etc
     *
     * @param leg Leg number to start
     */
    public static void startLeg(int leg) {
        // Start new leg
        Game.leg = leg;
        generateObstacles(10 + (Game.leg * 5));

        float seperation;
        if (Game.leg < 4) {
            seperation = ((Game.WIDTH - (2 * GRASS_BORDER_WIDTH)) / (npcs.size() + 1));
        } else {
            seperation = ((Game.WIDTH - (2 * GRASS_BORDER_WIDTH)) / (Math.min(3, Game.getAllTeams().length)));
        }

        player.boat.reset();
        player.boat.setPos(new Vector(GRASS_BORDER_WIDTH + seperation/2, 0));
        laneDividers.put(player, new Float[] {GRASS_BORDER_WIDTH, GRASS_BORDER_WIDTH + seperation});
        player.setPlaying(true);
        player.setPenalty(0);
        for (int i = 0; i < npcs.size(); i++) {
            NPC npc = npcs.get(i);
            if (Game.leg >= 4 && !npc.hasQualified()) continue; // Do not include this guy

            laneDividers.put(npc, new Float[] {GRASS_BORDER_WIDTH + (seperation * (i+1)), GRASS_BORDER_WIDTH + (seperation * (i+2))});
            npc.boat.reset();
            npc.setPenalty(0);
            npc.boat.setPos(new Vector(Game.GRASS_BORDER_WIDTH + (seperation * (i+1)) + seperation/2, 0));
            npc.setPlaying(true);
            npc.init();
        }
        time = 0;
    }

    /**
     * Called when a leg has finished
     *
     * Perform cleanup & start new leg if required
     */
    private static void finishLeg() {
        // Clean entities
        entities.clear();
        laneDividers.clear();

        if (leg <= 2) {
            Game.gameManager.sm.setScreen(ScreenManager.GAMESTATE.LegResult);
        } else if (leg == 3) {
            // Calculate which teams qualified and show FinalistsScreen
            int teamsToQualify = Math.min(3, Game.getAllTeams().length);
            int qualified = 0;

            while (qualified < teamsToQualify) {
                float minTime = Float.MAX_VALUE;
                Team minTeam = null;
                for (Team team : Game.getAllTeams()) {
                    if (team.hasQualified()) continue;
                    if (team.getBestTime() < minTime) {
                        minTime = team.getBestTime();
                        minTeam = team;
                    }
                }
                if (minTeam != null) {
                    minTeam.setQualified(true);
                    qualified++;
                }
            }

            Game.gameManager.sm.setScreen(ScreenManager.GAMESTATE.Finalists);
        } else if (leg == 4) {
            Game.gameManager.sm.setScreen(ScreenManager.GAMESTATE.Medals);
        }
    }

    /**
     * Check whether the current leg is finished
     * First updates playing status of all Teams before doing check
     *
     * A leg is finished if all boats are past the finishing line or out (dead)
     *
     * @return whether the current leg is finished
     */
    public static boolean legFinished() {
        if (leg == 0) return false; // There is no current leg running

        // Update player playing status & time
        if (player.isPlaying() && player.boat.getPos().y + player.boat.getSize().y > Game.MAP_HEIGHT) {
            player.setPlaying(false);
            player.addTime(time + player.getPenalty());
        }
        if (player.isPlaying() && !player.boat.isAlive()) {
            player.setPlaying(false);
            player.addTime(Float.MAX_VALUE);
        }


        // Update npcs playing status & times
        for (NPC npc : npcs) {
            if (npc.isPlaying() && npc.boat.getPos().y + npc.boat.getSize().y > Game.MAP_HEIGHT) {
                npc.setPlaying(false);
                npc.addTime(time + npc.getPenalty());
            }
            if (npc.isPlaying() && !npc.boat.isAlive()) {
                npc.setPlaying(false);
                npc.addTime(Float.MAX_VALUE);
            }
        }

        // Check whether all npcs & players are actually playing
        for (NPC npc : npcs) {
            if (npc.isPlaying()) return false;
        }
        return !player.isPlaying();

    }


    /**
     *  The main tick of the game is run periodically
     *  Calls all of it's known entities' ticks, as well as checks for win conditions
     */
    public static void tick() {
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
                team.addPenalty(delta * 2);
            }
        }

        // Check whether this leg has finished
        if (legFinished()) {
            finishLeg();
            return;
        }

        // Update camera
        camera.position.set(camera.position.x, Math.min(Math.max(player.boat.getPos().y, Game.HEIGHT/2), Game.MAP_HEIGHT - Game.HEIGHT/2), 0);
        camera.update();

    }

    /**
     *  The main draw function for the game
     *
     *  Calls all of it's known entities' draw functions
     */
    public static void draw() {

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();

        // Draw basic scene

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(GRASS_COLOR);
        shapeRenderer.rect(0, 0, GRASS_BORDER_WIDTH, Game.MAP_HEIGHT);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(GRASS_BORDER_WIDTH, 0, WIDTH -  (2 * GRASS_BORDER_WIDTH), Game.MAP_HEIGHT);
        shapeRenderer.setColor(GRASS_COLOR);
        shapeRenderer.rect(Game.WIDTH - GRASS_BORDER_WIDTH, 0, GRASS_BORDER_WIDTH, Game.MAP_HEIGHT);

        // Draw lane dividers

        for (Float[] aPos: laneDividers.values()) {
            if (aPos[1] >= Game.WIDTH - Game.GRASS_BORDER_WIDTH) continue;
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
        font.draw(batch, "Leg: " + ((leg < 4) ? leg : "Finals"), 10, Game.HEIGHT - 5);
        font.draw(batch, "Time: " + Math.floor(time) + "s", 10, Game.HEIGHT - 20);
        font.draw(batch, "Penalty: " + Math.floor(player.getPenalty()) + "s", 10, Game.HEIGHT - 35);
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


    public static void addEntity(Entity entity) { entities.add(entity); }
    public static ArrayList<Entity> getEntities() { return entities; }
    public static void addNPC(NPC npc) { npcs.add(npc); }
    public static Float[] getLaneDividers(Team team) { return laneDividers.get(team); }

    /**
     * Returns all the teams assigned to the game
     *  This is all NPCs + Player
     * @return Team[] of teams
     */
    public static Team[] getAllTeams() {
        Team[] teams = new Team[npcs.size() + 1];
        teams[0] = player;
        for (int i = 0; i < npcs.size(); i++) {
            teams[i+1] = npcs.get(i);
        }
        return teams;
    }


    /**
     * Generates random obstacles across the course
     *
     * @param n Number of obstacles to be placed
     */
    public static void generateObstacles(int n) {
        for (int i = 0; i < n; i++) {
            Vector newPos = new Vector(random.nextInt((int)(Game.WIDTH - (2*Game.GRASS_BORDER_WIDTH))) + Game.GRASS_BORDER_WIDTH, 200 + random.nextInt((int)Game.MAP_HEIGHT - 200));
            switch (Game.random.nextInt(4)) {
                case 0:
                    addEntity(new Duck(newPos));
                    break;
                case 1:
                    addEntity(new Goose(newPos));
                    break;
                case 2:
                    addEntity(new Log(newPos));
                    break;
                case 3:
                    addEntity(new Rock(newPos));
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
    public static void addRandomNPCs(int n, int difficulty) {
        for (int i = 0; i < n; i++) {
            NPC npc = new NPC("Bot" + npcs.size(), new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1), Game.getRandomBoat(), difficulty);
            npcs.add(npc);
        }
    }

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

    /**
     * Return the HashMap of boats
     *
     * @return Pre-defined Boats
     */
    public static HashMap<String, int[]> getBoats() {
        return Game.BOATS;
    }

    /**
     * Create a new boat from the given boat name
     *
     * @param name Boat name to create instance of
     * @return Boat object of new boat with given attributes
     */
    public static Boat getNewBoat(String name) {
        if (!BOATS.containsKey(name)) throw new NullPointerException();
        int[] attrs = BOATS.get(name);
        return new Boat(name, attrs[0], attrs[1], attrs[2]);
    }


    /**
     * Create a new Game to be run
     *
     * @param gameManager GameManager managing the game
     */
    public Game(GameManager gameManager) {
        super(gameManager);
        Game.addRandomNPCs(3, 5);
        Game.gameManager = gameManager;
    }

    @Override
    public void tick(float delta) {}

    @Override
    public void render(float delta) {
        Game.draw();
        Game.tick();
    }

    @Override
    public void show() {
        Game.startLeg((Game.leg + 1) % 5);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
