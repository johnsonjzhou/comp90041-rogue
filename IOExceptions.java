/**
 * General exceptions encountered whilst working with the game world file 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
public class IOExceptions extends Exception {

  public static final String GEN_MESSAGE = "An error occurred while loading the file.";

  public IOExceptions() {
    super(IOExceptions.GEN_MESSAGE);
  }

  public IOExceptions(String message) {
    super(IOExceptions.GEN_MESSAGE, new Throwable(message));
  }
}
