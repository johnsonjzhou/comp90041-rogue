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

  /** getter */

  public boolean isPassable() {
    switch(this.type) {
      case HEAL: return false;
      case ATTACKUP: return false;
      case WARPSTONE: default: return true;
    }
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
