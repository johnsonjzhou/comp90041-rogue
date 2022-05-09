/**
 * Attributes of the Monster, which is a GameCharacter.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class Monster extends GameCharacter {

  public Monster() {
    // init GameCharacter
    super();

    // init Monster
    this.setType("Monster");
  }

  /**
   * Short cut constructor at also invokes the <code>load</code> method. 
   * @param  loadString  matching format 
   *                     <code>monster x y monsterName health attack</code> 
   * @param  map  the world map 
   * @throws  IOExceptions  if loadString does not contain valid data 
   */
  public Monster(String loadString, Map map) throws IOExceptions {
    // init GameCharacter
    super();

    // init Monster
    this.setType("Monster");

    this.load(loadString, map);
  }

  /**
   * todo Loads Monster information from a string of text 
   * @param  loadString  matching format 
   *                     <code>monster x y monsterName health attack</code> 
   * @param  map  the world map 
   * @throws  IOExceptions  if loadString does not contain valid data 
   */
  public void load(String loadString, Map map) throws IOExceptions {
    String[] loaded = loadString.split(" ");

    // validate load string length 
    if (loaded.length != 6) {
      throw new IOExceptions("Unexpected length of monster load string");
    }

    // validate string identifier 
    if (!loaded[0].equals("monster")) {
      throw new IOExceptions("'monster' identifier expected in loadString");
    }

    // parse monster coordinates 
    int x = -1;
    int y = -1;
    try {
      x = Integer.parseInt(loaded[1]);
      y = Integer.parseInt(loaded[2]);
    } catch (NumberFormatException e) {
      throw new IOExceptions("Could not parse monster coordinates");
    }

    // set monster coordinates 
    if (!map.traversable(x, y) || x < 0 || y < 0) {
      throw new IOExceptions("Non-traversable monster coordinates");
    }

    this.setX(x);
    this.setY(y);

    // parse and set monster name and attributes 
    try {
      String name = loaded[3];
      int maxHealth = Integer.parseInt(loaded[4]);
      int damage = Integer.parseInt(loaded[5]);
      this.create(name, maxHealth, damage, true);
    } catch (NumberFormatException e) {
      throw new IOExceptions("Could not parse monster attributes");
    }
  }

  /** Entity */

  /**
   * @return  the first letter of the name in lower case
   */
  public char getMapMarker() {
    char marker = this.getName().charAt(0);
    return Character.toLowerCase(marker);
  }

  /** GameCharacter */

  /**
   * Prints the player stats to the system in the following format
   * Name
   * Damage: 2
   * Health: 20/20
   */
  public void displayCharacterInfo() {
    System.out.printf("%s%n", this.getName());
    System.out.printf("Damage: %d%n", this.getDamage());
    System.out.printf("Health: %d/%d%n", 
      this.getCurrentHealth(), this.getMaxHealth()
    );
  }

}
