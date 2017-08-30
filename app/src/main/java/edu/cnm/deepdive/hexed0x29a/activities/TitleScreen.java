package edu.cnm.deepdive.hexed0x29a.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;
import android.widget.RelativeLayout;
import edu.cnm.deepdive.hexed0x29a.rest_client.GameTraffic;

public class TitleScreen extends AppCompatActivity {

  private boolean mIsBound = false;
  private MusicService mServ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_title_screen);

    GameTraffic.getInstance(this);


    final ImageButton newGame = (ImageButton)findViewById(R.id.newGameButton);
    final ImageButton options = (ImageButton)findViewById(R.id.optionsButton);
    final ImageButton credits = (ImageButton)findViewById(R.id.creditsButton);



    OrmHelper ormHelper = OpenHelperManager.getHelper(this,OrmHelper.class);
    ormHelper.getWritableDatabase().close();
    OpenHelperManager.releaseHelper();

    newGame.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Class activity = Class.forName("edu.cnm.deepdive.hexed0x29a.activities.NewGame");
          Intent intent = new Intent(TitleScreen.this, activity);
          startActivity(intent);
          //newGame()
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    });

    options.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Class activity = Class.forName("edu.cnm.deepdive.hexed0x29a.activities.Options");
          Intent intent = new Intent(TitleScreen.this, activity);
          startActivity(intent);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    });

    credits.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Class activity = Class.forName("edu.cnm.deepdive.hexed0x29a.activities.Credits");
          Intent intent = new Intent(TitleScreen.this, activity);
          startActivity(intent);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    });
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
    doBindService();
    Intent music = new Intent();
    music.putExtra(MusicService.TRACK_ID_KEY, R.raw.title_theme);
    music.setClass(this,MusicService.class);
    startService(music);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mServ.stopMusic();
    doUnbindService();
  }
}
