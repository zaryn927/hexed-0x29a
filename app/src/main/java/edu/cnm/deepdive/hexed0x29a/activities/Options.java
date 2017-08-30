package edu.cnm.deepdive.hexed0x29a.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.cnm.deepdive.hexed0x29a.R;
import edu.cnm.deepdive.hexed0x29a.rest_client.Game;
import edu.cnm.deepdive.hexed0x29a.rest_client.GameTraffic;

public class Options extends AppCompatActivity {

  private Game[] games = null;

  public synchronized Game[] getGames() {
    return games;
  }

  public synchronized void setGames(Game[] games) {
    this.games = games;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_options);
    requestData();
  }

  private void requestData() {
    GameTraffic traffic = GameTraffic.getInstance(null);
    traffic.HScores(this);
//     test code to be removed later
//    games = new Game[5];
//    for (int i = 0; i < games.length; i++) {
//      Game game = new Game();
//      game.player = "test" + i;
//      game.score = i * i;
//      games[i] = game;
//    }
    //end of test code
    while (getGames() == null) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        // Do nothing.
      }
    }
    ArrayAdapter<Game> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, getGames());
    ((ListView) findViewById(R.id.high_scores)).setAdapter(adapter);
  }

}