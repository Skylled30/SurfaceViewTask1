package com.example.surfaceviewtask1;

import android.util.Log;

import java.util.ArrayList;

public class Ball {
    private int color;
    private float x;
    private float y;
    private float dx;
    private float dy;
    private int radius;

    public Ball(int color, float x, float y, float dx, float dy, int radius) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.radius = radius;
    }

    public void update(Ball ball) {
        this.color = ball.getColor();
        this.x = ball.getX();
        this.y = ball.getY();
        this.dx = ball.getDx();
        this.dy = ball.getDy();
        this.radius = ball.getRadius();
    }


    public void get_intersection_wall(int width, int height){
        if(x > width - radius || x < radius){
            dx = -dx;
        }
        if(y > height - radius || y < radius){
            dy = -dy;
        }
    }

    public void move_step(){
        x += dx;
        y += dy;
    }

    public boolean intersection(float x1, float x2, float y1, float y2, int radius1, int radius2){
        return Math.abs((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) < (radius1 + radius2) * (radius1 + radius2) ;
    }


    public Ball push_balls(Ball ball){
        if(intersection(x, ball.getX(), y, ball.getY(), radius, ball.getRadius())) {
            Log.d("mytag", "after " + x + " " + y);
            float dxx = dx;
            float dyy = dy;
            dx = ball.getDx();
            dy = ball.getDy();
            ball.setDx(dxx);
            ball.setDy(dyy);
        }
        return ball;
    }

    public void next_color(ArrayList<Integer> colors){
        int index = colors.indexOf(color);
        Log.d("mytag", (index + 1) % (colors.size()) + " ");
        color = colors.get((index + 1) % (colors.size()));
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
