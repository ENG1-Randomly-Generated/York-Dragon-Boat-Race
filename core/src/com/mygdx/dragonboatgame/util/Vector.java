package com.mygdx.dragonboatgame.util;

/**
 * Represents a Vector in 2D space
 *
 * @author Devon
 */
public class Vector {

    public float x;
    public float y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public boolean equals(Object other) {
        if (other instanceof Vector) {
            Vector otherVector = (Vector) other;
            return otherVector.x == this.x && otherVector.y == this.y;
        }

        return false;
    }

}
