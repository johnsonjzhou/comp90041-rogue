/**
 * Entities implementing this interface should be able to move within the world 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
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