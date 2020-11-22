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

    /**
     * Add another vector to this one
     * @param other Other vector to add
     */
    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Add the following x, y coordinates to this vector
     * @param x x coordinate
     * @param y y coordinate
     */
    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Return the magnitude of this vector, i.e. the length
     * @return Float magnitude
     */
    public float getMagnitude() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    /**
     * Return whether this vector is equal to zero
     * @return Boolean whether this vector is zero
     */
    public boolean isZero() {
        return this.x == 0 && this.y == 0;
    }

    /**
     * Clamps given vector to a maximum magnitude
     *
     * @param max Maximum magnitude
     */
    public void clamp(float max) {
        float over = this.getMagnitude() / max;
        if (over > 1) {
            this.x = (this.x / over);
            this.y = (this.y / over);
        }
    }


    /**
     * Returns a new vector that is multiplied by the given scale
     * @param scale Scale to multiply this vector by
     * @return Vector new multiplied vector
     */
    public Vector multiply(float scale) {
        return new Vector(this.x * scale, this.y * scale);
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
