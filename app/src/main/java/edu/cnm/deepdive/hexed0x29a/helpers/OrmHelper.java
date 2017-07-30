package edu.cnm.deepdive.hexed0x29a.helpers;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.entities.Artifact;
import edu.cnm.deepdive.hexed0x29a.entities.ArtifactType;
import edu.cnm.deepdive.hexed0x29a.entities.Char;
import edu.cnm.deepdive.hexed0x29a.entities.Terrain;
import edu.cnm.deepdive.hexed0x29a.entities.TerrainType;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zaryn on 7/27/2017.
 */

public class OrmHelper extends OrmLiteSqliteOpenHelper {

  private static final String DATABASE_NAME = "hexed.db";
  private static final int DATABASE_VERSION = 1;

  private Dao<Char, Integer> charDao = null;
  private Dao<Terrain, Integer> terrainDao = null;
  private Dao<TerrainType, Integer> terTypDao = null;
  private Dao<Artifact, Integer> artifactDao = null;
  private Dao<ArtifactType, Integer> artTypDao = null;

  private RuntimeExceptionDao<Char, Integer> charRuntimeDao = null;
  private RuntimeExceptionDao<Terrain, Integer> terrainRuntimeDao = null;
  private RuntimeExceptionDao<TerrainType, Integer> terTypRuntimeDao = null;
  private RuntimeExceptionDao<Artifact, Integer> artifactRuntimeDao = null;
  private RuntimeExceptionDao<ArtifactType, Integer> artTypRuntimeDao = null;



  public OrmHelper (Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, Char.class);
      TableUtils.createTable(connectionSource, Terrain.class);
      TableUtils.createTable(connectionSource, TerrainType.class);
      TableUtils.createTable(connectionSource, Artifact.class);
      TableUtils.createTable(connectionSource, ArtifactType.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      charDao = getDao(Char.class);

      Char character = new Char();
      character.setName("Player 1");
      character.setHealth(100);
      character.setNpc(false);
      character.setStamina(100);
      character.setStaminaRechargeRate(5.0);
      character.setX(0);
      character.setY(0);

      charDao.create(character);

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }


  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {

  }

  public Dao<Char, Integer> getDao() throws SQLException {
    if (charDao == null) {
      charDao = getDao(Char.class);
    }
    return charDao;
  }


}
