/**
 * Handles user inputs
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
import java.util.Scanner;
import java.lang.NumberFormatException;
import java.lang.NullPointerException;
import java.util.ArrayList;

public class UserConsole {

  private Scanner stdin;
  private ArrayList<String> inputBuffer; 

  public UserConsole() {
    this.stdin = new Scanner(System.in);
    this.inputBuffer = new ArrayList<String>();
  }

  /** getter */

  /**
   * Exposes the scanner used in the class, not recommneded
   */
  public Scanner getScanner() {
    return this.stdin;
  }

  /** private */

  private void printMessage(String message) {
    System.out.printf("(%s)", message);
  }

  private void printInputError(String message) {
    this.printMessage(message);
  }

  /** public */

  /**
   * Creates a new line and prints a user prompt indicator.
   * \n
   * >\s
   */
  public void printPrompt() {
    System.out.println();
    System.out.print("> ");
  }

  /**
   * Reads the input token with exception handling.
   * If an exception occurs, a blank String will be returned.
   * @return  user input as String
   */
  public String readNext() {
    try {
      String input = this.stdin.next();
      this.stdin.nextLine();
      return input;
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * Reads the next input and converts it into an integer. 
   * If the input is not an integer, an error prompt will be displayed 
   * for the user to try again. 
   * The user can break the input loop by typing "exit"
   * @param  errorMessage - optional message to display for invalid input
   * @return  user input as int
   */
  public int readInt(String errorMessage) {
    String input = this.readNext();
    int output = 0;
    convert : while (true) {
      try {
        output = Integer.parseInt(input);
        break convert;
      } catch (NumberFormatException | NullPointerException e) {
        this.printInputError(errorMessage);
        this.printPrompt();
        input = this.readNext();
        if (input.equals("exit")) {
          break convert;
        }
        continue convert;
      }
    }
    return output;
  }

  /**
   * Reads the next input as an integer 
   */
  public int readInt() {
    return this.readInt(GameEngine.INVALID_INPUT_MSG);
  }

  /**
   * Creates a read buffer by reading the entire input line 
   * and splitting at the spaces. 
   * This then enables to step through each of the input items iteratively. 
   * @return  the next string from the buffer 
   */
  public String readBufferedNext() {
    // return and remove the first element in the inputBuffer
    if (this.inputBuffer.size() > 0) {
      return this.inputBuffer.remove(0);
    }

    // receive from the console and add to buffer
    try {
      String input = this.stdin.nextLine();
      for (String next : input.split(" ")) {
        this.inputBuffer.add(next);
      }
      return this.readBufferedNext();
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * @return  <code>True</code> if scanner has another token 
   */
  public boolean hasNext() {
    return this.stdin.hasNext();
  }

  /**
   * Checks whether there is another item in the buffer 
   */
  public boolean hasBufferedNext() {
    return (this.inputBuffer.size() > 0);
  }

  /**
   * Clear the buffer 
   */
  public void clearBuffer() {
    this.inputBuffer.clear();
  }

  /**
   * Wait for the user to press enter 
   */
  public boolean waitUserEnter() {
    try {
      System.out.println();
      this.printMessage(GameEngine.WAIT_ENTER_KEY_MSG);
      this.stdin.nextLine();
      System.out.println();
    } catch (Exception e) { }
    return false;
  }

  /**
   * Close the input 
   */
  public void close() {
    this.stdin.close();
  }
}
