package com.example.surfaceviewtask1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;
    int background;
    DrawThread thread;
    boolean runFlag = true;
    Paint p;
    Random random;
    ArrayList<Integer> colors;
    ArrayList<Ball> balls;
    int width;
    int height;
    Rectangle rect;
    boolean pressing = false;

    public void disableFlag(){
        runFlag = false;
    }

    public void enableFlag(){
        runFlag = true;
    }



    class DrawThread extends Thread {

        @Override
        public void run() {
            super.run();
            // задание: реализовать плавную смену цвета
            // палитру выбирайте сами
            while(runFlag) {
                //move step balls
                for (int i = 0; i < balls.size(); i++) {
                    balls.get(i).move_step();
                }
                Canvas c = holder.lockCanvas();
                if (c != null) {
                    c.drawColor(background);
                    rect.draw(c);
                    for (int i = 0; i < balls.size(); i++) {
                        balls.get(i).get_intersection_wall(width, height);
                        if(rect.inRect(balls.get(i).getX(), balls.get(i).getY(), balls.get(i).getRadius())){
                            balls.get(i).setDx(-balls.get(i).getDx());
                            balls.get(i).setDy(-balls.get(i).getDy());
                            balls.get(i).next_color(colors);
                        }
                        //intersect ball
                        for (int j = i + 1; j < balls.size(); j++) {
                            Ball ball = balls.get(i).push_balls(balls.get(j));
                            balls.get(j).update(ball);
                        }
                        p.setColor(balls.get(i).getColor());
                        c.drawCircle(balls.get(i).getX(), balls.get(i).getY(), balls.get(i).getRadius(), p);
                    }
                    check_victory(c);
                    holder.unlockCanvasAndPost(c);
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                }
            }
        }

        private void check_victory(Canvas c) {
            int checkColor = balls.get(0).getColor();
            boolean isVictory = true;
            for (int i = 1; i < balls.size(); i++) {
                if(balls.get(i).getColor() != checkColor){
                    isVictory = false;
                }
            }
            if(isVictory){
                rect.setX(300);
                rect.setY(300);
                p.setTextSize(100);
                c.drawColor(Color.BLACK);
                p.setColor(Color.WHITE);
                c.drawText("You win", 200, height/2, p);
                try {
                    sleep(1000);
                }
                catch (InterruptedException e) {}
//                balls.get(0).setColor(colors.get(random.nextInt(colors.size())));
            }
        }
    }


    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // этот класс является обработчиком событий с поверхностью
        getHolder().addCallback(this);

        random = new Random();
        p = new Paint();
        //colors init
        colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.WHITE);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLACK);
        //balls init
        balls = new ArrayList<>();
        p.setColor(Color.GREEN);
        background = Color.BLUE;
        rect = new Rectangle(300, 300, Color.BLACK);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // запустить поток отрисовки
        holder = surfaceHolder;
        thread = new DrawThread();
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // перезапустить поток
        enableFlag();
        width = getWidth();
        height = getHeight();
        int numberBalls = 2;
        for (int j = 0; j < numberBalls; j++) {
            int radius = 50;
            int dx = j % 2 == 0 ? 10 : -10;
            int dy = j % 2 != 0 ? 10 : -10;
            balls.add(new Ball(colors.get(random.nextInt(colors.size())), radius + random.nextInt(width - (radius * 2)), radius + random.nextInt(height - (radius * 2)), dx, dy, radius));
        }
//        balls.add(new Ball(colors.get(random.nextInt(colors.size())), random.nextInt(), 150, 10, -10, 50));
//        balls.add(new Ball(colors.get(random.nextInt(colors.size())), 300, 600, -10, 10, 50));
        holder = surfaceHolder;
        thread = new DrawThread();
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // остановить поток
        disableFlag();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                if (rect.getLeft() <= event.getX() && rect.getRight() >= event.getX() && rect.getTop() <= event.getY() && rect.getBottom() >= event.getY()) {
                    Log.d("mytag", "choose");
                    pressing = true;
                }

                Log.d("mytag", "pressing");
                break;
            case MotionEvent.ACTION_MOVE: // движение
                if(pressing) {
                    rect.update_coord(x, y);
                }
                break;
            case MotionEvent.ACTION_UP: // отпускание
                pressing = false;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}
