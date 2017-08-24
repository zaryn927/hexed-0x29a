package edu.cnm.deepdive.hexed0x29a.helpers;

import java.util.Random;

/**
 * Created by zaryn on 8/21/2017.
 */

public class PerlinNoise {

  private int scale;
  private int size;
  private Cell[][] cells;

  public PerlinNoise(int size) {
    this.size = size;
    cells = new Cell[size][size];
    for (int x = 0; x < size; x++){
      int xOffset = 2 * x + 1;
      int xCompOffset = 2 * size - xOffset;
      for (int y = 0; y < size; y ++){
        int yOffset = 2 * y + 1;
        int yCompOffset = 2 * size - yOffset;
        Cell cell = new Cell();
        int sum = 0;
        cell.vectors[0][0] = xOffset * xCompOffset * yCompOffset;
        cell.vectors[0][1] = yOffset * xCompOffset * yCompOffset;
        cell.vectors[1][0] = xOffset * xCompOffset * yOffset;
        cell.vectors[1][1] = -yCompOffset * xCompOffset * yOffset;
        cell.vectors[2][0] = -xCompOffset * xOffset * yOffset;
        cell.vectors[2][1] = -yCompOffset * xOffset * yOffset;
        cell.vectors[3][0] = -xCompOffset * xOffset * yCompOffset;
        cell.vectors[3][1] = yOffset * xOffset * yCompOffset;
        for(int[] vector: cell.vectors){
          for(int component : vector) {
            sum += Math.abs(component);
          }
        }
        cells[y][x] = cell;
        scale = Math.max(scale, sum);
      }
    }
  }

  public double[][] apply(int[][] gradients) {
    double[][] values = new double[size][size];
    for (int i = 0; i < cells.length; i++){
      for(int j = 0; j < cells[i].length; j++) {
        values[i][j] = apply(gradients, j, i);
      }
    }
    return values;
  }

  public double apply(int[][] gradients, int x, int y) {
    Cell cell = cells[y][x];
    double value = 0;
    for (int i = 0; i < cell.vectors.length; i ++) {
      value += cell.vectors[i][0] * gradients[i][0] + cell.vectors[i][1] * gradients[i][1];
    }
    return  value / (double) scale;
  }
  private class Cell {
    private int[][] vectors = new int[4][2];
  }

  public static void main(String[] args) {
    Random rng = new Random();
    int[][] gradients = new int[4][];
    for (int i = 0; i < gradients.length; i++){
      int selector = rng.nextInt(4);
      gradients[i] = new int[]{
          (selector / 2 == 0)? -1 : 1,
          (selector % 2 == 0)? -1 : 1
      };
    }
  }

//  private static Random rng = new Random();
//
//  public static double generateNoise(int freq, double x, double y){
//    x = x / freq;
//    y = y / freq;
//    double dX = x - Math.floor(x);
//    double dY = y - Math.floor(y);
//
//    double dot1 = dotProduct(dX, dY);
//    double dot2 = dotProduct(dX - 1, dY);
//    double dot3 = dotProduct(dX, dY - 1);
//    double dot4 = dotProduct(dX - 1, dY - 1);
//
//    double u = fade(dX);
//    double v = fade(dY);
//
//    double xInterpTop = lerp(dot1, dot2, u);
//    double xInterpBottom = lerp(dot3, dot4, u);
//    double yInterp = lerp(xInterpTop, xInterpBottom, v);
//
//    return yInterp;
//  }
//
//  private static double dotProduct(double dX, double dY){
//    int randomGradVect = rng.nextInt(4);
//    switch (randomGradVect){
//      case 0:
//        return dX + dY;
//      case 1:
//        return dX - dY;
//      case 2:
//        return -dX + dY;
//      case 3:
//        return -dX - dY;
//      default:
//        return 0;
//    }
//  }
//
//  private static double fade(double t){
//    return t * t * t * (t * (t * 6 - 15) + 10);
//  }
//
//  private static double lerp(double v0, double v1, double fade){
//    return ((1 - fade) * v0 + fade * v1);
//  }
}
