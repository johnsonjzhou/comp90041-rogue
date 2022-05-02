/**
 * Attributes of the Player, which is a GameCharacter.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class Player extends GameCharacter {

  // player attributes
  private int level = 1;

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

  /** getters */

  /**
   * @return  player level as int
   */
  public int getLevel() {
    return this.level;
  }

  /** public */

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
