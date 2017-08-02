package edu.cnm.deepdive.hexed0x29a.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Button;
import edu.cnm.deepdive.hexed0x29a.R;

/**
 * Created by zaryn on 7/31/2017.
 */

public class Screen extends SurfaceView implements Callback, Runnable {

//  AnimationDrawable characterAnimationFront;
//  AnimationDrawable characterAnimationBack;
//  AnimationDrawable characterAnimationRight;
//  AnimationDrawable characterAnimationLeft;
  Drawable background;
//  Rect characterBounds;


  SurfaceHolder holder;
  Thread renderThread = null;
  volatile boolean isRunning = false;

  public boolean isUpPressed() {
    return upPressed;
  }
  public void setUpPressed(boolean upPressed) {
    this.upPressed = upPressed;
  }
  public boolean isDownPressed() {
    return downPressed;
  }
  public void setDownPressed(boolean downPressed) {
    this.downPressed = downPressed;
  }
  public boolean isRightPressed() {
    return rightPressed;
  }
  public void setRightPressed(boolean rightPressed) {
    this.rightPressed = rightPressed;
  }
  public boolean isLeftPressed() {
    return leftPressed;
  }
  public void setLeftPressed(boolean leftPressed) {
    this.leftPressed = leftPressed;
  }

  private boolean upPressed;
  private boolean downPressed;
  private boolean rightPressed;
  private boolean leftPressed;


  public Screen(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
    holder = getHolder();
    holder.addCallback(this);

    Resources res = this.getResources();
//    characterAnimationFront = (AnimationDrawable)res.getDrawable(R.drawable.front);
//    characterAnimationBack = (AnimationDrawable)res.getDrawable(R.drawable.back);
//    characterAnimationRight = (AnimationDrawable)res.getDrawable(R.drawable.right);
//    characterAnimationLeft = (AnimationDrawable)res.getDrawable(R.drawable.left);
    background = (Drawable)res.getDrawable(R.drawable.map);

  }

  public void resume() {
    isRunning = true;
    renderThread = new Thread(this);
    renderThread.start();
  }
  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    Canvas canvas = holder.lockCanvas();
//    characterBounds = new Rect(this.getWidth() / 2 - 85,this.getHeight() / 2 - 85,this.getWidth() / 2 + 85,this.getHeight() / 2 + 85);
//    characterAnimationFront.setBounds(characterBounds);
//    characterAnimationBack.setBounds(characterBounds);
//    characterAnimationRight.setBounds(characterBounds);
//    characterAnimationLeft.setBounds(characterBounds);
//    characterAnimationFront.setCallback(new Drawable.Callback() {
//      @Override
//      public void invalidateDrawable(@NonNull Drawable who) {
//        return;
//      }
//
//      @Override
//      public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
//        Handler handler = new Handler();
//        handler.postAtTime(what, when);
//      }
//
//      @Override
//      public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
//        return;
//      }
//    });
//    characterAnimationBack.setCallback(new Drawable.Callback() {
//      @Override
//      public void invalidateDrawable(@NonNull Drawable who) {
//        return;
//      }
//
//      @Override
//      public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
//        Handler handler = new Handler();
//        handler.postAtTime(what, when);
//      }
//
//      @Override
//      public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
//        return;
//      }
//    });
//    characterAnimationRight.setCallback(new Drawable.Callback() {
//      @Override
//      public void invalidateDrawable(@NonNull Drawable who) {
//        return;
//      }
//
//      @Override
//      public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
//        Handler handler = new Handler();
//        handler.postAtTime(what, when);
//      }
//
//      @Override
//      public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
//        return;
//      }
//    });
//    characterAnimationLeft.setCallback(new Drawable.Callback() {
//      @Override
//      public void invalidateDrawable(@NonNull Drawable who) {
//        return;
//      }
//
//      @Override
//      public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
//        Handler handler = new Handler();
//        handler.postAtTime(what, when);
//      }
//
//      @Override
//      public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
//        return;
//      }
//    });
//    characterAnimationFront.start();
//    characterAnimationBack.start();
//    characterAnimationRight.start();
//    characterAnimationLeft.start();
    getHolder().unlockCanvasAndPost(canvas);
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {

  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  @Override
  public void run() {
    while(isRunning) {
      if (!holder.getSurface().isValid()) {
        continue;
      }
      Canvas canvas = holder.lockCanvas();
      if (upPressed) {
        canvas.translate(0, 20);
        background.draw(canvas);
        //canvas.drawRGB(0, 0, 0);
//        characterAnimationBack.draw(canvas);
      } else if (downPressed) {
        background.draw(canvas);
        //canvas.drawRGB(0, 0, 0);
//        characterAnimationFront.draw(canvas);
      } else if (rightPressed) {
       background.draw(canvas);
        //canvas.drawRGB(0, 0, 0);
//        characterAnimationRight.draw(canvas);
      } else if (leftPressed) {
        background.draw(canvas);
        //canvas.drawRGB(0, 0, 0);
//        characterAnimationLeft.draw(canvas);
      }
      holder.unlockCanvasAndPost(canvas);
    }
  }

  public void pause(){
    isRunning = false;
    while (true) {
      try {
        renderThread.join();
        return;
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }
}
