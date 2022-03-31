/**
 * Attributes of the Player, which is a GameCharacter.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class Player extends GameCharacter {

  // player attributes
  private int level = 1;

  public Player(String name) {
    // init GameCharacter
    super();

    // init Player
    this.setName(name);
    this.resetHealth();
  }

  /** setters */

  /**
   * @param  level  player level as int
   */
  public void setLevel(int level) {
    this.level = level;
    this.resetHealth();
  }

  /** getters */

  /**
   * @return  player level as int
   */
  public int getLevel() {
    return this.level;
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
  public void resetHealth() {
    this.setMaxHealth(this.calculateMaxHealth());
    this.setDamage(this.calculateDamage());
  }

  /**
   * Prints the player stats to the system in the following format
   * Name (Lv. 1)
   * Damage: 2
   * Health: 20/20
   */
  @Override
  public void displayCharacterInfo() {
    System.out.printf("%s (Lv. %d)%n", this.getName(), this.level);
    System.out.printf("Damage: %d%n", this.getDamage());
    System.out.printf("Health: %d/%d%n", 
      this.getCurrentHealth(), this.getMaxHealth()
    );
  }
}
