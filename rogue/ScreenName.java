import java.util.Random;

/**
 * Generates a random name to use for the Game Character
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
public class ScreenName {
  
  String[] names = {
    "Alisa", 
    "Alvaro", 
    "Anna", 
    "Bolivar", 
    "Clementine", 
    "Eamon", 
    "Freya", 
    "Harbin", 
    "Joanna", 
    "Marcel"
  };

  public ScreenName () { }

  public String getName() {
    return this.toString();
  }

  @Override
  public String toString() {
    Random r = new Random();
    int pick = r.nextInt(names.length - 1);
    return names[pick];
  }
}
