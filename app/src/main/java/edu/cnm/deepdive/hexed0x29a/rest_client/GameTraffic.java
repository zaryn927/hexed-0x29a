package edu.cnm.deepdive.hexed0x29a.rest_client;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by kelly on 8/24/17.
 */

public class GameTraffic {

  public static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  private static Context context;

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

  public static void newGame(final int hoodSize) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          Object payload = new Object() {
            @Expose
            int size = hoodSize;
          };
          // TODO create game class for deserialization
        serverCom(gson.toJson(payload), "base_url", "POST");

        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();
  }

  public static void gameIdentity(final int gameId) {
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
  public static void HScores(final int highScores) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          //TODO create payload
          Object payload = new Object() {
            @Expose
            int highScores =  new Array []hScores;
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

  public static void hoodGen(final int gameSize) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          //TODO create payload
          Object payload = new Object() {
            @Expose
            int size = gameSize;
          };
          // TODO create game class for deserialization
          serverCom(gson.toJson(payload), "get_neighborhood", "GET");

        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();
  }

}
