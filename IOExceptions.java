/**
 * General exceptions encountered whilst working with the game world file 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
public class IOExceptions extends Exception {
  public IOExceptions() {
    super("An error occurred while loading the file.");
  }
}