package me.arnavgarg.nimgame.Game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.arnavgarg.nimgame.R;

/**
 * Created by Arnav on 4/7/2016.
 */
public class GameView extends Activity {

    Bitmap matchSticks;
    Sprite sprite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        matchSticks = BitmapFactory.decodeResource(getResources(), R.drawable.matchsticksprite);

    }

    public class OurView extends SurfaceView implements Runnable {

        Thread t = null;
        SurfaceHolder holder;

        public OurView(Context context) {
            super(context);
            holder = getHolder();
            sprite = new Sprite(this, matchSticks);
        }

        @Override
        public void run() {

            Canvas canvas = holder.lockCanvas();
            onDraw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            sprite.onDraw(canvas);

        }
    }
}
