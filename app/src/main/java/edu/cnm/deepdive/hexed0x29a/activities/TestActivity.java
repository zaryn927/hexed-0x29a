package edu.cnm.deepdive.hexed0x29a.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.entities.Char;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;
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
      Dao<Char, Integer> dao = getHelper().getCharDao();
      List<Char> characters = dao.queryForAll();
      ArrayAdapter<Char> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, characters);
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
