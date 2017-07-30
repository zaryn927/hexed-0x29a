package edu.cnm.deepdive.hexed0x29a.activities;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import edu.cnm.deepdive.hexed0x29a.R;

public class NewGame extends Activity {

  AnimationDrawable characterAnimationFront;
  AnimationDrawable characterAnimationBack;
  AnimationDrawable characterAnimationRight;
  AnimationDrawable characterAnimationLeft;
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

    final ImageView characterFront = (ImageView) findViewById(R.id.frontView);
    //characterFront.setBackgroundResource(R.drawable.front);
    characterAnimationFront = (AnimationDrawable) characterFront.getBackground();

    final ImageView characterBack = (ImageView) findViewById(R.id.backView);
    //characterBack.setBackgroundResource(R.drawable.back);
    characterAnimationBack = (AnimationDrawable) characterBack.getBackground();

    final ImageView characterRight = (ImageView) findViewById(R.id.rightView);
    //characterRight.setBackgroundResource(R.drawable.right);
    characterAnimationRight = (AnimationDrawable) characterRight.getBackground();

    final ImageView characterLeft = (ImageView) findViewById(R.id.leftView);
    //characterLeft.setBackgroundResource(R.drawable.left);
    characterAnimationLeft = (AnimationDrawable) characterLeft.getBackground();

    final ImageView map = (ImageView) findViewById(R.id.mapView) ;


    upButton = (Button) findViewById(R.id.upButton);
    downButton = (Button) findViewById(R.id.downButton);
    rightButton = (Button) findViewById(R.id.rightButton);
    leftButton = (Button) findViewById(R.id.leftButton);

    upButton.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            characterFront.setVisibility(View.INVISIBLE);
            characterRight.setVisibility(View.INVISIBLE);
            characterLeft.setVisibility(View.INVISIBLE);
            characterBack.setVisibility(View.VISIBLE);
            characterAnimationBack.start();
            map.scrollBy(0, 50);
            return true;
          case MotionEvent.ACTION_UP:
            characterAnimationBack.stop();
        }
        return false;
      }
    });

    downButton.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            characterBack.setVisibility(View.INVISIBLE);
            characterRight.setVisibility(View.INVISIBLE);
            characterLeft.setVisibility(View.INVISIBLE);
            characterFront.setVisibility(View.VISIBLE);
            characterAnimationFront.start();
            map.scrollBy(0, 50);
            return true;
          case MotionEvent.ACTION_UP:
            characterAnimationFront.stop();
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
            characterFront.setVisibility(View.INVISIBLE);
            characterBack.setVisibility(View.INVISIBLE);
            characterLeft.setVisibility(View.INVISIBLE);
            characterRight.setVisibility(View.VISIBLE);
            characterAnimationRight.start();
            map.scrollBy(50, 0);
            return true;
          case MotionEvent.ACTION_UP:
            characterAnimationRight.stop();
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
            characterFront.setVisibility(View.INVISIBLE);
            characterRight.setVisibility(View.INVISIBLE);
            characterBack.setVisibility(View.INVISIBLE);
            characterLeft.setVisibility(View.VISIBLE);
            characterAnimationLeft.start();
            map.scrollBy(50, 0);
            return true;
          case MotionEvent.ACTION_UP:
            characterAnimationLeft.stop();
        }
        return false;
      }
    });


  }

}
