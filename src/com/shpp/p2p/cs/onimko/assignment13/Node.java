package com.shpp.p2p.cs.onimko.assignment13;

import java.awt.*;

/**The node - pixel with links for pixels around*/
public class Node {
  private int x;
  private int y;
  private Color color;
  private Node upNode;
  private Node bottomNode;
  private Node leftNode;
  private Node rightNode;

  public Node(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  public Node getUpNode() {
    return upNode;
  }

  public void setUpNode(Node upNode) {
    this.upNode = upNode;
  }

  public Node getBottomNode() {
    return bottomNode;
  }

  public void setBottomNode(Node bottomNode) {
    this.bottomNode = bottomNode;
  }

  public Node getLeftNode() {
    return leftNode;
  }

  public void setLeftNode(Node leftNode) {
    this.leftNode = leftNode;
  }

  public Node getRightNode() {
    return rightNode;
  }

  public void setRightNode(Node rightNode) {
    this.rightNode = rightNode;
  }

  /**
   * Method returns a string representation of the object.
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "[x=" + x + ", y=" + y + ", color = {" + color.getRed() +", "
            + color.getGreen() + ", " + color.getBlue()
            + "}, up = "+ (!(upNode == null))
            + ", bottom = "+ (!(bottomNode == null))
            + ", left = "+ (!(leftNode == null))
            +  ", right = "+ (!(rightNode == null)) +"]";
  }
}
