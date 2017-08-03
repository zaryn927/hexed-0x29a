package edu.cnm.deepdive.hexed0x29a.views;

import android.content.Context;
import android.content.res.Resources;
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


  public Screen(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
    holder = getHolder();
    holder.addCallback(this);

    Resources res = this.getResources();
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
    int x = 0;
    int y = 0;
    while(isRunning) {
      if (!holder.getSurface().isValid()) {
        continue;
      }
      Canvas canvas = holder.lockCanvas();

      if (upPressed) {
        y += 5;
      } else if (downPressed) {
        y -= 5;
      } else if (rightPressed) {
        x -= 5;
      } else if (leftPressed) {
        x += 5;
      }
      canvas.translate(x, y);
      background.draw(canvas);
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
