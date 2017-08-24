package edu.cnm.deepdive.hexed0x29a_demo.interfaces_abstracts;

/**
 * Created by zaryn on 7/24/2017.
 */

public interface Game {
  Input getInput();
  FileIO getFileIO();
  Graphics getGraphics();
  Audio getAudio();
  void setScreen(Screen screen);
  Screen getCurrentScreen();
  Screen getStartScreen();
}
