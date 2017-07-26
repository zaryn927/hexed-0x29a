package edu.cnm.deepdive.hexed0x29a.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zaryn on 7/26/2017.
 */
@DatabaseTable(tableName = "TERRAIN")
public class Terrain {

  @DatabaseField(generatedId = true, columnName = "DUMMY_ID")
  private long dummyId;

  @DatabaseField(uniqueCombo = true, columnName = "X_LOC", canBeNull = false)
  private int x;

  @DatabaseField(uniqueCombo = true, columnName = "Y_LOC", canBeNull = false)
  private int y;

  @DatabaseField(columnName = "TERRAIN_TYPE", canBeNull = false, foreign = true)
  private TerrainType terrainType;

  public Terrain(){

  }

  public long getDummyId() {
    return dummyId;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public TerrainType getTerrainType() {
    return terrainType;
  }

  public void setTerrainType(TerrainType terrainType) {
    this.terrainType = terrainType;
  }
}
