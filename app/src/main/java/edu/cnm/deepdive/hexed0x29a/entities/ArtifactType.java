package edu.cnm.deepdive.hexed0x29a.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zaryn on 7/26/2017.
 */
@DatabaseTable(tableName = "ARTIFACT_TYPE")
public class ArtifactType {

  @DatabaseField(columnName = "ART_TYPE_ID", generatedId = true)
  private int artTypeId;

  @DatabaseField(columnName = "ARTIFACT_NAME", width = 100, unique = true)
  private String artifactName;

  @DatabaseField(columnName = "COLLECTIBLE")
  private boolean collectible;

  @DatabaseField(columnName = "CONSUMABLE")
  private boolean consumable;

  @DatabaseField(columnName = "BURN_RATE")
  private double burnRate;

  @DatabaseField(columnName = "DROPPABLE")
  private boolean droppable;

  public ArtifactType() {

  }

  @Override
  public String toString() {
    return artifactName;
  }

  public int getArtTypeId() {
    return artTypeId;
  }

  public String getArtifactName() {
    return artifactName;
  }

  public void setArtifactName(String artifactName) {
    this.artifactName = artifactName;
  }

  public boolean isCollectible() {
    return collectible;
  }

  public void setCollectible(boolean collectible) {
    this.collectible = collectible;
  }

  public boolean isConsumable() {
    return consumable;
  }

  public void setConsumable(boolean consumable) {
    this.consumable = consumable;
  }

  public double getBurnRate() {
    return burnRate;
  }

  public void setBurnRate(double burnRate) {
    this.burnRate = burnRate;
  }

  public boolean isDroppable() {
    return droppable;
  }

  public void setDroppable(boolean droppable) {
    this.droppable = droppable;
  }
}
