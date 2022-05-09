/**
 * Attributes of the Player, which is a GameCharacter.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
import java.lang.NumberFormatException;

public class Player extends GameCharacter {

  // player attributes
  private int level = 1;
  private boolean ready = false;

  public Player() {
    // init GameCharacter
    super();

    // init Player
    this.setType("Player");
  }

  /** setters */

  /**
   * @param  level  player level as int
   */
  public void setLevel(int level) {
    this.level = level;
    this.resetAttributes();
  }

  /**
   * Sets the player as being ready to play 
   */
  public void setReady() {
    this.ready = true;
  }

  /** getters */

  /**
   * @return  player level as int
   */
  public int getLevel() {
    return this.level;
  }

  /**
   * @return  <code>True</code> if player is ready to play 
   */
  public boolean getReady() {
    return this.ready;
  }

  /** public */

  /**
   * Loads Player attribute information from a string of text
   * @param  loadString  matching format <code>name level</code>
   * @throws  IOExceptions  if loadString does not contain valid data 
   */
  public void load(String loadString) throws IOExceptions {
    String[] loaded = loadString.split(" ");

    // validate string elements
    if (loaded.length != 2) {
      throw new IOExceptions("Unexpected length of player load string");
    }

    // parse and set player level 
    try {
      int level = Integer.parseInt(loaded[1]);
      this.setLevel(level);
    } catch (NumberFormatException e) {
      throw new IOExceptions("Could not parse player level");
    }

    // set the name and apply calculated attributes 
    this.create(loaded[0], this.calculateMaxHealth(), this.calculateDamage(), true);

    System.out.println("Player data loaded.");
  }

  /**
   * Loads Player map information from a string of text 
   * @param  loadString  matching format <code>player x y</code> 
   * @param  map  the world map 
   * @throws  IOExceptions  if loadString does not contain valid data 
   */
  public void load(String loadString, Map map) throws IOExceptions {
    String[] loaded = loadString.split(" ");

    // validate string identifier 
    if (!loaded[0].equals("player")) {
      throw new IOExceptions("'player' identifier expected in loadString");
    }

    // parse player coordinates 
    int x = -1;
    int y = -1;
    try {
      x = Integer.parseInt(loaded[1]);
      y = Integer.parseInt(loaded[2]);
    } catch (NumberFormatException e) {
      throw new IOExceptions("Could not parse player coordinates");
    }

    // set player coordinates 
    if (!map.traversable(x, y) || x < 0 || y < 0) {
      throw new IOExceptions("Non-traversable player coordinates");
    }

    this.setX(x);
    this.setY(y);
  }

  /**
   * Overloaded create method that takes only the name 
   * and calculates the max health and damage for the player
   * @param  name  player name as String
   */
  public void create(String name) {
    int maxHealth = this.calculateMaxHealth();
    int damage = this.calculateDamage();
    this.create(name, maxHealth, damage);
  }

  /** private */

  /**
   * Calculates and returns the maxHealth of the Player.
   * @return  int - calculated maxHealth of the player
   */
  private int calculateMaxHealth() {
    return (17 + 3 * this.level);
  }

  /**
   * Calculates and returns the damage of the Player.
   * @return  int - calculated damage of the player
   */
  private int calculateDamage() {
    return (1 + this.level);
  }

  /** public */

  /**
   * Resets the player maxHealth, currentHealth and damage
   */
  public void resetAttributes() {
    this.setMaxHealth(this.calculateMaxHealth());
    this.setDamage(this.calculateDamage());
  }

  /** Entity */

  /**
   * @return  the first letter of the name in upper case
   */
  public char getMapMarker() {
    char marker = this.getName().charAt(0);
    return Character.toUpperCase(marker);
  }

  /** GameCharacter */

  /**
   * Prints the player stats to the system in the following format
   * Name (Lv. 1)
   * Damage: 2
   * Health: 20/20
   */
  public void displayCharacterInfo() {
    System.out.printf("%s (Lv. %d)%n", this.getName(), this.level);
    System.out.printf("Damage: %d%n", this.getDamage());
    System.out.printf("Health: %d/%d%n", 
      this.getCurrentHealth(), this.getMaxHealth()
    );
  }
}
