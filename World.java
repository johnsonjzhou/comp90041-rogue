/**
 * The Rogue world map.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.lang.Math;

public class World {

  private Player player;
  private ArrayList<Entity> entities;
  private Map map;
  private UserConsole console;
  private boolean monsterMove = false;
  private boolean winWhenNoMonsters = false;

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

  /**
   * @param  moveable  whether the mosters are allowed to move 
   */
  public void setMonsterMove(boolean moveable) {
    this.monsterMove = moveable;
  }

  /**
   * @param  winWhenNoMonsters  whether to end the game when all monsters defeated 
   */
  public void setWinCondition(boolean winWhenNoMonsters) {
    this.winWhenNoMonsters = winWhenNoMonsters;
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
      if (this.monsterMove) {
        this.moveMonsters();
      }
      this.console.printPrompt();

      // break out of loop if directed input non-existent 

      
      // readBufferedNext is required as opposed to readNext for empty inputs
      String command;
      try {
        this.console.clearBuffer();
        command = this.console.readBufferedNext();
        this.console.clearBuffer();
      } catch (NoSuchElementException e) {
        // handle end of file in directed inputs 
        throw new GameOver();
      }

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
          this.player.resetDamageModifier();
          throw new GameOver();
        default:
          break;
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
        }

        if (entity instanceof Item) {
          this.itemPickup((Item) entity);
          this.entities.remove(entity);
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
   * Moves any of the monster entities 
   */
  private void moveMonsters() {
    // do not move monsters if negative flag 
    if (!this.monsterMove) {
      return;
    }

    getMonster : for (Entity entity : this.entities) {
      // do nothing if not a monster 
      if (!(entity instanceof Monster)) {
        continue getMonster;
      }

      Monster monster = (Monster) entity;

      int vectorX = monster.getX() - this.player.getX();
      int deltaX = Math.abs(vectorX);
      int vectorY = monster.getY() - this.player.getY();
      int deltaY = Math.abs(vectorY);

      if (deltaX > 2 || deltaY > 2) {
        // player outside 2x2 grid 
        continue getMonster;
      }

      // move east / west 
      if (deltaX > 0) {
        int newX = monster.getX() - (vectorX / deltaX);
        if (map.traversable(newX, monster.getY())) {
          monster.setX(newX);
          continue getMonster;
        }
      }
      
      // move north / south 
      if (deltaY > 0) {
        int newY = monster.getY() - (vectorY / deltaY);
        if (map.traversable(monster.getX(), newY)) {
          monster.setY(newY);
          continue getMonster;
        }
      }
    }
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
      System.out.println();
      this.entities.remove(battle.getLoser());
      // game ends if all monsters defeated in V1
      // game continues if all monster defeated per Ed post #266 in V2
      if (this.winWhenNoMonsters && !this.checkMonsterExist()) {
        throw new GameOver();
      }
    }
    if (winner instanceof Monster) {
      this.player.resetDamageModifier();
      throw new GameOver();
    }
  }

  /**
   * Handles item pickup 
   * @throws GameOver if planer picks up the warp stone 
   */
  private void itemPickup(Item item) throws GameOver {
    switch (item.getType()) {
      case HEAL:
        this.player.restoreHealth();
        System.out.println(GameEngine.ITEM_HEAL);
        break;
      case ATTACKUP:
        this.player.increaseDamageModifier();
        System.out.println(GameEngine.ITEM_ATTACK_UP);
        break;
      case WARPSTONE: 
        this.playerWins();
        throw new GameOver();
      default:
        return;
    }
  }

  /**
   * Handle when player wins the level 
   */
  private void playerWins() {
    this.player.setLevel(this.player.getLevel() + 1);
    System.out.println(GameEngine.ITEM_COMPLETE);
  }

  /**
   * Checks entities for a warp stone, if not, 
   * set the win condition to when all monsters have been eliminated 
   * @see  https://edstem.org/au/courses/7656/discussion/868301
   */
  private void checkWarpStoneExist() {
    checkEntities : for (Entity entity : this.entities) {
      if (!(entity instanceof Item)) {
        continue checkEntities;
      }

      Item item = (Item) entity;
      
      switch (item.getType()) {
        case WARPSTONE:
          return;
        default:
          continue checkEntities;
      }
    }

    // if no warp stone is present, win when no monsters 
    this.winWhenNoMonsters = true;
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

    // check for warp stone and handle win condition 
    this.checkWarpStoneExist();

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
