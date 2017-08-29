package edu.cnm.deepdive.hexed0x29a.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zaryn on 7/26/2017.
 */

@DatabaseTable(tableName = "TERRAIN_TYPE")
public class TerrainType {

  @DatabaseField(columnName = "TERRAIN_TYPE_ID", id = true)
  private int id;

  @DatabaseField(columnName = "TERRAIN_NAME", width = 100)
  private String terrainName;

  @DatabaseField(columnName = "PASSABLE")
  private boolean passable;

  public TerrainType() {

  }

  @Override
  public String toString(){
    return terrainName;
  }

  public int getId() {
    return id;
  }

  public String getTerrainName() {
    return terrainName;
  }

  public void setTerrainName(String terrainName) {
    this.terrainName = terrainName;
  }

  public boolean isPassable() {
    return passable;
  }

  public void setPassable(boolean passable) {
    this.passable = passable;
  }
}
