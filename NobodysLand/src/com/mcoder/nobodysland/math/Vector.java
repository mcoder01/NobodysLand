package com.mcoder.nobodysland.math;

import java.io.Serializable;

public class Vector implements Serializable {
	private double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector() {
		this(0, 0);
	}

	public void add(Vector v) {
		x += v.x;
		y += v.y;
	}

	public void sub(Vector v) {
		x -= v.x;
		y -= v.y;
	}

	public void mult(double val) {
		x *= val;
		y *= val;
	}

	public void normalize() {
		double len = mag();
		x /= len;
		y /= len;
	}

	public double mag() {
		return Math.sqrt(x * x + y * y);
	}

	public double heading() {
		return Math.atan2(y, x);
	}

	public void setMag(double mag) {
		double currMag = mag();
		x = x / currMag * mag;
		y = y / currMag * mag;
	}

	public Vector copy() {
		return new Vector(x, y);
	}

	public void set(Vector v) {
		setX(v.x);
		setY(v.y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public static Vector add(Vector v1, Vector v2) {
		Vector copy = v1.copy();
		copy.add(v2);
		return copy;
	}

	public static Vector sub(Vector v1, Vector v2) {
		Vector copy = v1.copy();
		copy.sub(v2);
		return copy;
	}

	public static Vector mult(Vector v, double val) {
		Vector copy = v.copy();
		copy.mult(val);
		return copy;
	}

	public static Vector normalize(Vector v) {
		Vector copy = v.copy();
		copy.normalize();
		return copy;
	}

	public static double dist(Vector v1, Vector v2) {
		return Vector.sub(v2, v1).mag();
	}

	public static Vector fromAngle(double angle) {
		return new Vector(Math.cos(angle), Math.sin(angle));
	}
}
