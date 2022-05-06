/**
 * Base attributes for a Game Character in Rogue.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
public abstract class GameCharacter extends Entity 
  implements Moveable, Militant<GameCharacter> {
  
  // character state
  private String type = "Game Character";
  private String name = "Character";
  private int maxHealth = 0;
  private int currentHealth = 0;
  private int damage = 0;

  public GameCharacter() {
    super();
  }

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

  /** Militant */

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
   * Restores the currentHealth to the maxHealth value
   */
  public void restoreHealth() {
    this.currentHealth = this.maxHealth;
  }

  /**
   * Sets the damage value
   * @param  damage - the damage value as int
   */
  public void setDamage(int damage) {
    this.damage = damage;
  }

  /**
   * @return  damage value as int
   */
  public int getDamage() {
    return this.damage;
  }

  /**
   * Attacks the foe by dealing it damage and 
   * optionally output the attack to the screen
   * @param  foe - another game character as the foe
   * @param  output - whether to output the attack to the screen
   * @return  true if killed the foe || false if foe lives
   */
  @Override
  public boolean attacks(GameCharacter foe) {
    int foeHealth = foe.receiveDamage(this.getDamage());
    System.out.printf("%s attacks %s for %d damage.%n",
      this.getName(), foe.getName(), this.getDamage()
    );
    return (foeHealth <= 0);
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

  /** Moveable */

  @Override
  public void move(int newX, int newY) {
    this.setX(newX);
    this.setY(newY);
  };

  /** abstract */ 

  public abstract void displayCharacterInfo();

  /** overrides */

  @Override
  public String toString() {
    return String.format(
      "%s %d/%d",
      this.name, this.currentHealth, this.maxHealth
    );
  }
}
