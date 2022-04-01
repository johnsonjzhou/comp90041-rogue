/**
 * Base attributes for a Game Character in Rogue.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
public class GameCharacter {
  
  // character state
  private String type = "Game Character";
  private String name = "Character";
  private int maxHealth = 0;
  private int currentHealth = 0;
  private int damage = 0;

  // character position
  private int posX = 0;
  private int posY = 0;

  public GameCharacter() { }

  /** setters */

  protected void setType(String type) {
    this.type = type;
  }

  /** 
   * Sets the name, ensuring the first character is a capital. 
   * If a name is not provided, a random name will be assigned. 
   * @param  name - the game character name as String
   */
  public void setName(String name) { 
    if (name.equals("")) {
      ScreenName generator = new ScreenName();
      name = generator.getName();
    }
    char firstChar = name.charAt(0);
    this.name = Character.toUpperCase(firstChar) + name.substring(1);
  }

  /**
   * Sets the maxHealth and currentHealth, 
   * this will effectively reset the currentHealth
   * @param  maxHealth - value for maxHealth
   */
  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
    this.currentHealth = maxHealth;
  }

  /**
   * Sets the damage value
   * @param  damage - the damage value as int
   */
  public void setDamage(int damage) {
    this.damage = damage;
  }

  /**
   * @param  x - x coordinate as int
   */
  public void setX(int x) {
    this.posX = x;
  }

  /**
   * @param  y - y coordinate as int
   */
  public void setY(int y) {
    this.posY = y;
  }

  /** getters */

  /**
   * @return  type as String
   */
  protected String getType() {
    return this.type;
  }

  /** 
   * @return  name as String
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return  maxHealth as int
   */
  public int getMaxHealth() {
    return this.maxHealth;
  }

  /**
   * @return  currentHealth as int
   */
  public int getCurrentHealth() {
    return this.currentHealth;
  }

  /**
   * @return  damage value as int
   */
  public int getDamage() {
    return this.damage;
  }

  /**
   * @return  the first letter of the name in upper case
   */
  public char getMapMarker() {
    char marker = this.name.charAt(0);
    return Character.toUpperCase(marker);
  }

  /**
   * Returns the character name and health stats 
   * by invoking the toString method
   * @return  Bilbo 18/20
   */
  public String getStats() {
    return this.toString();
  }

  /**
   * Returns whether the current health is above 0
   * @return  true for alive | false for dead
   */
  public boolean getAlive() {
    return (this.currentHealth > 0);
  }

  /**
   * @return  x coordinate as int
   */
  public int getX() {
    return this.posX;
  }

  /**
   * @return  y coordinate as int
   */
  public int getY() {
    return this.posY;
  }

  /** public */

  /**
   * Populates the monster attributes and prints a confirmation
   * @param  name - monster name as String
   * @param  maxHealth - maximum health as int
   * @param  damage - damage value as int
   */
  public void create(String name, int maxHealth, int damage) {
    this.setName(name);
    this.setMaxHealth(maxHealth);
    this.setDamage(damage);
    System.out.printf("%s '%s' created.%n", this.getType(), this.getName());
  }

  /** 
   * Receives a damage value and returns the value of currentHealth 
   * after the damage is dealt
   * @param  damage - damage value to apply to currentHealth
   * @return  value of currentHealth after damage is dealt
   */
  public int receiveDamage(int damage) {
    this.currentHealth -= damage;
    return this.currentHealth;
  }

  /**
   * Attacks the foe by dealing it damage and 
   * optionally output the attack to the screen
   * @param  foe - another game character as the foe
   * @param  output - whether to output the attack to the screen
   * @return  true if killed the foe || false if foe lives
   */
  public boolean attacks(GameCharacter foe, boolean output) {
    int foeHealth = foe.receiveDamage(this.getDamage());
    if (output) {
      System.out.printf("%s attacks %s for %d damage.%n",
        this.getName(), foe.getName(), this.getDamage()
      );
    }
    return (foeHealth <= 0);
  }

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

  /** overrides */

  @Override
  public String toString() {
    return String.format(
      "%s %d/%d",
      this.name, this.currentHealth, this.maxHealth
    );
  }
}
