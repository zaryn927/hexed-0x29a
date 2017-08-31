package edu.cnm.deepdive.hexed0x29a.views;

import android.content.Context;
import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;


import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.activities.NewGame;
import edu.cnm.deepdive.hexed0x29a.entities.Artifact;
import edu.cnm.deepdive.hexed0x29a.entities.Char;
import edu.cnm.deepdive.hexed0x29a.entities.Terrain;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;

import edu.cnm.deepdive.hexed0x29a.rest_client.Game;
import edu.cnm.deepdive.hexed0x29a.rest_client.GameTraffic;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zaryn on 7/31/2017.
 */

public class Screen extends SurfaceView implements Runnable {
  static final int UPDATE_INTERVAL = 30;
  static final int WORLD_VIEW_SIZE = 50;
  Bitmap[][] background;
  Terrain[][] backgroundTiles;

  SurfaceHolder holder;
  Thread renderThread = null;



  volatile boolean isRunning = false;
  public void setUpPressed(boolean upPressed) {
    this.upPressed = upPressed;
  }
  public void setDownPressed(boolean downPressed) {
    this.downPressed = downPressed;
  }
  public void setRightPressed(boolean rightPressed) {
    this.rightPressed = rightPressed;
  }
  public void setLeftPressed(boolean leftPressed) {
    this.leftPressed = leftPressed;
  }
  private boolean upPressed;
  private boolean downPressed;
  private boolean rightPressed;
  private boolean leftPressed;

  private OrmHelper dbHelper = null;
  Dao<Char,Integer> characterDao;
  Dao<Artifact, Integer> artifactDao;
  Dao<Terrain, Integer> terrainDao;
  Resources res;
//  Bitmap greenGem;
//  Bitmap blueGem;
//  Bitmap redGem;
//  Bitmap pearl;
//  Bitmap crystal;
//  Bitmap hourglass;
  Bitmap deepWater;
  Bitmap shallowWater;
  Bitmap sand;
  Bitmap grass;
  Bitmap rock;
  Bitmap artifact1;
  Bitmap artifact2;
  Bitmap artifact3;
  Bitmap artifact4;
  Bitmap artifact5;
  Bitmap artifact6;
  Bitmap artifact7;
  Bitmap artifact8;
  Bitmap artifact9;
  Bitmap artifact10;
  Bitmap artifact11;
  Bitmap artifact12;
  Bitmap artifact13;
  Bitmap artifact14;
  Bitmap artifact15;
  Context context;



  public Screen(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    setWillNotDraw(false);
    holder = getHolder();
    res = this.getResources();
    deepWater = BitmapFactory.decodeResource(res, R.drawable.deep_water);
    shallowWater = BitmapFactory.decodeResource(res, R.drawable.shallow_water);
    sand = BitmapFactory.decodeResource(res, R.drawable.sand);
    grass = BitmapFactory.decodeResource(res, R.drawable.grass);
    rock = BitmapFactory.decodeResource(res, R.drawable.rock);
    artifact1 = BitmapFactory.decodeResource(res, R.drawable.artifact_1);
    artifact2 = BitmapFactory.decodeResource(res, R.drawable.artifact_2);
    artifact3 = BitmapFactory.decodeResource(res, R.drawable.artifact_3);
    artifact4 = BitmapFactory.decodeResource(res, R.drawable.artifact_4);
    artifact5 = BitmapFactory.decodeResource(res, R.drawable.artifact_5);
    artifact6 = BitmapFactory.decodeResource(res, R.drawable.artifact_6);
    artifact7 = BitmapFactory.decodeResource(res, R.drawable.artifact_7);
    artifact8 = BitmapFactory.decodeResource(res, R.drawable.artifact_8);
    artifact9 = BitmapFactory.decodeResource(res, R.drawable.artifact_9);
    artifact10 = BitmapFactory.decodeResource(res, R.drawable.artifact_10);
    artifact11 = BitmapFactory.decodeResource(res, R.drawable.artifact_11);
    artifact12 = BitmapFactory.decodeResource(res, R.drawable.artifact_12);
    artifact13 = BitmapFactory.decodeResource(res, R.drawable.artifact_13);
    artifact14 = BitmapFactory.decodeResource(res, R.drawable.artifact_14);
    artifact15 = BitmapFactory.decodeResource(res, R.drawable.artifact_15);
//    background = new Bitmap[WORLD_VIEW_SIZE][WORLD_VIEW_SIZE];
    backgroundTiles = new Terrain[WORLD_VIEW_SIZE][WORLD_VIEW_SIZE];
    int top = - (WORLD_VIEW_SIZE/2);
    int left = - (WORLD_VIEW_SIZE/2);
    try {
      QueryBuilder<Terrain, Integer> queryBuilder = getHelper().getTerrainDao().queryBuilder();
      queryBuilder.where().between("X_LOC", left, left + WORLD_VIEW_SIZE - 1)
          .and().between("Y_LOC", top, top + WORLD_VIEW_SIZE - 1);
      List<Terrain> tiles = getHelper().getTerrainDao().query(queryBuilder.prepare());
      for (Terrain tile : tiles) {
        // TODO row = tile.gety - top, col = tile.getx - left
        int row = tile.getY() - top;
        int col = tile.getX() - left;
        double elevation = tile.getElevation();
        if (elevation < -0.6){
          tile.setTile(deepWater);
        }else if (elevation < -0.1){
          tile.setTile(shallowWater);
        }else if (elevation < 0.0){
          tile.setTile(sand);
        }else if (elevation < 0.3){
          tile.setTile(grass);
        }else {
          tile.setTile(rock);
        }
        // TODO backgroundTiles[y-top][x-left] = tile
        backgroundTiles[row][col] = tile;
      }
    } catch (SQLException e) {
      //Do nothing
    }
//    for (int i = 0; i < WORLD_VIEW_SIZE; i++){
//      for (int j = 0; j < WORLD_VIEW_SIZE; j++) {
//        Terrain terrain ;
//        double elevation = -0.05;
//        if (elevation < -0.6){
//          background[i][j] = deepWater;
//        }else if (elevation >= -0.6 && elevation < -0.1){
//          background[i][j] = shallowWater;
//        }else if (elevation >= -0.1 && elevation < 0.0){
//          background[i][j] = sand;
//        }else{
//          background[i][j] = grass;
//        }
//
//      }
//    }

  }

  private synchronized OrmHelper getHelper() {
    if (dbHelper == null) {
      dbHelper = OpenHelperManager.getHelper(getContext(), OrmHelper.class);
    }
    return dbHelper;
  }

  private synchronized void releaseHelper() {
    if (dbHelper != null) {
      OpenHelperManager.releaseHelper();
      dbHelper = null;
    }
  }

  public void resume() {
    isRunning = true;
    renderThread = new Thread(this);
    renderThread.start();
  }

  @Override
  public void run() {
    try {
      characterDao = getHelper().getCharDao();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    double x = 0.0;
    double y = 0.0;
    Char character = new Char();
//    character.setName("Me");
    character.setX((int) (x/64));
    character.setY((int)(y/64));
//    try {
//      characterDao.create(character);
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    double distFromCenter = 0;
//    double scale = 1;
    double moveDistance = 10 ;
    long tickCounter = 0;
    while(isRunning) {

      if (!holder.getSurface().isValid()) {
        continue;
      }
      Canvas canvas = holder.lockCanvas();

      if (upPressed) {
          y += moveDistance;
          updateCharacterLocation(character, x, y);
      }else if (downPressed) {
          y -= moveDistance;
          updateCharacterLocation(character, x, y);
      }else if (rightPressed) {
          x -= moveDistance;
          updateCharacterLocation(character,x,y);
      }else if (leftPressed) {
          x += moveDistance;
          updateCharacterLocation(character,x,y);
      }

//      distFromCenter = Math.abs(x) + Math.abs(y);
//      scale = distFromCenter / 500 + 1;
//      canvas.scale((float) scale, (float) scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
      canvas.translate((float) (x + (canvas.getWidth() / 2)), (float) (y + (canvas.getHeight() / 2)));
      canvas.drawColor(Color.BLACK);
      for (int i = 0; i < WORLD_VIEW_SIZE; i++){
        for(int j = 0; j < WORLD_VIEW_SIZE; j++ ) {
          Terrain tile = backgroundTiles[i][j];
          canvas.drawBitmap(tile.getTile(), tile.getX() * 64, tile.getY() * 64, null);
//          canvas.drawBitmap(background[i][j], j*64 - canvas.getWidth() / 2, i*64 - canvas.getHeight() / 2,null);
        }
      }
      drawArtifacts(canvas, character, queryArtifacts());
      holder.unlockCanvasAndPost(canvas);
      if (++tickCounter % UPDATE_INTERVAL == 0){
        int top = character.getY() - (WORLD_VIEW_SIZE/2);
        int left = character.getX() - (WORLD_VIEW_SIZE/2);
        try {
          QueryBuilder<Terrain, Integer> queryBuilder = getHelper().getTerrainDao().queryBuilder();
          queryBuilder.where().between("X_LOC", left, left + WORLD_VIEW_SIZE - 1)
              .and().between("Y_LOC", top, top + WORLD_VIEW_SIZE - 1);
          List<Terrain> tiles = getHelper().getTerrainDao().query(queryBuilder.prepare());
          for (Terrain tile : tiles) {
            // TODO row = tile.gety - top, col = tile.getx - left
            int row = tile.getY() - top;
            int col = tile.getX() - left;
            double elevation = tile.getElevation();
            if (elevation < -0.6){
              tile.setTile(deepWater);
            }else if (elevation < -0.1){
              tile.setTile(shallowWater);
            }else if (elevation < 0.0){
              tile.setTile(sand);
            }else if (elevation < 0.3){
              tile.setTile(grass);
            }else {
              tile.setTile(rock);
            }
            // TODO backgroundTiles[y-top][x-left] = tile
            backgroundTiles[row][col] = tile;
          }
        } catch (SQLException e) {
          //do nothing
        }
       GameTraffic.getInstance(null).gameUpdate(((NewGame)context).getGameId(), character.getX(),character.getY(),null,null,null);
      }
    }
  }

  public void pause(){
    isRunning = false;
    while (true) {
      try {
        renderThread.join();
        return;
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }

  public void updateCharacterLocation(Char character, double x, double y){
      character.setY((int)-Math.floor(y / 64)-1);
    Log.d("character", character.toString());
      character.setX((int)-Math.floor(x / 64)-1);
    try {
      characterDao.update(character);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean collisionDetection (int x, int y, int dx, int dy) {
    Terrain t = null;
    try {
      terrainDao = getHelper().getTerrainDao();
      QueryBuilder<Terrain, Integer> terrainQuery = terrainDao.queryBuilder();
      t = terrainQuery.where().eq("X_LOC", x + dx).and().eq("Y_LOC", y + dy).queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return t == null || t.isBlocked();
  }

  public List<Artifact> queryArtifacts() {
    List<Artifact> artifacts = null;
    try {
      artifactDao = getHelper().getArtifactDao();
      QueryBuilder<Artifact, Integer> queryBuilder = artifactDao.queryBuilder();
      artifacts = queryBuilder.where().eq("COLLECTED", false).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return artifacts;
  }

  public void drawArtifacts(Canvas canvas, Char character, List<Artifact> artifacts){
    float charX = character.getX();
    float charY = character.getY();
    for (Artifact artifact : artifacts) {
      float x = artifact.getX();
      float y = artifact.getY();
      String name = artifact.getArtifactType();
      Bitmap bitmap;
      switch (name) {
        case "Artifact1":
          bitmap = artifact1;
          break;
        case "Artifact2":
          bitmap = artifact2;
          break;
        case "Artifact3":
          bitmap = artifact3;
          break;
        case "Artifact4":
          bitmap = artifact4;
          break;
        case "Artifact5":
          bitmap = artifact5;
          break;
        case "Artifact6":
          bitmap = artifact6;
          break;
        case "Artifact7":
          bitmap = artifact7;
          break;
        case "Artifact8":
          bitmap = artifact8;
          break;
        case "Artifact9":
          bitmap = artifact9;
          break;
        case "Artifact10":
          bitmap = artifact10;
          break;
        case "Artifact11":
          bitmap = artifact11;
          break;
        case "Artifact12":
          bitmap = artifact12;
          break;
        case "Artifact13":
          bitmap = artifact13;
          break;
        case "Artifact14":
          bitmap = artifact14;
          break;
        case "Artifact15":
          bitmap = artifact15;
          break;

        default:
          bitmap = null;
      }
      if(Math.abs(x - charX) < 0.1 && Math.abs(y - charY) < 0.1) {
        try {
          artifactDao = getHelper().getArtifactDao();
          artifact.setCollected(true);
          artifactDao.update(artifact);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else {
        canvas.drawBitmap(bitmap, x * 64, y * 64, null);
//        Log.d("artifact", artifact.toString());
      }
    }
  }
}
