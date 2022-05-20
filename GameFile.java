/**
 * Handles loading and saving of game files 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
import java.util.ArrayList;

class GameFile {

  // game file parsed as an arraylist of string 
  private ArrayList<String> fileLines;

  // parsed game file particulars 
  private ArrayList<String> mapLines;
  private String playerLine;
  private ArrayList<String> monsterLines;
  private ArrayList<String> itemLines;

  /**
   * @param  fileLines  as extracted by FileIO 
   */
  public GameFile(ArrayList<String> fileLines) {
    this.fileLines = fileLines;
    this.mapLines = new ArrayList<String>();
    this.monsterLines = new ArrayList<String>();
    this.itemLines = new ArrayList<String>();
  }
  
  /**
   * Read file lines and parse map, player, monster and item information 
   * @throws  FileIOException  if file information is unexpected 
   */
  public void readFile() throws FileIOException {
    boolean playerLoaded = false;
    boolean monsterLoaded = false;
    boolean itemLoaded = false;

    // iterate through lines of loaded game file and segregating 
    // various map and entity information 
    for (int line = 0, mapRows = 0; line < this.fileLines.size(); line++) {
      // map dimensions in the first row
      if (line == 0) {
        try {
          mapRows = Integer.parseInt(this.fileLines.get(0).split(" ")[1]);
        } catch (Exception e) {
          throw new FileIOException("Could not parse file map dimensions");
        }
      }

      String lineContents = this.fileLines.get(line);

      // extract mapLines 
      if (line <= mapRows) {
        this.mapLines.add(lineContents);
      }

      // extract player
      if (lineContents.startsWith("player")) {
        if (monsterLoaded || itemLoaded) {
          throw new FileIOException("Player data out of order in file");
        }
        this.playerLine = lineContents;
        playerLoaded = true;
      }

      // extract monsters
      if (lineContents.startsWith("monster")) {
        if (!playerLoaded || itemLoaded) {
          throw new FileIOException("Monster data out of order in file");
        }
        this.monsterLines.add(lineContents);
        monsterLoaded = true;
      }

      // extract items
      if (lineContents.startsWith("item")) {
        if (!playerLoaded && !monsterLoaded) {
          throw new FileIOException("Item data out of order in file");
        }
        this.itemLines.add(lineContents);
        itemLoaded = true;
      }
    }
  }

  /**
   * @return  the map lines as an ArrayList of String
   */
  public ArrayList<String> getMapLines() {
    return this.mapLines;
  }

  /**
   * @return  the player line as a String
   */
  public String getPlayerLine() {
    return this.playerLine;
  }

  /**
   * @return  the monster lines as an ArrayList of String
   */
  public ArrayList<String> getMonsterLines() {
    return this.monsterLines;
  }

  /**
   * @return  the item lines as an ArrayList of String
   */
  public ArrayList<String> getItemLines() {
    return this.itemLines;
  }
}
