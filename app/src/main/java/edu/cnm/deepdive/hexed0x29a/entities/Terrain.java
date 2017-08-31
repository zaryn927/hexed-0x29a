package edu.cnm.deepdive.hexed0x29a.entities;

import android.graphics.Bitmap;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import edu.cnm.deepdive.hexed0x29a.rest_client.Game;

/**
 * Created by zaryn on 7/26/2017.
 */
@DatabaseTable(tableName = "TERRAIN")
public class Terrain {

  private Bitmap tile = null;

  @DatabaseField(columnName = "DUMMY_ID", generatedId = true)
  private long dummyId;

  @DatabaseField(uniqueCombo = true, columnName = "X_LOC", canBeNull = false)
  private int x;

  @DatabaseField(uniqueCombo = true, columnName = "Y_LOC", canBeNull = false)
  private int y;

  @DatabaseField(columnName = "ELEVATION", canBeNull = false)
  private double elevation;

  @DatabaseField(columnName = "COLLISION", canBeNull = false)
  private boolean blocked;

  @DatabaseField(columnName = "OBSCURE", canBeNull = false)
  private boolean obscured;

  @DatabaseField(columnName = "ARTIFACT_ID", foreign = true)
  private Artifact artifact;
//  @DatabaseField(columnName = "TERRAIN_TYPE", canBeNull = false, foreign = true)
//  private TerrainType terrainType;

  public Terrain(){

  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(x + ", ");
    sb.append(y + ", ");
    sb.append(blocked + ", ");
    sb.append(obscured);
    return sb.toString();
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

  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public boolean isObscured() {
    return obscured;
  }

  public void setObscured(boolean obscured) {
    this.obscured = obscured;
  }

  public Artifact getArtifact() {
    return artifact;
  }

  public void setArtifact(Artifact artifact) {
    this.artifact = artifact;
  }

  public double getElevation() {
    return elevation;
  }

  public void setElevation(double elevation) {
    this.elevation = elevation;
  }

  public Bitmap getTile() {
    return tile;
  }

  public void setTile(Bitmap tile) {
    this.tile = tile;
  }
//  public TerrainType getTerrainType() {
//    return terrainType;
//  }
//
//  public void setTerrainType(TerrainType terrainType) {
//    this.terrainType = terrainType;
//  }
}
