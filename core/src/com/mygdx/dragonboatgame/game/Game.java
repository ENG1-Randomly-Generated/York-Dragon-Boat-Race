package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.dragonboatgame.DragonBoatGame;
import com.mygdx.dragonboatgame.entity.Boat;
import com.mygdx.dragonboatgame.entity.Entity;
import com.mygdx.dragonboatgame.entity.obstacle.Duck;
import com.mygdx.dragonboatgame.entity.obstacle.Goose;
import com.mygdx.dragonboatgame.entity.obstacle.Log;
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

    public static HashMap<String, Boat> BOATS = new HashMap<String, Boat>();

    /*
     * Declare our predefined boats here and append to the master HashMap
     * Name (String) : Boat (Boat)
     */
    static {
        BOATS.put("Speedy", new Boat("Speedy", 10, 5, 2));
        BOATS.put("Twisty", new Boat("Twisty", 6, 10, 4));
        BOATS.put("Tanky", new Boat("Tanky", 4, 3, 10));
    }

    public static Random random = new Random();

    private int leg;
    private float time;

    private Player player;
    private ArrayList<NPC> npcs;
    private ArrayList<Entity> entities;

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
    }

    /**
     *  The main draw function for the game
     *      TODO: Here we shall draw the track
     *  Calls all of it's known entities' draw functions
     */
    public void draw() {
        player.boat.draw();
        for (NPC npc: npcs) {
            npc.boat.draw();
        }
        for (Entity entity: entities) {
            entity.draw();
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
            switch (Game.random.nextInt(3)) {
                case 1:
                    this.addEntity(new Duck(new Vector(random.nextInt(Gdx.graphics.getWidth()), random.nextInt(Gdx.graphics.getHeight()))));
                case 2:
                    this.addEntity(new Goose(new Vector(random.nextInt(Gdx.graphics.getWidth()), random.nextInt(Gdx.graphics.getHeight()))));
                case 3:
                    this.addEntity(new Log(new Vector(random.nextInt(Gdx.graphics.getWidth()), random.nextInt(Gdx.graphics.getHeight()))));
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
            this.npcs.add(
                    new NPC("Bot" + npcs.size(), new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1), Game.getRandomBoat(), difficulty)
            );
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
        return (Boat) BOATS.values().toArray()[random.nextInt(BOATS.size() - 1)];
    }



}
