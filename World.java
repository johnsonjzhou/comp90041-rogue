/**
 * The Rogue world map.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
import java.util.ArrayList;

public class World {

  private Player player;
  private ArrayList<Entity> entities;
  private Map map;
  private UserConsole console;

  public World(Map map, Player player, ArrayList<Entity> entities) {
    this.setMap(map);
    this.setPlayer(player);
    this.setEntities(entities);
  }

  /** setters */

  /**
   * @param  map - the map as Map 
   */
  public void setMap(Map map) {
    this.map = map;
  }

  /**
   * @param  player - the player as Player
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  public void setEntities(ArrayList<Entity> entities) {
    this.entities = entities;
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
    char[][] map = this.map.getMap();
    for (int row = 0; row < map.length; row++) {
      char[] mapRow = map[row];
      for (int col = 0; col < mapRow.length; col++) {
        // default map marker
        char marker = mapRow[col];

        // check if position matches any of the entities
        findEntity : for (Entity entity : this.entities) {
          if (col == entity.getX() && row == entity.getY()) {
            marker = entity.getMapMarker();
            break findEntity;
          }
        }

        // check if position matches the player
        if (col == this.player.getX() && row == this.player.getY()) {
          marker = this.player.getMapMarker();
        }
        
        // display the marker
        System.out.print(marker);
      }
      System.out.printf("%n");
    }
  }

  /**
   * Checks whether player and entities have collided. 
   * Seggregate entities into monsters and items. 
   * If multiple, this returns the entity that was added first in the ArrayList. 
   * If mixed monsters, items, monsters will preceed items in the ArrayList. 
   * @return  ArrayList of Entities, will be empty if no collisions 
   */
  private ArrayList<Entity> checkCollision() {
    ArrayList<Entity> collidedEntities = new ArrayList<Entity>();
    ArrayList<Entity> collidedMonsters = new ArrayList<Entity>();
    ArrayList<Entity> collidedItems = new ArrayList<Entity>();

    for (Entity entity : this.entities) {
      if (
        this.player.getX() == entity.getX() && 
        this.player.getY() == entity.getY()
      ) {
        if (entity instanceof Monster) {
          collidedMonsters.add(entity);
        }
        if (entity instanceof Item) {
          collidedItems.add(entity);
        }
      }
    }

    if (collidedMonsters.size() > 0) {
      collidedEntities.addAll(collidedMonsters);
    }

    if (collidedItems.size() > 0) {
      collidedEntities.addAll(collidedItems);
    }

    return collidedEntities;
  }

  private void movementLoop() throws GameOver {
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
          newX += 1;
          break;
        case "home":
          System.out.println(GameEngine.RETURN_HOME_MSG);
          break moving;
        default:
          continue moving;
      }
      if (this.map.traversable(newX, newY)) {
        this.player.setX(newX);
        this.player.setY(newY);
      }

      ArrayList<Entity> collidedEntities = this.checkCollision();

      if (collidedEntities.size() < 1) {
        continue moving;
      }

      for (Entity entity : collidedEntities) {
        if (entity instanceof Monster) {
          this.battleLoop(
            new Battle(this.player, (Monster) entity)
          );
          System.out.println("Monster collision");
        }
        if (entity instanceof Item) {
          // todo implement
          System.out.println("Item collision");
        }
      }
    }
  }

  /**
   * @return  <code>True</code> if at least one monster exist in entities 
   */
  private boolean checkMonsterExist() {
    for (Entity entity : this.entities) {
      if (entity instanceof Monster) {
        return true;
      }
    }
    return false;
  }

  /**
   * Runs the battleloop
   * If the player is the winner, remove the monster 
   * If the monster is the winner, Game Over 
   * @throws  GameOver  if player loses 
   * @throws  GameOver  if there are no more monsters 
   */
  private void battleLoop(Battle battle) throws GameOver {
    battle.announce();
    battle.begin();
    GameCharacter winner = battle.getWinner();
    if (winner instanceof Player) {
      this.entities.remove(battle.getLoser());
      if (!this.checkMonsterExist()) {
        throw new GameOver();
      }
    }
    if (winner instanceof Monster) {
      throw new GameOver();
    }
  }

  /** public */

  /**
   * Starts the game world. Check if player and monster have been 
   * initiated. 
   * @return  false if not ready
   */
  public void start() throws WorldNotReady, GameOver {
    if (this.console == null) {
      throw new WorldNotReady(GameEngine.GEN_ERROR_MSG);
    }

    if (this.player == null) {
      throw new WorldNotReady(GameEngine.NO_PLAYER_MSG);
    }

    if (this.entities == null || !this.checkMonsterExist()) {
      throw new WorldNotReady(GameEngine.NO_MONSTER_MSG);
    }

    // begin the movement loop
    this.movementLoop();
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

class GameOver extends Exception {
  public GameOver() {
    super();
  }
}
