package edu.cnm.deepdive.hexed0x29a_demo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import edu.cnm.deepdive.hexed0x29a_demo.R;
import edu.cnm.deepdive.hexed0x29a_demo.entities.Terrain;
import edu.cnm.deepdive.hexed0x29a_demo.helpers.OrmHelper;
import java.sql.SQLException;
import java.util.List;

public class TestActivity extends AppCompatActivity {

  private OrmHelper dbHelper = null;

  private synchronized OrmHelper getHelper() {
    if (dbHelper == null) {
      dbHelper = OpenHelperManager.getHelper(this, OrmHelper.class);
    }
    return dbHelper;
  }

  private synchronized void releaseHelper() {
    if (dbHelper != null) {
      OpenHelperManager.releaseHelper();
      dbHelper = null;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    try {
      Dao<Terrain, Integer> dao = getHelper().getTerrainDao();
      List<Terrain> characters = dao.queryForAll();
      ArrayAdapter<Terrain> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, characters);
      ((ListView) findViewById(R.id.queryList)).setAdapter(adapter);
    }catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  protected void onDestroy() {
    releaseHelper();
    super.onDestroy();
  }
}
