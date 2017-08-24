package edu.cnm.deepdive.hexed0x29a_demo.interfaces_abstracts;

import java.util.List;
/**
 * Created by zaryn on 7/24/2017.
 */

public interface Input {
  class KeyEvent {
    public static final int KEY_DOWN = 0;
    public static final int KEY_UP = 1;
    public int type;

    public int keycode;
    public int keyChar;
  }
  class TouchEvent {
    public static final int TOUCH_DOWN = 0;
    public static final int TOUCH_UP = 1;
    public static final int TOUCH_DRAGGED = 2;

    public int type;
    public int x;
    public int y;
    public int pointer;
  }

  boolean isKeyPressed(int keyCode);
  boolean isTouchDown(int pointer);
  int getTouchX(int pointer);
  int getTouchY(int pointer);
  float getAccelX(); //Probably won't need accelerometer
  float getAccelY();
  float getAccelZ();
  List<KeyEvent> getKeyEvents();
  List<TouchEvent> getTouchEvents();

}
