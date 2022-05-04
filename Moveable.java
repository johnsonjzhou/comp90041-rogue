interface Moveable {

  /**
   * Gets the current X position 
   */
  public int getX(); 

  /**
   * Gets the current Y position 
   */
  public int getY();

  /**
   * Initiate a movement command 
   */
  public void move(int newX, int newY); 
}