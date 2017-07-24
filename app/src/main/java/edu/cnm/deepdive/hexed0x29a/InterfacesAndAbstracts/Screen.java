package edu.cnm.deepdive.hexed0x29a.InterfacesAndAbstracts;

/**
 * Created by zaryn on 7/24/2017.
 */

public abstract class Screen {
  protected final Game game;

  public Screen(Game game) {
    this.game = game;
  }

  public abstract void update(float deltaTime);
  public abstract void present(float deltaTime);
  public abstract void pause();
  public abstract void resume();
  public abstract void dispose();
}
