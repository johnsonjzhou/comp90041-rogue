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

  /**
   * @param  blueprint  the map scaffold as loaded from file 
   */
  public Map(ArrayList<String> blueprint) throws IOExceptions {
    this.parseMap(blueprint);
  }

  /**
   * Parses and validates the map blueprint 
   * @throws  IOExceptions  if blueprint does not match expected. 
   *                        use <code>error.getCause</code> for more details. 
   */
  private void parseMap(ArrayList<String> blueprint) throws IOExceptions {
    // first line of blueprint is dimensions
    String[] dimensions = blueprint.remove(0).split(" ");
    
    // validate 
    if (dimensions.length != 2) {
      throw new IOExceptions("Could not validate map dimensions");
    }

    // init map dimensions 
    try {
      this.mapWidth = Integer.parseInt(dimensions[0]);
      this.mapHeight = Integer.parseInt(dimensions[1]);
      this.map = new char[this.mapHeight][this.mapWidth];
    } catch (NumberFormatException e) {
      throw new IOExceptions("Could not interpret map dimensions");
    }

    // load and validate the map 
    if (blueprint.size() != this.mapHeight) {
      throw new IOExceptions( 
        String.format("Map height mismatch expected %d given %d", 
          this.mapHeight, blueprint.size()
        )
      );
    }

    for (int i = 0; i < this.mapHeight; i++) {
      char[] mapRow = blueprint.get(i).toCharArray();
      
      if (mapRow.length != this.mapWidth) {
        throw new IOExceptions(
          String.format("Map width mismatch at row %d, expected %d given %d", 
            i, this.mapWidth, mapRow.length
          )
        );
      }

      for (int j = 0; j < this.mapWidth; j++) {
        switch(mapRow[j]) {
          case Map.GROUND: 
          case Map.MOUNTAIN: 
          case Map.WATER: 
            this.map[i][j] = mapRow[j];
            break; 
          default: 
            throw new IOExceptions(
              String.format("Unsupported map char %c at [%d][%d]", 
                mapRow[j], i, j
              )
            );
        }
      }
    }
  }

  /**
   * @return  the map that was loaded as a 2D char array 
   */
  public char[][] getMap() {
    return this.map;
  }

  /**
   * Tests whether a particular location in the map is traversable 
   * @param  col  column value 
   * @param  row  row value 
   * @return  <code>True</code> if traversable 
   */
  public boolean traversable(int col, int row) {
    switch(this.map[row][col]) {
      case Map.GROUND: 
        return true;
      case Map.MOUNTAIN: 
      case Map.WATER: 
      default: 
        return false;
    }
  }
}
