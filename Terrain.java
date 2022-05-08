/**
 * ! Deprecated
 * Terrain entity for the world map 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
@Deprecated
public class Terrain extends Entity {

  public static enum Types { GROUND, MOUNTAIN, WATER };

  private Types type; 

  public Terrain() {
    this.type = Types.GROUND;
  }
  
  public Terrain(Types type) {
    this.type = type;
  }

  /** getter */

  public boolean isPassable() {
    switch(this.type) {
      case MOUNTAIN: return false;
      case WATER: return false;
      case GROUND: default: return true;
    }
  }

  /** Entity */

  @Override
  public char getMapMarker() {
    switch(this.type) {
      case MOUNTAIN: return '#';
      case WATER: return '~';
      case GROUND: default: return '.';
    }
  }
}
