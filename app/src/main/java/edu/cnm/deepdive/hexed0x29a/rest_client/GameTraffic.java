package edu.cnm.deepdive.hexed0x29a.rest_client;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.entities.Artifact;
import edu.cnm.deepdive.hexed0x29a.entities.Terrain;
import edu.cnm.deepdive.hexed0x29a.helpers.OrmHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;


/**
 * Created by kelly on 8/24/17.
 */

public class GameTraffic {

  private static GameTraffic instance = null;
  public Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  private Context context;
  private OrmHelper dbHelper = null;

  private GameTraffic(Context context) {
    this.context = context;
  }

  private synchronized OrmHelper getHelper() {
    if (dbHelper == null) {
      dbHelper = OpenHelperManager.getHelper(context, OrmHelper.class);
    }
    return dbHelper;
  }

  private synchronized void releaseHelper() {
    if (dbHelper != null) {
      OpenHelperManager.releaseHelper();
      dbHelper = null;
    }
  }

  public static GameTraffic getInstance(Context context) { // TODO use this for set game id
    if (instance == null) {
      instance = new GameTraffic(context);
    }
    return instance;
  }

  private static Reader serverCom(String payload, String url, String httpMethod)
      throws IOException {

    HttpURLConnection connection = null;
    URL hexedUrl = new URL(url);
    connection = (HttpURLConnection) hexedUrl.openConnection();

    connection.setRequestMethod(httpMethod);
    if (payload != null && payload.length() > 0) {
      connection.setRequestProperty("Content-Type", "application/json");
      connection.getOutputStream().write(payload.getBytes());
    }
    //TODO check http status (value depends on http method)
    InputStream input = connection.getInputStream();
    InputStreamReader reader = new InputStreamReader(input);

    return reader;
  }

  public void newGame(final int size) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          Game game = new Game();
          game.size = size;
          game = gson
              .fromJson(
                  serverCom(gson.toJson(game), context.getResources().getString(R.string.base_url)
                      + context.getResources().getString(R.string.post_game), "POST"), Game.class);
          // TODO write tile objects from game to database (local entities)
          for (int i = 0; i < game.neighborhood.tiles.length; i++) {
            for (int j = 0; j < game.neighborhood.tiles[i].length; j++) {
              try {
                //TODO write contents of game.neighborhood.tiles[i][j] to database
                Game.Neighborhood.Tile tile = game.neighborhood.tiles[i][j];
                Terrain entity = new Terrain();
                entity.setX(tile.x);
                entity.setY(tile.y);
                if (tile.artifact != null) {
                  Artifact artifact = new Artifact();
                  artifact.setX(tile.x);
                  artifact.setY(tile.y);
                  artifact.setArtifactType(tile.artifact.type);
                  artifact.setCollected(false);
                  getHelper().getArtifactDao().create(artifact);
                  entity.setArtifact(artifact);
                }
                getHelper().getTerrainDao().create(entity);
              } catch (SQLException e) {
                e.printStackTrace();
              }
              //TODO set remaining fields
            }
          }
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    };
    new Thread(task).start();
  }


  public void gameUpdate(final Integer id, final Integer x, final Integer y, final Integer score,
      final Boolean finished, final String playerName) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        boolean locationUpdate = false;
        try {
          Game game = new Game();
          if (x != null) {
            game.x = x;
            locationUpdate = true;
          }
          if (y != null) {
            game.y = y;
            locationUpdate = true;
          }
          if (score != null) {
            game.score = score;
          }
          if (finished != null) {
            game.finished = finished;
          }
          if (playerName != null) {
            game.player = playerName;
          }

          serverCom(gson.toJson(game), context.getResources().getString(R.string.base_url)
              + String.format(context.getResources().getString(R.string.put_game), id), "PUT");
          //TODO if (locationUpdate) {sleep some interval then get the neighborhood}
          if (locationUpdate) {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException ex) {
              ex.printStackTrace();
            }
          }
          // TODO Get tiles

        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();

  }

  public void artCollected(final Boolean collected) { //game and artifact id
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {

          // update local entity?
          serverCom(gson.toJson(collected), context.getResources().getString(R.string.base_url)
              + context.getResources().getString(R.string.put_artifact), "PUT");

        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();
  }

//  public Integer gameIdentity(final Integer id) { // resume, syncrhonize, SHOW NICK
//
//    Runnable task = new Runnable() {
//      @Override
//      public void run() {
//        try {
//
//          Game game = gson
//              .fromJson(serverCom(null, context.getResources().getString(R.string.base_url)
//                  + context.getResources().getString(R.string.get_game), "GET"), Game.class);
//          // TODO Update tiles & artifacts in local database, based on what was received. Ignore
//          // TODO exceptions caused by unique key violations.
//        } catch (IOException ex) {
//          throw new RuntimeException(ex);
//        }
//      }
//
//    };
//    new Thread(task).start();
//    return id;
//  }

  public void HScores() {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          Game[] games = gson
              .fromJson(serverCom(null, context.getResources().getString(R.string.base_url)
                  + context.getResources().getString(R.string.get_all_games), "GET"), Game[].class);
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    };
    new Thread(task).start();
  }
}
