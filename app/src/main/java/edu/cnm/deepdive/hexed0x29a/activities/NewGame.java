package edu.cnm.deepdive.hexed0x29a.activities;

import android.app.Activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.views.Screen;

public class NewGame extends Activity {

  Button upButton;
  Button downButton;
  Button rightButton;
  Button leftButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    upButton = (Button) findViewById(R.id.upButton);
    downButton = (Button) findViewById(R.id.downButton);
    rightButton = (Button) findViewById(R.id.rightButton);
    leftButton = (Button) findViewById(R.id.leftButton);

    upButton.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            ((Screen)findViewById(R.id.screenView)).setUpPressed(true);
            return true;
          case MotionEvent.ACTION_UP:
            ((Screen)findViewById(R.id.screenView)).setUpPressed(false);
        }
        return false;
      }
    });

    downButton.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            ((Screen)findViewById(R.id.screenView)).setDownPressed(true);
            return true;
          case MotionEvent.ACTION_UP:
            ((Screen)findViewById(R.id.screenView)).setDownPressed(false);
            return true;
        }
        return false;
      }
    });

    rightButton.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            ((Screen)findViewById(R.id.screenView)).setRightPressed(true);
            return true;
          case MotionEvent.ACTION_UP:
            ((Screen)findViewById(R.id.screenView)).setRightPressed(false);
            return true;
        }
        return false;
      }
    });
    leftButton.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            ((Screen)findViewById(R.id.screenView)).setLeftPressed(true);
            return true;
          case MotionEvent.ACTION_UP:
            ((Screen)findViewById(R.id.screenView)).setLeftPressed(false);
        }
        return false;
      }
    });


  }

  @Override
  protected void onResume() {
    super.onResume();
    ((Screen)findViewById(R.id.screenView)).resume();

  }

  @Override
  protected void onPause() {
    super.onPause();
    ((Screen)findViewById(R.id.screenView)).pause();
  }

}
