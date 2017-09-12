package edu.cnm.deepdive.hexed0x29a.activities;

import android.app.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.rest_client.GameTraffic;
import edu.cnm.deepdive.hexed0x29a.views.Screen;

public class NewGame extends Activity {
  static final int NEIGHBORHOOD_SIZE = 50;
  Button upButton;
  Button downButton;
  Button rightButton;
  Button leftButton;
  ImageButton pauseButton;

  private boolean mIsBound = false;
  private MusicService mServ;

  private Integer gameId = null;

  public synchronized Integer getGameId() {
    return gameId;
  }

  public synchronized void setGameId(Integer gameId) {
    this.gameId = gameId;
  }

  ImageView backView;
  ImageView frontView;
  ImageView rightView;
  ImageView leftView;

  AnimationDrawable walk;

  GameTraffic gameTraffic = GameTraffic.getInstance(this);

  public Handler handler;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
//    gameTraffic.newGame(NEIGHBORHOOD_SIZE, this);
//    while (getGameId() == null) {
//      try {
//        Thread.sleep(500);
//      } catch (InterruptedException ex) {
//        // Do nothing
//      }
//    }
    setContentView(R.layout.activity_game);

    backView = (ImageView)findViewById(R.id.backView) ;
    backView.setBackgroundResource(R.drawable.back);
    frontView = (ImageView)findViewById(R.id.frontView) ;
    frontView.setBackgroundResource(R.drawable.front);
    rightView = (ImageView)findViewById(R.id.rightView) ;
    rightView.setBackgroundResource(R.drawable.right);
    leftView = (ImageView)findViewById(R.id.leftView) ;
    leftView.setBackgroundResource(R.drawable.left);

    upButton = (Button) findViewById(R.id.upButton);
    downButton = (Button) findViewById(R.id.downButton);
    rightButton = (Button) findViewById(R.id.rightButton);
    leftButton = (Button) findViewById(R.id.leftButton);
//    pauseButton = (ImageButton) findViewById(R.id.pauseButton);


    upButton.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            ((Screen)findViewById(R.id.screenView)).setUpPressed(true);
            frontView.setVisibility(View.INVISIBLE);
            backView.setVisibility(View.VISIBLE);
            rightView.setVisibility(View.INVISIBLE);
            leftView.setVisibility(View.INVISIBLE);
            walk = (AnimationDrawable) backView.getBackground();
            walk.start();
            return true;
          case MotionEvent.ACTION_UP:
            ((Screen)findViewById(R.id.screenView)).setUpPressed(false);
            walk.stop();
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
            frontView.setVisibility(View.VISIBLE);
            backView.setVisibility(View.INVISIBLE);
            rightView.setVisibility(View.INVISIBLE);
            leftView.setVisibility(View.INVISIBLE);
            walk = (AnimationDrawable) frontView.getBackground();
            walk.start();
            return true;
          case MotionEvent.ACTION_UP:
            ((Screen)findViewById(R.id.screenView)).setDownPressed(false);
            walk.stop();
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
            frontView.setVisibility(View.INVISIBLE);
            backView.setVisibility(View.INVISIBLE);
            rightView.setVisibility(View.VISIBLE);
            leftView.setVisibility(View.INVISIBLE);
            walk = (AnimationDrawable) rightView.getBackground();
            walk.start();
            return true;
          case MotionEvent.ACTION_UP:
            ((Screen)findViewById(R.id.screenView)).setRightPressed(false);
            walk.stop();
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
            frontView.setVisibility(View.INVISIBLE);
            backView.setVisibility(View.INVISIBLE);
            rightView.setVisibility(View.INVISIBLE);
            leftView.setVisibility(View.VISIBLE);
            walk = (AnimationDrawable) leftView.getBackground();
            walk.start();
            return true;
          case MotionEvent.ACTION_UP:
            ((Screen)findViewById(R.id.screenView)).setLeftPressed(false);
            walk.stop();
        }
        return false;
      }
    });

//    pauseButton.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//
//      }
//    });

    new CountDownTimer(90000, 1000) {
      public void onTick(long millisUntilFinished) {
        ((TextView)findViewById(R.id.Timer)).setText("Seconds Remaining: " + millisUntilFinished / 1000);
      }
      public void onFinish() {

      }
    }.start();

  }

  private ServiceConnection sCon =new ServiceConnection(){

    public void onServiceConnected(ComponentName name, IBinder
        binder) {
      mServ = ((MusicService.ServiceBinder)binder).getService();
    }

    public void onServiceDisconnected(ComponentName name) {
      mServ = null;
    }
  };

  void doBindService(){
    bindService(new Intent(this,MusicService.class),
        sCon, Context.BIND_AUTO_CREATE);
    mIsBound = true;
  }

  void doUnbindService()
  {
    if(mIsBound)
    {
      unbindService(sCon);
      mIsBound = false;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    ((Screen)findViewById(R.id.screenView)).resume();
    doBindService();
    Intent music = new Intent();
    music.putExtra(MusicService.TRACK_ID_KEY, R.raw.overworld_theme);
    music.setClass(this,MusicService.class);
    startService(music);
  }

  @Override
  protected void onPause() {
    super.onPause();
    ((Screen)findViewById(R.id.screenView)).pause();
    mServ.stopMusic();
    doUnbindService();
  }

}
