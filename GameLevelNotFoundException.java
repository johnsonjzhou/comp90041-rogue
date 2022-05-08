/**
 * Exception relating to loading of the game level 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
public class GameLevelNotFoundException extends Exception {
  public GameLevelNotFoundException() {
    super("Map not found.");
  }
}
