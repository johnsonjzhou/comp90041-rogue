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
}
