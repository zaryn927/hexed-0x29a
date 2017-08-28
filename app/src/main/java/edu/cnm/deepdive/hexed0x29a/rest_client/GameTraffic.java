package edu.cnm.deepdive.hexed0x29a.rest_client;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import edu.cnm.deepdive.hexed0x29a.R;
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

  private GameTraffic(Context context){
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
  public GameTraffic getInstance(Context context){
    if (instance == null){
      instance = new GameTraffic(context);
    }
    return instance;
  }

  private static Reader serverCom(String payload, String url, String httpMethod) throws IOException{

    HttpURLConnection connection = null;
    URL hexedUrl = new URL(url);
    connection = (HttpURLConnection) hexedUrl.openConnection();

    connection.setRequestMethod(httpMethod);
    if (payload != null && payload.length() > 0) {
      connection.setRequestProperty("Content-Type", "application/json");
      connection.getOutputStream().write(payload.getBytes());
    }

    InputStream input = connection.getInputStream();
    InputStreamReader reader = new InputStreamReader(input);

    return reader;
  }

  public void newGame(final int genGame) { //changed from hoodSize
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          Game game = new Game();
          game.size = genGame;
          game = gson.fromJson(serverCom(gson.toJson(game), context.getResources().getString(R.string.base_url)
            +context.getResources().getString(R.string.post_game) , "POST"), Game.class);
          // TODO write tile objects from game to database (local entities)
          for (int i = 0; i < game.neighborhood.tiles.length; i++) {
            for (int j = 0; j < game.neighborhood.tiles[i].length; j++) {
              //TODO write contents of game.neighborhood.tiles[i][j] to database
              Game.Neighborhood.Tile tile = game.neighborhood.tiles[i][j];
              Terrain entity = new Terrain();
              entity.setX(tile.x);
              entity.setY(tile.y);
              //TODO set remaining fields
              try {
                getHelper().getTerrainDao().create(entity);
              } catch (SQLException e) {
                e.printStackTrace();
              }
            }
          }
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();
  }

  public void gameUpdate(final int update) { //added getters and setters
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          Object payload = new Object() {
            @Expose
            private
            int score = 0;
            private int x = 0;
            private int y = 0;
            private String playerName = "";
            private boolean finished = false;


          };
          // TODO create game class for deserialization
          serverCom(gson.toJson(payload), context.getResources().getString(R.string.base_url)
              +context.getResources().getString(R.string.put_game), "PUT");


        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();
  }

  public void artCollected(boolean collected) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          Object payload = new Object() {
            @Expose
            boolean collected = false;
          };
          // TODO create game class for deserialization
          serverCom(gson.toJson(payload), "put_artifact", "PUT");

        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();
  }


  public void gameIdentity(final int gameId) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          //TODO create payload
          Object payload = new Object() {
            @Expose
            int id = gameId;
          };
          serverCom(gson.toJson(payload), "get_game", "GET");

        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();

  }
  public void HScores(final int highScores) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          //TODO create payload
          Object payload = new Object() {
            @Expose
            int[] highScores;
          };
          // TODO create game class for deserialization
          serverCom(gson.toJson(payload), "get_all_games", "GET");

        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();
  }

//  public void hoodGen(final int hoodSize) {
//    Runnable task = new Runnable() {
//      @Override
//      public void run() {
//        try {
//          //TODO create payload
//          Object payload = new Object() {
//            @Expose
//            int size = hoodSize;
//          };
//          // TODO create game class for deserialization
//          serverCom(gson.toJson(payload), "get_neighborhood", "GET");
//
//        } catch (IOException ex) {
//          throw new RuntimeException(ex);
//        }
//      }
//
//    };
//    new Thread(task).start();
//  }



}
