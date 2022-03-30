/**
 * Base attributes for a Game Character in Rogue.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
public class GameCharacter {
  
  // character state
  private String name = "Character";
  private int maxHealth = 0;
  private int currentHealth = 0;
  private int damage = 0;

  // character position
  // ? should this be in character or map
  private int posX = 0;
  private int posY = 0;

  public GameCharacter() { }

  /** setters */

  /** 
   * Sets the name, ensuring the first character is a capital. 
   * If a name is not provided, a random name will be assigned. 
   * @param  name - the game character name
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

  public void setDamage(int damage) {
    this.damage = damage;
  }

  /** getters */

  public String getName() {
    return this.name;
  }

  public int getMaxHealth() {
    return this.maxHealth;
  }

  public int getCurrentHealth() {
    return this.currentHealth;
  }

  public int getDamage() {
    return this.damage;
  }

  /** public */

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
   * Attacks the foe by dealing it damage
   * @param  GameCharacter - another game character as the foe
   * @return  true if killed the foe || false if foe lives
   */
  public boolean attacks(GameCharacter foe) {
    int foeHealth = foe.receiveDamage(this.getDamage());
    return (foeHealth <= 0);
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
