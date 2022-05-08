import java.util.Random;

/**
 * ! Deprecate
 * Generates a random name to use for the Game Character
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
@Deprecated
public class ScreenName {
  
  private String[] names = {
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

  public ScreenName() { }

  /**
   * Randomly returns a name from the names list 
   * @return  a string name
   */
  public String getName() {
    Random r = new Random();
    int pick = r.nextInt(names.length - 1);
    return names[pick];
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
