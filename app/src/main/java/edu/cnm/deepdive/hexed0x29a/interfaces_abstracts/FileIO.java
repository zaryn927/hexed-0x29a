package edu.cnm.deepdive.hexed0x29a.interfaces_abstracts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zaryn on 7/24/2017.
 */

public interface FileIO {
  InputStream readAsset(String fileName) throws IOException;
  InputStream readFile(String fileName) throws IOException;
  OutputStream writeFile(String fileName) throws IOException;
}
