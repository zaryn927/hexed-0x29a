package edu.cnm.deepdive.hexed0x29a.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;

public class TitleScreen extends ListActivity {

  String[] views = {"NewGame", "Options", "Credits", "AndroidDatabaseManager", "TestActivity"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OrmHelper ormHelper = OpenHelperManager.getHelper(this,OrmHelper.class);
    ormHelper.getWritableDatabase().close();
    OpenHelperManager.releaseHelper();

    setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, views));
  }

  @Override
  protected void onListItemClick(ListView list, View view, int position, long id) {
    super.onListItemClick(list, view, position, id);
    String viewName = views[position];
    try {
      Class activity = Class.forName("edu.cnm.deepdive.hexed0x29a.activities." + viewName);
      Intent intent = new Intent(this, activity);
      startActivity(intent);
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
  }
}
