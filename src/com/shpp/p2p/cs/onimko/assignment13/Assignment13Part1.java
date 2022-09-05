package com.shpp.p2p.cs.onimko.assignment13;

/**
 * Task: the program should output to the console the number of silhouettes of large objects in the picture.
 * The file name is the first parameter at the input of the program. For example: 'test.jpg'.
 * If there are no parameters, the first parameter is assumed to be 'test.jpg'
 */
public class Assignment13Part1 implements Const{

  /**
   * The start method
   * @param args the input array of string.
   */
  public static void main(String[] args) {
    SearchEngine engine = new SearchEngine();
    System.out.println("Number of objects: " + engine.findSilhouettes(getFileName(args)));
  }

  /**
   * Method gets fileName with an array - args.
   * If args is empty - returns "test.jpg".
   * @param args the input string array
   * @return the file's name.
   */
  private static String getFileName(String[] args) {
    String fileName;
    if (args.length == 0)  fileName = "test.jpg";
    else fileName = args[0].replaceAll(" ","");
    return fileName;
  }
}