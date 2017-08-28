package edu.cnm.deepdive.hexed0x29a.rest_client;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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

  public static void newGame(final int genGame) { //changed from hoodSize
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          Object payload = new Object() {

            @Expose
            private
            int size = genGame;

            public void setSize(int size) {
              this.size = size;
            }

            public int getSize() {
              return size;
            }
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

  public static void gameUpdate(final int update) { //added getters and setters
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

            public void setFinished(boolean finished) {
              this.finished = finished;
            }

            public boolean isFinished() {
              return finished;
            }

            public void setPlayerName(String playerName) {
              this.playerName = playerName;
            }

            public String getPlayerName() {
              return playerName;
            }

            public void setY(int y) {
              this.y = y;
            }

            public int getY() {
              return y;
            }

            public void setX(int x) {
              this.x = x;
            }

            public int getX() {
              return x;
            }

            public void setScore(int score) {
              this.score = score;
            }

            public int getScore() {
              return score;
            }

          };
          // TODO create game class for deserialization
          serverCom(gson.toJson(payload), "put_game", "PUT");


        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }

    };
    new Thread(task).start();
  }

  public static void artCollected(boolean collected) {
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

  public static void hoodGen(final int hoodSize) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          //TODO create payload
          Object payload = new Object() {
            @Expose
            int size = hoodSize;
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
