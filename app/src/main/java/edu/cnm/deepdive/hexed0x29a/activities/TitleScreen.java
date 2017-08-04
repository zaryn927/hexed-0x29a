package edu.cnm.deepdive.hexed0x29a.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class TitleScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_title_screen);

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




}
