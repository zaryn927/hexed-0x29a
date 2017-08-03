package edu.cnm.deepdive.hexed0x29a.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;

public class TitleScreen extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_title_screen);

    OrmHelper ormHelper = OpenHelperManager.getHelper(this,OrmHelper.class);
    ormHelper.getWritableDatabase().close();
    OpenHelperManager.releaseHelper();
  }

}
