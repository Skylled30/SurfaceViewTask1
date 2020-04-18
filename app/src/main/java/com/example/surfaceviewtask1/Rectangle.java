package com.example.surfaceviewtask1;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Rectangle {
    private float left, right, top, bottom, r, x, y;
    private int color;

    public void update_coord(float x, float y) {
        this.left = x - 75;
        this.right = x + 75;
        this.top = y - 75;
        this.bottom = y + 75;
        this.x = x;
        this.y = y;
        this.r = (float) (120 * Math.sqrt(2) / 2);
    }

    public Rectangle(float x, float y, int color) {
        this.color = color;
        update_coord(x, y);
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawRect(left, top, right, bottom, p);
    }

    public boolean inRect(float point_x, float point_y, float radius) {
        float d = (float)Math.sqrt((point_x - x) * (point_x - x) + (point_y - y) * (point_y - y));
        if (d <= r + radius) {
            return true;
        }
        return false;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }

    public float getR() {
        return r;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}