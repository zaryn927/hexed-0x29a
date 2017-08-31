package edu.cnm.deepdive.hexed0x29a.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zaryn on 7/26/2017.
 */
@DatabaseTable(tableName = "CHAR")
public class Char {

  @DatabaseField(columnName = "CHAR_ID", id = true)
  private int id;

  @DatabaseField(columnName = "PLAYER_NAME", width = 100, unique = true)
  private String name;

  @DatabaseField(columnName = "X_LOC", canBeNull = false)
  private int x;

  @DatabaseField(columnName = "Y_LOC", canBeNull = false)
  private int y;

//  @DatabaseField(columnName = "NPC", canBeNull = false)
//  private boolean npc;
//
//  @DatabaseField(columnName = "HEALTH", canBeNull = false)
//  private int health;
//
//  @DatabaseField(columnName = "HEALTH_RECHARGE_RATE")
//  private int healthRechargeRate;
//
//  @DatabaseField(columnName = "STAMINA", canBeNull = false)
//  private int stamina;
//
//  @DatabaseField(columnName = "STAMINA_RECHARGE_RATE", canBeNull = false)
//  private double staminaRechargeRate;

//  @ForeignCollectionField(eager = false)
//  private ForeignCollection<Artifact> artifacts;

  public Char(){

  }

  @Override
  public String toString() {
    return x +", "+ y;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

//  public ForeignCollection<Artifact> getArtifacts() {
//    return artifacts;
//  }
//
//  public void setArtifacts(
//      ForeignCollection<Artifact> artifacts) {
//    this.artifacts = artifacts;
//  }
//  public boolean isNpc() {
//    return npc;
//  }
//
//  public void setNpc(boolean npc) {
//    this.npc = npc;
//  }
//
//  public int getHealth() {
//    return health;
//  }
//
//  public void setHealth(int health) {
//    this.health = health;
//  }
//
//  public int getHealthRechargeRate() {
//    return healthRechargeRate;
//  }
//
//  public void setHealthRechargeRate(int healthRechargeRate) {
//    this.healthRechargeRate = healthRechargeRate;
//  }
//
//  public int getStamina() {
//    return stamina;
//  }
//
//  public void setStamina(int stamina) {
//    this.stamina = stamina;
//  }
//
//  public double getStaminaRechargeRate() {
//    return staminaRechargeRate;
//  }
//
//  public void setStaminaRechargeRate(double staminaRechargeRate) {
//    this.staminaRechargeRate = staminaRechargeRate;
//  }
}
