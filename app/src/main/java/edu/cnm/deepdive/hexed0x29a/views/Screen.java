package edu.cnm.deepdive.hexed0x29a.views;

import android.content.ClipData.Item;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import edu.cnm.deepdive.hexed0x29a.R;

/**
 * Created by zaryn on 7/31/2017.
 */

public class Screen extends SurfaceView implements Callback, Runnable {

  Drawable background;
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

  Resources res;


  public Screen(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
    holder = getHolder();
    holder.addCallback(this);

    res = this.getResources();
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
    double x = 0;
    double y = 0;
    double distFromCenter = 0;
    double scale =distFromCenter / 500 + 1;
    Bitmap originalBitmap = BitmapFactory.decodeResource(res, R.drawable.temp_map);
    Bitmap greenGem = BitmapFactory.decodeResource(res, R.drawable.green_gem);
    Bitmap blueGem = BitmapFactory.decodeResource(res, R.drawable.blue_gem);
    Bitmap redGem = BitmapFactory.decodeResource(res, R.drawable.red_gem);
    Bitmap pearl = BitmapFactory.decodeResource(res, R.drawable.pearl);
    Bitmap crystal = BitmapFactory.decodeResource(res, R.drawable.crystal);
    Bitmap hourglass = BitmapFactory.decodeResource(res, R.drawable.hourglass);

    while(isRunning) {
      if (!holder.getSurface().isValid()) {
        continue;
      }
      Canvas canvas = holder.lockCanvas();

      if (upPressed) {

        if ( y < (originalBitmap.getHeight() - Math.abs(y))) {
          y += 12 / scale;
        } else {
          y -= 12 / scale;
        }
      } else if (downPressed) {
        if ( y > - (originalBitmap.getHeight() - Math.abs(y))) {
          y -= 12 / scale;
        } else {
          y += 12 /scale;
        }
      } else if (rightPressed) {
        if ( x > - (originalBitmap.getWidth() - Math.abs(x))) {
          x -= 12 / scale;
        } else {
          x += 12 / scale;
        }
      } else if (leftPressed) {
        if ( x < (originalBitmap.getWidth() - Math.abs(x))) {
          x += 12 / scale;
        } else {
          x -= 12 / scale;
        }
      }

      distFromCenter = Math.abs(x) + Math.abs(y);
      scale = distFromCenter / 500 + 1;

      canvas.scale((float) scale, (float) scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
      canvas.translate((float) x + (canvas.getWidth() / 2), (float) y + (canvas.getHeight() / 2));
      background.draw(canvas);
      canvas.drawBitmap(greenGem,-2000,-1500,null);
      canvas.drawBitmap(crystal,1750,-1300,null);

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
