/**
 * Attributes of the Monster, which is a GameCharacter.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class Monster extends GameCharacter {

  public Monster() {
    super();
  }

  /** getter */

  /**
   * @return  the first letter of the name in lower case
   */
  @Override
  public char getMapMarker() {
    char marker = this.getName().charAt(0);
    return Character.toLowerCase(marker);
  }

  /** public */

  /**
   * Populates the monster attributes
   * @param  name - monster name as String
   * @param  maxHealth - maximum health as int
   * @param  damage - damage value as int
   * @return  Monster '[name]' created.
   */
  public String create(String name, int maxHealth, int damage) {
    this.setName(name);
    this.setMaxHealth(maxHealth);
    this.setDamage(damage);
    return String.format("Monster '%s' created.", this.getName());
  }
}
