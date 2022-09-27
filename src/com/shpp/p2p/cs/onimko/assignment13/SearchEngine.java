package com.shpp.p2p.cs.onimko.assignment13;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

/**
 * Class for search objects in file of image.
 */
public class SearchEngine implements Const {

  /**Array for subjects in the image with background*/
  private final ArrayList<HashSet<Node>> silhouettesAndBackground = new ArrayList<>();
  /**Array for nodes of borders for get objects color*/
  private final ArrayList<Node> borders = new ArrayList<>();

  /**
   * Method looks for objects in the image.
   * @param image the input name of image-file
   * @return the number of objects
   */
  public int findSilhouettes(String image) {
    HashSet<Node> nodesFind;
    ArrayList<Node> allNodes = getNodes(image);
    separateStickySilhouettes(allNodes);
    while (!allNodes.isEmpty()) {
      nodesFind = new HashSet<>();
      bfs(allNodes.get(0), nodesFind);
      silhouettesAndBackground.add(nodesFind);
      allNodes.removeAll(nodesFind);
    }
    return countSilhouettes();
  }

  /**
   * Method finds a region for similar colors of pixels.
   * @param node node on the pictures.
   * @param ns the collection for saving pixels.
   */
  private void bfs(Node node, HashSet<Node> ns ) {
    ArrayDeque<Node> queue = new ArrayDeque<>();
    queue.addLast(node);
      while (!queue.isEmpty()){
        node = queue.pollFirst();
        if (!ns.contains(node)) {
          if (node.getRightNode() != null && ColorComparison.isSimilarColor(node.getColor(), node.getRightNode().getColor()))
            queue.addLast(node.getRightNode());
          if (node.getLeftNode() != null && ColorComparison.isSimilarColor(node.getColor(), node.getLeftNode().getColor()))
            queue.addLast(node.getLeftNode());
          if (node.getBottomNode() != null && ColorComparison.isSimilarColor(node.getColor(), node.getBottomNode().getColor()))
            queue.addLast(node.getBottomNode());
          if (node.getUpNode() != null && ColorComparison.isSimilarColor(node.getColor(), node.getUpNode().getColor()))
            queue.addLast(node.getUpNode());
        }
        ns.add(node);
      }
  }

    /**
     * Method gets an array of nodes with a file.
     * @param fileName input file's name.
     * @return the ArrayList of Nodes.
     */
  private ArrayList<Node> getNodes(String fileName) {
    Node[][] nodes = null;
    try {
      BufferedImage image = ImageIO.read(new File(fileName));
      nodes = new Node[image.getWidth() / STEP][image.getHeight() / STEP];
      for (int x = 0; x < nodes.length; x++) {
        for (int y = 0; y < nodes[0].length; y++) {
          nodes[x][y] = new Node(x, y, new Color(image.getRGB(x * STEP, y * STEP), true));
          if (x > 0 && x < nodes.length - 1) {
            nodes[x][y].setLeftNode(nodes[x - 1][y]);
            nodes[x - 1][y].setRightNode(nodes[x][y]);
          }
          if (y > 0 && y < nodes.length - 1) {
            nodes[x][y].setUpNode(nodes[x][y - 1]);
            nodes[x][y - 1].setBottomNode(nodes[x][y]);
          }
          if (x < BORDER || x > image.getWidth() / STEP - BORDER
              || y < BORDER || y > image.getHeight() / STEP - BORDER)
            borders.add(nodes[x][y]);
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    return (ArrayList<Node>) Arrays.stream(nodes).flatMap(Arrays::stream).collect(Collectors.toList());
  }

  /**
   * Method counts number of silhouettes in the array of silhouettes.
   * @return number of objects.
   */
  private int countSilhouettes(){
    return (int) silhouettesAndBackground.stream()
            .filter(a -> a.size() > SIZE_OBJECT
                    && !ColorComparison.isSimilarColor(a.stream().findFirst().get().getColor()
                    ,getBackgroundColor())).count();
  }

  /**
   * Method returns the color of background of the image.
   * @return the color of objects.
   */
  private Color getBackgroundColor() {
    int numberOfAlpha = (int) borders.stream()
            .filter(n -> n.getColor().getAlpha() < ALPHA)
            .count();
    if (numberOfAlpha > borders.size()/2) return new Color(0,0,0,0);
    int numberOfWhite = (int) borders.stream()
            .filter(f -> ColorComparison.isSimilarColor(f.getColor(),Color.WHITE))
            .count();
    return borders.size() / 2 < numberOfWhite ? Color.WHITE : Color.BLACK;
  }

  private void separateStickySilhouettes(ArrayList<Node> nodes) {
    for (Node node:nodes){
      if (node.getUpNode() != null && !ColorComparison.isSimilarColor(node.getColor(),node.getUpNode().getColor()))
        needSeparate(node,true);

    }
  }

  private void needSeparate(Node node, boolean isDown) {
    Node current = node;
    for (int i = 0; i < SEPARATE_NUMBER; i++) {
      if(isDown) {
        if (current.getBottomNode() != null) current = current.getBottomNode();
      } else
      if (current.getLeftNode() != null) current = current.getLeftNode();
    }
    if (ColorComparison.isSimilarColor(node.getColor(), current.getColor())) return;
    for (int i = 0; i < SEPARATE_NUMBER; i++) {
      node.setColor(getBackgroundColor());
      node = node.getUpNode();
    }
  }
}