/**
 * Item entity for the world map 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class Item extends Entity {

  public static enum Types { HEAL, ATTACKUP, WARPSTONE };

  private Types type; 
  
  public Item(Types type) {
    this.type = type;
  }

  /** Entity */

  @Override
  public char getMapMarker() {
    switch(this.type) {
      case HEAL: return '+';
      case ATTACKUP: return '^';
      case WARPSTONE: default: return '@';
    }
  }
}
