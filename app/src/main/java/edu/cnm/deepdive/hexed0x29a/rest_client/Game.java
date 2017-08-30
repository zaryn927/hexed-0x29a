package edu.cnm.deepdive.hexed0x29a.rest_client;

import com.google.gson.annotations.Expose;
import edu.cnm.deepdive.hexed0x29a.entities.Artifact;

/**
 * Created by kelly on 8/28/17.
 */

public class Game {
  
  @Expose
  public Integer id;
  
  @Expose
  public Integer size;
  
  @Expose
  public Integer x;
  
  @Expose
  public Integer y;
  
  @Expose
  public Integer score;
  
  @Expose
  public Boolean finished;
  
  @Expose
  public String player;
  
  @Expose
  public Neighborhood neighborhood;

  @Expose
  public Game.Artifact[] artifacts;

  @Override
  public String toString() {
    return ((player != null) ? player : "(none)") + " - " + ((score != null) ? score : 0);
  }
  public static class Artifact{
    @Expose
    public String type;
    @Expose
    public Boolean collected;
  }
  public static class Neighborhood{
    
    @Expose
    public Integer left;
    
    @Expose
    public Integer top;
    
    @Expose
    public Integer width;
    
    @Expose
    public Integer height;
    
    @Expose
    public Integer id;
    
    @Expose
    public Tile[][] tiles;
    
    public static class Tile{
      @Expose
      public Integer x;
      @Expose
      public Integer y;
      @Expose
      public Double elevation;
      @Expose
      public Game.Artifact artifact;
    }
  }


}
