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
