package edu.cnm.deepdive.hexed0x29a.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.entities.Char;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;
import java.sql.SQLException;

public class DataEntryActivity extends AppCompatActivity {
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
    setContentView(R.layout.activity_data_entry);

    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Dao<Char, Integer> dao = getHelper().getCharDao();
          Char character = new Char();
          EditText editText = (EditText) findViewById(R.id.editText);
          character.setName(editText.getText().toString());
          dao.create(character);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
