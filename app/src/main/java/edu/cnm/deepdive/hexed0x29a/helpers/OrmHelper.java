package edu.cnm.deepdive.hexed0x29a.helpers;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
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
import java.util.ArrayList;
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

  private static ConnectionSource source;

  public OrmHelper (Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    try {
      source = connectionSource;
      TableUtils.createTable(connectionSource, Char.class);
      TableUtils.createTable(connectionSource, Terrain.class);
      TableUtils.createTable(connectionSource, TerrainType.class);
      TableUtils.createTable(connectionSource, Artifact.class);
      TableUtils.createTable(connectionSource, ArtifactType.class);
      populateDB();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }


  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {

  }

  private void populateDB() throws SQLException{
    Char player = new Char();
    player.setName("Player 1");
    player.setNpc(false);
    player.setHealth(100);
    player.setStamina(100);
    player.setStaminaRechargeRate(5.0);
    player.setX(0);
    player.setY(0);
    getCharDao().create(player);

    player = new Char();
    player.setName("Player 2");
    player.setNpc(false);
    player.setHealth(100);
    player.setStamina(100);
    player.setStaminaRechargeRate(5.0);
    player.setX(49);
    player.setY(49);
    getCharDao().create(player);

    ArtifactType artifactType = new ArtifactType();
    artifactType.setArtifactName("token");
    artifactType.setCollectible(true);
    artifactType.setDroppable(false);
    getArtTypDao().create(artifactType);

    Artifact artifact = new Artifact();
    artifact.setArtifactType(artifactType);
    artifact.setX(32);
    artifact.setY(32);
    getArtifactDao().create(artifact);

    artifactType = new ArtifactType();
    artifactType.setArtifactName("weapon");
    artifactType.setCollectible(true);
    artifactType.setDroppable(false);
    getArtTypDao().create(artifactType);

    artifact = new Artifact();
    artifact.setArtifactType(artifactType);
    artifact.setCharacter(player);
    getArtifactDao().create(artifact);


  }

  public ArrayList<Cursor> getData(String Query){
    //get writable database
    SQLiteDatabase sqlDB = this.getWritableDatabase();
    String[] columns = new String[] { "message" };
    //an array list of cursor to save two cursors one has results from the query
    //other cursor stores error message if any errors are triggered
    ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
    MatrixCursor Cursor2= new MatrixCursor(columns);
    alc.add(null);
    alc.add(null);

    try{
      String maxQuery = Query ;
      //execute the query results will be save in Cursor c
      Cursor c = sqlDB.rawQuery(maxQuery, null);

      //add value to cursor2
      Cursor2.addRow(new Object[] { "Success" });

      alc.set(1,Cursor2);
      if (null != c && c.getCount() > 0) {

        alc.set(0,c);
        c.moveToFirst();

        return alc ;
      }
      return alc;
    } catch(Exception ex){
      Log.d("printing exception", ex.getMessage());

      //if any exceptions are triggered save the error message to cursor an return the arraylist
      Cursor2.addRow(new Object[] { ""+ex.getMessage() });
      alc.set(1,Cursor2);
      return alc;
    }
  }

  public synchronized Dao<Char, Integer> getCharDao() throws SQLException {
    if (charDao == null) {
      charDao = getDao(Char.class);
    }
    return charDao;
  }
  public synchronized Dao<Terrain, Integer> getTerrainDao() throws SQLException {
  if (terrainDao == null) {
    terrainDao = getDao(Terrain.class);
  }
  return terrainDao;
}
  public synchronized Dao<TerrainType, Integer> getTerTypDao() throws SQLException {
  if (terTypDao == null) {
    terTypDao = getDao(TerrainType.class);
  }
  return terTypDao;
}
  public synchronized Dao<Artifact, Integer> getArtifactDao() throws SQLException {
  if (artifactDao == null) {
    artifactDao = getDao(Artifact.class);
  }
  return artifactDao;
}
  public synchronized Dao<ArtifactType, Integer> getArtTypDao() throws SQLException {
  if (artTypDao == null) {
    artTypDao = getDao(ArtifactType.class);
  }
  return artTypDao;
}

}
