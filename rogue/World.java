/**
 * The Rogue world map.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class World {

  public static final int DEFAULT_HEIGHT = 4;
  public static final int DEFAULT_WIDTH = 6;
  public static final int PLAYER_START_X = 1;
  public static final int PLAYER_START_Y = 1;
  public static final int MONSTER_START_X = 4;
  public static final int MONSTER_START_Y = 2;

  private GameCharacter player;
  private GameCharacter monster;
  private int mapHeight;
  private int mapWidth;

  public World() {
    this.mapHeight = World.DEFAULT_HEIGHT;
    this.mapWidth = World.DEFAULT_WIDTH;
  }

  public World(int mapHeight, int mapWidth) {
    this.mapHeight = mapHeight;
    this.mapWidth = mapWidth;
  }

  /** setters */

  /**
   * @param  player - the player as GameCharacter
   */
  public void setPlayer(GameCharacter player) {
    this.player = player;
  }

  /**
   * @param  monster - the monster as GameCharacter
   */
  public void setMonster(GameCharacter monster) {
    this.monster = monster;
  }

  /** private */

  /**
   * Outputs the world map as follows
   * ......
   * .B....
   * ....o.
   * ......
   */
  private void renderMap() {
    if (this.player == null || this.monster == null) { return; }
    for (int row = 0; row < this.mapHeight; row++) {
      for (int col = 0; col < this.mapWidth; col++) {
        char output = '.';
        if (col == this.player.getX() && row == this.player.getY()) {
          output = this.player.getMapMarker();
        }
        if (col == this.monster.getX() && row == this.monster.getY()) {
          output = this.monster.getMapMarker();
        }
        System.out.print(output);
      }
      System.out.printf("%n");
    }
  }

  /**
   * Resets the player and monster locations to default
   */
  private void reset() {
    if (this.player != null) { 
      this.player.setX(World.PLAYER_START_X);
      this.player.setY(World.PLAYER_START_Y);
    }
    if (this.monster != null) {
      this.monster.setX(World.MONSTER_START_X);
      this.monster.setY(World.MONSTER_START_Y);
    }
  }

  /**
   * Checks whether player and monster has collided
   * @return  true if collision | false if not
   */
  private boolean checkCollision() {
    return (
      this.player.getX() == this.monster.getX() && 
      this.player.getY() == this.monster.getY()
    );
  }

  /** public */

  /**
   * Starts the game world. Check if player and monster have been 
   * initiated. 
   * @return  false if not ready
   */
  public boolean start() {
    if (this.player == null || this.monster == null) { return false; }
    this.reset();
    this.renderMap();

    //todo  begin movement loop
    //todo  Console class
    
    return true;
  }
}
