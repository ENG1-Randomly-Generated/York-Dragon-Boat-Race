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

    public float getMagnitude() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public boolean isZero() {
        return this.x == 0 && this.y == 0;
    }

    public boolean equals(Object other) {
        if (other instanceof Vector) {
            Vector otherVector = (Vector) other;
            return otherVector.x == this.x && otherVector.y == this.y;
        }

        return false;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}
