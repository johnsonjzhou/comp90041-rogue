/**
 * The Rogue world map.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
import java.util.ArrayList;

public class World {

  public static final int DEFAULT_HEIGHT = 4;
  public static final int DEFAULT_WIDTH = 6;
  public static final int PLAYER_START_X = 1;
  public static final int PLAYER_START_Y = 1;
  public static final int MONSTER_START_X = 4;
  public static final int MONSTER_START_Y = 2;

  private Player player;
  private ArrayList<Monster> monsters;
  private int mapHeight;
  private int mapWidth;
  private UserConsole console;

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
   * @param  player - the player as Player
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * @param  monster - the monster as ArrayList<Monster>
   */
  public void setMonster(ArrayList<Monster> monsters) {
    this.monsters = monsters;
  }

  /**
   * @param  console - the user console to receive user inputs
   */
  public void setConsole(UserConsole console) {
    this.console = console;
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
    for (int row = 0; row < this.mapHeight; row++) {
      for (int col = 0; col < this.mapWidth; col++) {
        char output = '.';
        // check if position matches the player
        if (
          this.player != null & 
          col == this.player.getX() && row == this.player.getY()
        ) {
          output = this.player.getMapMarker();
        }

        // check if position matches any of the monsters
        for (Monster monster : this.monsters) {
          if (col == monster.getX() && row == monster.getY()) {
            output = monster.getMapMarker();
          }
          System.out.print(output);
        }
      }
      System.out.printf("%n");
    }
  }

  /**
   * Resets the player and monster locations to default
   * ! ALERT, if multiple monsters, they all start at the same location
   * ! take care for future scope
   */
  private void reset() {
    if (this.player != null) { 
      this.player.setX(World.PLAYER_START_X);
      this.player.setY(World.PLAYER_START_Y);
    }

    for (Monster monster : this.monsters) {
      monster.setX(World.MONSTER_START_X);
      monster.setY(World.MONSTER_START_Y);
    }
  }

  /**
   * Checks whether player and monster has collided
   * @return  -1 no collision | index of coliding monster
   */
  private int checkCollision() {
    for (Monster monster : this.monsters) {
      if (
        this.player.getX() == monster.getX() && 
        this.player.getY() == monster.getY()
      ) {
        return this.monsters.indexOf(monster);
      }
    }
    return -1;
  }

  private void movementLoop() throws CharacterCollision{
    moving : while(true) {
      this.renderMap();
      this.console.printPrompt();
      String command = this.console.readNext();
      int newX = this.player.getX();
      int newY = this.player.getY();
      switch(command) {
        case "w":
          newY -= 1;
          break;
        case "s":
          newY += 1;
          break;
        case "a":
          newX -= 1;
          break;
        case "d":
          newX +=1;
          break;
        case "home":
          System.out.println(GameEngine.RETURN_HOME_MSG);
          break moving;
        default:
          continue moving;
      }
      if (
        (newX >= 0) && (newX < this.mapWidth) && 
        (newY >= 0) && (newY < this.mapHeight)
      ) {
        this.player.setX(newX);
        this.player.setY(newY);
      }

      int monsterCollision = this.checkCollision();
      if (monsterCollision >= 0) {
        throw new CharacterCollision(
          new Battle(this.player, this.monsters.get(monsterCollision))
        );
      }
    }
  }

  /** public */

  /**
   * Starts the game world. Check if player and monster have been 
   * initiated. 
   * If the player collides with the monster, a CharacterCollision
   * exception bubbles.
   * @return  false if not ready
   */
  public void start() throws WorldNotReady, CharacterCollision {
    if (this.console == null) {
      throw new WorldNotReady(GameEngine.GEN_ERROR_MSG);
    }

    if (this.player == null) {
      throw new WorldNotReady(GameEngine.NO_PLAYER_MSG);
    }

    if (this.monsters == null || this.monsters.size() <= 0) {
      throw new WorldNotReady(GameEngine.NO_MONSTER_MSG);
    }

    // reset the player positions
    this.reset();

    // begin the movement loop
    try {
      this.movementLoop();
    } catch (CharacterCollision c) {
      // bubble the CharacterCollision and Battle to the GameEngine
      throw new CharacterCollision(c.getBattle());
    }
  }
}

/** thrown exceptions */

class WorldNotReady extends Exception {
  public WorldNotReady(String message) {
    super(message);
  }

  /**
   * Outputs the message to the screen
   */
  public void announce() {
    System.out.println(this.getMessage());
  }
}

/**
 * To be thrown when two game characters collide and will 
 * carry a Battle object with the attacker and defender
 */
class CharacterCollision extends Exception {

  private Battle battle;

  /**
   * @param  battle - a battle resulting from the collision
   */
  public CharacterCollision(Battle battle) {
    super("Game characters have collided");
    this.battle = battle;
  }

  /**
   * @return  the battle object
   */
  public Battle getBattle() {
    return this.battle;
  }
}