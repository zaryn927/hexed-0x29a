package edu.cnm.deepdive.hexed0x29a.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zaryn on 7/26/2017.
 */
@DatabaseTable(tableName = "ARTIFACT")
public class Artifact {

  @DatabaseField(columnName = "ARTIFACT_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "X_LOC")
  private int x;

  @DatabaseField(columnName = "Y_LOC")
  private int y;

  @DatabaseField(columnName = "CHAR_ID", foreign = true)
  private Char character;

  @DatabaseField(columnName = "ARTIFACT_TYPE", canBeNull = false)
  private String artifactType;

//  @DatabaseField(columnName = "IN_USE")
//  private boolean inUse;


  public Artifact() {

  }

//  @Override
//  public String toString() {
//    return artifactType.toString();
//  }

  public int getId() {
    return id;
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

  public String getArtifactType() {
    return artifactType;
  }

  public void setArtifactType(String artifactType) {
    this.artifactType = artifactType;
  }
//
//  public boolean isInUse() {
//    return inUse;
//  }
//
//  public void setInUse(boolean inUse) {
//    this.inUse = inUse;
//  }

  public Char getCharacter() {
    return character;
  }

  public void setCharacter(Char character) {
    this.character = character;
  }
}
