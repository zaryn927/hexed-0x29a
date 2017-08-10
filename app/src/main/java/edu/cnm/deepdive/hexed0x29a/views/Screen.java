package edu.cnm.deepdive.hexed0x29a.views;

import android.content.Context;
import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;


import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.entities.Artifact;
import edu.cnm.deepdive.hexed0x29a.entities.Char;
import edu.cnm.deepdive.hexed0x29a.entities.Terrain;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zaryn on 7/31/2017.
 */

public class Screen extends SurfaceView implements Runnable {

  Drawable background;

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
  Bitmap greenGem;
  Bitmap blueGem;
  Bitmap redGem;
  Bitmap pearl;
  Bitmap crystal;
  Bitmap hourglass;




  public Screen(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
    holder = getHolder();
    res = this.getResources();
    greenGem = BitmapFactory.decodeResource(res, R.drawable.green_gem);
    blueGem = BitmapFactory.decodeResource(res, R.drawable.blue_gem);
    redGem = BitmapFactory.decodeResource(res, R.drawable.red_gem);
    pearl = BitmapFactory.decodeResource(res, R.drawable.pearl);
    crystal = BitmapFactory.decodeResource(res, R.drawable.crystal);
    background = res.getDrawable(R.drawable.map);
    hourglass = BitmapFactory.decodeResource(res, R.drawable.hourglass);
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
    character.setName("Me");
    character.setX((int) (x/100));
    character.setY((int)(y/100));
    try {
      characterDao.create(character);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    double distFromCenter = 0;
    double scale =distFromCenter / 500 + 1;
    double moveDistance = 10 / scale;

    while(isRunning) {

      if (!holder.getSurface().isValid()) {
        continue;
      }
      Canvas canvas = holder.lockCanvas();

      if (upPressed) {
          if (!collisionDetection((int)-Math.ceil(x/100), (int)-Math.floor(y/100), 0, -1)) {
          y += moveDistance;
          updateCharacterLocation(character, x, y);
        } else {
//          y -= moveDistance*moveDistance;
        }
      } else if (downPressed) {
        if (!collisionDetection((int)-Math.ceil(x/100), (int)-(Math.ceil(y/100) + 1), 0, 1)) {
          y -= moveDistance;
        } else {
//          y += moveDistance*moveDistance;
        }
      } else if (rightPressed) {
        if (!collisionDetection((int)-(Math.ceil(x/100) + 1), (int)-Math.ceil(y/100), 1, 0)) {
          x -= moveDistance;
        } else {
//          x += moveDistance*moveDistance;
        }
      } else if (leftPressed) {
        if (!collisionDetection((int)-Math.floor(x/100), (int)-Math.ceil(y/100), -1, 0)) {
          x += moveDistance;
        } else {
//          x -= moveDistance*moveDistance;
        }
      }

      distFromCenter = Math.abs(x) + Math.abs(y);
      scale = distFromCenter / 500 + 1;
      //moveDistance = 10 / scale;
      canvas.scale((float) scale, (float) scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
      canvas.translate((float) (x + (canvas.getWidth() / 2)), (float) (y + (canvas.getHeight() / 2)));
      background.draw(canvas);
      drawArtifacts(canvas, queryArtifacts());
      holder.unlockCanvasAndPost(canvas);
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
      character.setY((int)(y / 100));
      character.setX((int)(x / 100));
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
      artifacts = queryBuilder.where().isNull("CHAR_ID").query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return artifacts;
  }

  public void drawArtifacts(Canvas canvas, List<Artifact> artifacts){
    for (Artifact artifact : artifacts) {
      float x = artifact.getX();
      float y = artifact.getY();
      String name = artifact.getArtifactType();
      Bitmap bitmap;
      switch (name) {
        case "blueGem":
          bitmap = blueGem;
          break;
        case "redGem":
          bitmap = redGem;
          break;
        case "greenGem":
          bitmap = greenGem;
          break;
        case "pearl":
          bitmap = pearl;
          break;
        case "crystal":
          bitmap = crystal;
          break;
        default:
          bitmap = hourglass;
      }
      canvas.drawBitmap( bitmap,x*100,y*100,null );
    }
  }
}
