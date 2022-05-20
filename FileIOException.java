/**
 * General exceptions encountered whilst working with the game world file 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
import java.io.IOException;

public class FileIOException extends IOException {

  public static final String GEN_MESSAGE = "An error occurred while loading the file.";

  public FileIOException() {
    super(FileIOException.GEN_MESSAGE);
  }

  public FileIOException(String message) {
    super(FileIOException.GEN_MESSAGE, new Throwable(message));
  }
}
