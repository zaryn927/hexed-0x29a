package edu.cnm.deepdive.hexed0x29a.interfaces_abstracts;

/**
 * Created by zaryn on 7/24/2017.
 */

public interface Audio {

  Music music(String fileName);
  Sound sound(String fileName);

  interface Music {
    void play();
    void stop();
    void pause();
    void setLooping(boolean looping);
    void setVolume(float volume);
    boolean isPlaying();
    boolean isStopped();
    boolean isLooping();
    void dispose();
  }

  interface Sound {
    void play(float volume);
    void dispose();
  }
}
