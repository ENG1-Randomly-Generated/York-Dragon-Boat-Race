package com.mygdx.dragonboatgame.game;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.dragonboatgame.entity.Boat;
import java.util.ArrayList;

/**
 * Abstract representation of a team, with methods to add times and get the best time
 *  as well as handle penalties etc
 *
 * @author Devon
 */
public abstract class Team {

    public String name;
    private ArrayList<Float> last_times;
    public Color color;
    public Boat boat;
    private boolean playing;
    private float penalty;
    private boolean qualified;

    public Team(String name, Color color) {
        this.name = name;
        this.color = color;
        this.last_times = new ArrayList<>(3);
        this.playing = false;
        this.penalty = 0;
        this.qualified = false;
    }

    public Team(String name, Color color, Boat boat) {
        this(name, color);
        this.boat = boat;
        this.boat.setTeam_name(name);
    }

    public void tick(float delta) {
        if (this.boat != null) {
            this.boat.tick(delta);
        }
    }


    public void addPenalty(float time) { this.penalty += time; }

    public void addTime(float time) {
        this.last_times.add(time);
    }

    /**
     * Returns the teams best time
     *  As described in brief, this is the best time from the last two legs
     *
     * @return The best time for this team
     */
    public float getBestTime() {
        if (this.last_times.size() < 3) return 0;

        return Math.min(this.last_times.get(1), this.last_times.get(2));
    }

    /**
     * Return the time this team got for the given leg
     * @param leg Leg number
     * @return Leg time INCLUDING PENALTY
     */
    public float getLegTime(int leg) {
        return this.last_times.get(leg - 1);
    }


    public void setPenalty(float penalty) { this.penalty = penalty; }
    public float getPenalty() { return this.penalty; }
    public void setName(String name) { this.name = name; }
    public void setColor(Color color) { this.color = color; }
    public void setBoat(Boat boat) {
        this.boat = boat;
        this.boat.setTeam_name(name);
    }
    public void setPlaying(boolean bool) { this.playing = bool; }
    public boolean isPlaying() { return this.playing; }
    public boolean hasQualified() { return qualified; }
    public void setQualified(boolean qualified) { this.qualified = qualified; }
}
