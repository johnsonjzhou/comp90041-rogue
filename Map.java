/**
 * Handles matters related to the game map 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
import java.util.ArrayList;
import java.lang.NumberFormatException;

public class Map {

  public static final char GROUND = '.';
  public static final char MOUNTAIN = '#';
  public static final char WATER = '~';
  
  private int mapHeight;
  private int mapWidth;
  private char[][] map;

  public Map(ArrayList<String> blueprint) throws IOExceptions {
    this.parseMap(blueprint);
  }

  /**
   * Parses and validates the map blueprint 
   * @throws  IOExceptions  
   */
  private void parseMap(ArrayList<String> blueprint) throws IOExceptions {
    // first line of blueprint is dimensions
    String[] dimensions = blueprint.remove(0).split(" ");
    
    // validate 
    if (dimensions.length != 2) {
      throw new IOExceptions();
    }

    // init map dimensions 
    try {
      this.mapWidth = Integer.parseInt(dimensions[0]);
      this.mapHeight = Integer.parseInt(dimensions[1]);
    } catch (NumberFormatException e) {
      throw new IOExceptions();
    }

    // load and validate the map 
    if (blueprint.size() != this.mapHeight) {
      throw new IOExceptions();
    }

    for (int i = 0; i < this.mapHeight; i++) {
      char[] mapRow = blueprint.get(i).toCharArray();
      
      if (mapRow.length != this.mapWidth) {
        throw new IOExceptions();
      }

      for (int j = 0; j < this.mapWidth; j++) {
        switch(mapRow[j]) {
          case Map.GROUND: 
          case Map.MOUNTAIN: 
          case Map.WATER: 
            this.map[i][j] = mapRow[j];
            break; 
          default: 
            throw new IOExceptions();
        }
      }
    }
  }
}
