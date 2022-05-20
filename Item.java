/**
 * Item entity for the world map 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class Item extends Entity {

  public static enum Types { 
    HEAL('+'), ATTACKUP('^'), WARPSTONE('@'); 
  
    char symbol;

    Types(char symbol) {
      this.symbol = symbol;
    }

    /**
     * @return  type symbol as char 
     */
    public char getSymbol() {
      return this.symbol;
    }

    /**
     * @param  other  another string to compare against 
     * @return  <code>True</code> if other string matches one of enum type symbols 
     */
    public boolean equals(String other) {
      return this.toString().equals(other);
    }

    /**
     * @return type symbol as String
     */
    @Override
    public String toString() {
      return Character.toString(this.symbol);
    }
  };

  private Types type; 
  
  public Item(Types type) {
    this.type = type;
  }

  /**
   * Short cut constructor at also invokes the <code>load</code> method. 
   * @param  loadString  matching format <code>item x y symbol</code> 
   * @param  map  the world map 
   * @throws  FileIOException  if loadString does not contain valid data 
   */
  public Item(String loadString, Map map) throws FileIOException {
    this.load(loadString, map);
  }

    /**
   * Loads Item map information from a string of text 
   * @param  loadString  matching format <code>item x y symbol</code> 
   * @param  map  the world map 
   * @throws  FileIOException  if loadString does not contain valid data 
   */
  public void load(String loadString, Map map) throws FileIOException {
    String[] loaded = loadString.split(" ");

    // validate string elements
    if (loaded.length != 4) {
      throw new FileIOException("Unexpected length of item load string");
    }

    // validate string identifier 
    if (!loaded[0].equals("item")) {
      throw new FileIOException("'item' identifier expected in loadString");
    }

    // parse item coordinates 
    int x = -1;
    int y = -1;
    try {
      x = Integer.parseInt(loaded[1]);
      y = Integer.parseInt(loaded[2]);
    } catch (NumberFormatException e) {
      throw new FileIOException("Could not parse item coordinates");
    }

    // set item coordinates 
    if (!map.traversable(x, y) || x < 0 || y < 0) {
      throw new FileIOException("Non-traversable item coordinates");
    }

    this.setX(x);
    this.setY(y);

    // parse and set item type 
    for (Types type : Types.values()) {
      if (type.equals(loaded[3])) {
        this.type = type;
        return;
      }
    }
    throw new FileIOException("Item symbol is not of known type");
  }

  /**
   * @return  this item type 
   */
  public Types getType() {
    return this.type;
  }

  /** Entity */

  @Override
  public char getMapMarker() {
    return this.type.getSymbol();
  }
}
