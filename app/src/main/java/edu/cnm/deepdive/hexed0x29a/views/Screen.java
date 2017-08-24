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



import com.j256.ormlite.dao.Dao;

import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.activities.NewGame;
import edu.cnm.deepdive.hexed0x29a.entities.Artifact;
import edu.cnm.deepdive.hexed0x29a.entities.Char;
import edu.cnm.deepdive.hexed0x29a.entities.Terrain;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;

import edu.cnm.deepdive.hexed0x29a.helpers.PerlinNoise;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Created by zaryn on 7/31/2017.
 */

public class Screen extends SurfaceView implements Runnable {

  //Drawable background;

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
  Bitmap[][] background;
  Bitmap greenGem;
  Bitmap blueGem;
  Bitmap redGem;
  Bitmap pearl;
  Bitmap crystal;
  Bitmap hourglass;
  Bitmap grass;
  Bitmap deepWater;
  Bitmap shallowWater;
  Bitmap sand;
  Random rng;
  private static final int WORLD_SIZE = 96;
  private static final int NOISE_BLOCK_SIZE = 8;





  public Screen(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
    dbHelper = ((NewGame) context).getHelper();
    holder = getHolder();
    res = this.getResources();
    greenGem = BitmapFactory.decodeResource(res, R.drawable.green_gem);
    blueGem = BitmapFactory.decodeResource(res, R.drawable.blue_gem);
    redGem = BitmapFactory.decodeResource(res, R.drawable.red_gem);
    pearl = BitmapFactory.decodeResource(res, R.drawable.pearl);
    crystal = BitmapFactory.decodeResource(res, R.drawable.crystal);
    hourglass = BitmapFactory.decodeResource(res, R.drawable.hourglass);
    deepWater = BitmapFactory.decodeResource(res, R.drawable.deep_water);
    shallowWater = BitmapFactory.decodeResource(res, R.drawable.shallow_water);
    sand = BitmapFactory.decodeResource(res, R.drawable.sand);
    grass = BitmapFactory.decodeResource(res, R.drawable.grass);
    //background = res.getDrawable(R.drawable.map);
    background = new Bitmap[96][96];
    rng = new Random();
//    int[][] gradients = new int[4][];
//    for (int i = 0; i < gradients.length; i++){
//      int selector = rng.nextInt(4);
//      gradients[i] = new int[]{
//          (selector / 2 == 0)? -1 : 1,
//          (selector % 2 == 0)? -1 : 1
//      };
//    }
    int[][][] gradients = new int[WORLD_SIZE/NOISE_BLOCK_SIZE+1][WORLD_SIZE/NOISE_BLOCK_SIZE+1][];
    for(int i = 0; i < gradients.length; i++){
      for(int j = 0; j < gradients[i].length; j++){
        int selector = rng.nextInt(4);
        gradients[i][j] = new int[]{
            (selector / 2 == 0)? -1 : 1,
            (selector % 2 == 0)? -1 : 1
        };
      }
    }
    PerlinNoise generator = new PerlinNoise(NOISE_BLOCK_SIZE);

    for (int i = 0; i < WORLD_SIZE; i++){
      for (int j = 0; j < WORLD_SIZE; j++) {
        int gradientRow = i/NOISE_BLOCK_SIZE;
        int gradientCol = j/NOISE_BLOCK_SIZE;
        int [][] localGradients = {
            gradients[gradientRow][gradientCol],
            gradients[gradientRow + 1][gradientCol],
            gradients[gradientRow + 1][gradientCol + 1],
            gradients[gradientRow][gradientCol + 1]
        };
        double elevation = generator.apply(localGradients, j % NOISE_BLOCK_SIZE, i % NOISE_BLOCK_SIZE);
        if (elevation < -0.6){
          background[i][j] = deepWater;
        }else if (elevation >= -0.6 && elevation < -0.1){
          background[i][j] = shallowWater;
        }else if (elevation >= -0.1 && elevation < 0.0){
          background[i][j] = sand;
        }else{
          background[i][j] = grass;
        }

      }
    }
    try {
      artifactDao = dbHelper.getArtifactDao();
      Artifact artifact = new Artifact();
      artifact.setArtifactType("greenGem");
      artifact.setX(0);
      artifact.setY(3);
      artifact.setCharacter(null);
      artifactDao.create(artifact);

      artifact = new Artifact();
      artifact.setArtifactType("blueGem");
      artifact.setX(16);
      artifact.setY(-13);
      artifact.setCharacter(null);
      artifactDao.create(artifact);

      artifact = new Artifact();
      artifact.setArtifactType("redGem");
      artifact.setX(-10);
      artifact.setY(11);
      artifact.setCharacter(null);
      artifactDao.create(artifact);

      artifact = new Artifact();
      artifact.setArtifactType("pearl");
      artifact.setX(16);
      artifact.setY(10);
      artifact.setCharacter(null);
      artifactDao.create(artifact);

      artifact = new Artifact();
      artifact.setArtifactType("crystal");
      artifact.setX(-17);
      artifact.setY(-11);
      artifact.setCharacter(null);
      artifactDao.create(artifact);
    } catch (SQLException e) {
      e.printStackTrace();
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
      characterDao = dbHelper.getCharDao();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    double x = 0.0;
    double y = 0.0;
    Char character = new Char();
    character.setName("Me");
    character.setX((int) (x/32));
    character.setY((int)(y/32));
    try {
      characterDao.create(character);
    } catch (SQLException e) {
      e.printStackTrace();
    }
//    double distFromCenter = 0;
//    double scale = 1;
    double moveDistance = 25 ;

    while(isRunning) {

      if (!holder.getSurface().isValid()) {
        continue;
      }
      Canvas canvas = holder.lockCanvas();

      if (upPressed) {
//          if (!collisionDetection((int)-Math.ceil(x/100), (int)-Math.floor(y/100), 0, -1)) {
          y += moveDistance;
          updateCharacterLocation(character, x, y);
//        } else {
//          y -= moveDistance*moveDistance;
//        }
      } else if (downPressed) {
//        if (!collisionDetection((int)-Math.ceil(x/100), (int)-(Math.ceil(y/100) + 1), 0, 1)) {
          y -= moveDistance;
          updateCharacterLocation(character, x, y);
//        } else {
//          y += moveDistance*moveDistance;
//        }
      } else if (rightPressed) {
//        if (!collisionDetection((int)-(Math.ceil(x/100) + 1), (int)-Math.ceil(y/100), 1, 0)) {
          x -= moveDistance;
          updateCharacterLocation(character,x,y);
//          }
//        } else {
//          x += moveDistance*moveDistance;
//        }
      } else if (leftPressed) {
//        if (!collisionDetection((int)-Math.floor(x/100), (int)-Math.ceil(y/100), -1, 0)) {
          x += moveDistance;
          updateCharacterLocation(character,x,y);
//          }
//        } else {
//          x -= moveDistance*moveDistance;
//        }
      }

//      distFromCenter = Math.abs(x) + Math.abs(y);
      //scale = distFromCenter / 500 + 1;
      //canvas.scale((float) scale, (float) scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
      canvas.translate((float) x, (float) y);
      //background.draw(canvas);
      canvas.drawColor(Color.MAGENTA);
      for (int i = 0; i < 96; i++){
        for(int j = 0; j < 96; j++ ) {
          canvas.drawBitmap(background[i][j], j*64 - canvas.getWidth() / 2, i*64 - canvas.getHeight() / 2,null);
        }
      }
      drawArtifacts(canvas, character, queryArtifacts());
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
      character.setY((int)-Math.floor(y / 32)-1);
    Log.d("character", character.toString());
      character.setX((int)-Math.floor(x / 32)-1);
    try {
      characterDao.update(character);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean collisionDetection (int x, int y, int dx, int dy) {
    Terrain t = null;
    try {
      terrainDao = dbHelper.getTerrainDao();
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
      artifactDao = dbHelper.getArtifactDao();
      QueryBuilder<Artifact, Integer> queryBuilder = artifactDao.queryBuilder();
      artifacts = queryBuilder.where().isNull("CHAR_ID").query();
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
      if(Math.abs(x - charX) < 0.1 && Math.abs(y - charY) < 0.1) {
        try {
          artifactDao = dbHelper.getArtifactDao();
          artifact.setCharacter(character);
          artifactDao.update(artifact);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else {
        canvas.drawBitmap(bitmap, x * 32, y * 32, null);
        Log.d("artifact", artifact.toString());
      }
    }
  }
}
