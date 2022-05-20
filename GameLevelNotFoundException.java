/**
 * Exception relating to loading of the game level 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
import java.io.IOException;

public class GameLevelNotFoundException extends IOException {

  public static final String GEN_MESSAGE = "Map not found.";

  public GameLevelNotFoundException() {
    super(GameLevelNotFoundException.GEN_MESSAGE);
  }

  public GameLevelNotFoundException(String message) {
    super(GameLevelNotFoundException.GEN_MESSAGE, new Throwable(message));
  }
}
