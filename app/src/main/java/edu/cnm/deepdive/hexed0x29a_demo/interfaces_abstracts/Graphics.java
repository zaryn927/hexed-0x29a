package edu.cnm.deepdive.hexed0x29a_demo.interfaces_abstracts;

/**
 * Created by zaryn on 7/24/2017.
 */

public interface Graphics {
  enum PixmapFormat{
    ARGB8888,
    ARGB4444,
    RGB565
  }

  interface Pixmap {

    int getWidth();
    int getHeight();
    PixmapFormat getFormat();
    void dispose();
  }

  Pixmap pixmap(String fileName, PixmapFormat format);
  void clear(int color);
  void drawPixel(int x, int y, int color);
  void drawLine(int x, int y, int x2, int y2, int color); //Probably won't need to draw lines or rectangles
  void drawRect(int x, int y, int width, int height, int color);
  void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
                  int srcWidth, int srcHeight);
  void drawPixmap(Pixmap pixmap, int x, int y);
  int getWidth();
  int getHeight();
}
