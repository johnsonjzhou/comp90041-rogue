/**
 * Exception relating to FileIO operations
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
public class FileIOException extends Exception {
  
  public FileIOException() {
    super("Error with FileIO operation");
  }

  public FileIOException(String message) {
    super(message);
  }
}
