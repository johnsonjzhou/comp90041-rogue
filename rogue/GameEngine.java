/**
 * The Rogue game engine.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
import java.lang.reflect.Field;
import java.util.ArrayList;

public class GameEngine {

  public static final String NO_PLAYER_MSG = 
    "No player found, please create a player with 'player' first.";

  public static final String NO_MONSTER_MSG = 
    "No monster found, please create a monster with 'monster' first.";

  public static final String NULL_CHARACTER_MSG = 
    "[None]";

  public static final String GEN_ERROR_MSG = 
    "Darn something unexpected happened...";

  public static final String INVALID_INPUT_MSG = 
    "Invalid input, please try again";

  public static final String INT_INPUT_MSG = 
    "Please enter a number";

  public static final String WAIT_ENTER_KEY_MSG = 
    "Press enter key to return to main menu";

  public static final String RETURN_HOME_MSG = 
    "Returning home...";

  public static final String EXIT_MSG = 
    "Thank you for playing Rogue!";

  public static final String MENU_CMD_COMMANDS = "commands";
  public static final String MENU_CMD_HELP = "help";
  public static final String MENU_CMD_PLAYER = "player";
  public static final String MENU_CMD_MONSTER = "monster";
  public static final String MENU_CMD_START = "start";
  public static final String MENU_CMD_EXIT = "exit";

  private World world;
  private Player player;
  // ?: there may be more than one monster in the future
  private ArrayList<Monster> monsters;
  private UserConsole console;
  
  public static void main(String[] args) {
    
    // Creates an instance of the game engine.
    GameEngine gameEngine = new GameEngine();
    
    // Runs the main game loop.
    gameEngine.runGameLoop();
  }

  public GameEngine() {
    // Creates an instance of UserConsole to receive user inputs
    this.console = new UserConsole();

    // initiate monsters list
    this.monsters = new ArrayList<Monster>();

    // initiate World and attach the user console
    this.world = new World();
    this.world.setConsole(this.console);
  }

  /** private */
  
  /** 
   *  Logic for running the main game loop.
   */
  private void runGameLoop() {

    // menu loop
    menuLoop : while(true) {
      // print out the title text.
      displayTitleText();

      commandLoop : while(true) {
        this.console.printPrompt();
        String input = this.console.readNext();
        
        switch(input) {
          case GameEngine.MENU_CMD_COMMANDS:
            this.displayCommands();
            continue commandLoop;

          case GameEngine.MENU_CMD_HELP:
            this.displayHelp();
            continue commandLoop;

          case GameEngine.MENU_CMD_PLAYER:
            this.createPlayer();
            this.console.waitUserEnter();
            break commandLoop;

          case GameEngine.MENU_CMD_MONSTER:
            this.createMonster();
            this.console.waitUserEnter();
            break commandLoop;

          case GameEngine.MENU_CMD_START:
            this.startGame();
            this.console.waitUserEnter();
            break commandLoop;

          case GameEngine.MENU_CMD_EXIT:
            System.out.println(GameEngine.EXIT_MSG);
            break menuLoop;

          default:
            this.displayHelp();
            continue commandLoop;
        }
      }
    }

    // tidy up
    if (this.console != null) {
      this.console.close();
    }
  }
  
  /**
   * Displays the title text.
   */
  private void displayTitleText() {
    
    String titleText = " ____                        \n" + 
        "|  _ \\ ___   __ _ _   _  ___ \n" + 
        "| |_) / _ \\ / _` | | | |/ _ \\\n" + 
        "|  _ < (_) | (_| | |_| |  __/\n" + 
        "|_| \\_\\___/ \\__, |\\__,_|\\___|\n" + 
        "COMP90041   |___/ Assignment ";
    
    System.out.println(titleText);
    System.out.println();

    this.displayGameCharacters();

    System.out.println();
    System.out.println("Please enter a command to continue.");
    System.out.println("Type 'help' to learn how to get started.");
  }

  /**
   * Displays the player and monster stats on the main menu
   * Player: Bilbo 20/20  | Monster: [None]
   */
  private void displayGameCharacters() {
    System.out.printf("Player: %s  | Monster: %s%n", 
      this.player == null ? 
        GameEngine.NULL_CHARACTER_MSG : this.player.getStats(), 
      this.monsters.size() == 0 ? 
        GameEngine.NULL_CHARACTER_MSG : 
        // display the most recently created monster
        this.monsters.get(this.monsters.size() - 1).getStats() 
    );
  }

  /**
   * Type 'commands' to list all available commands
   * Type 'start' to start a new game
   * Create a character, battle monsters, and find treasure!
   */
  private void displayHelp() {
    System.out.println("Type 'commands' to list all available commands");
    System.out.println("Type 'start' to start a new game");
    System.out.println("Create a character, battle monsters, and find treasure!");
  }

  /**
   * Displays a list of available commands, save for its self
   * help
   * player
   * monster
   * start
   * exit
   */
  private void displayCommands() {
    Field[] fields = GameEngine.class.getDeclaredFields();
    for (Field field : fields) {
      if (field.getName().startsWith("MENU_CMD") && 
        !field.getName().equals("MENU_CMD_COMMANDS") && 
        field.getType().equals(String.class) 
      ) {
        try {
          System.out.println((String) field.get(null));
        } catch (IllegalAccessException e) {}
      }
    }
  }

  /**
   * Displays character info for the player
   */
  private void displayPlayer() {
    if (this.player != null) {
      this.player.displayCharacterInfo();
    }
  }

  /**
   * If a player does not exist, create a new player
   */
  private void createPlayer() {
    if (this.player != null) {
      this.displayPlayer();
      return;
    }
    this.player = new Player();
    System.out.println("What is your character's name?");
    String name = this.console.readNext();
    this.player.create(name);
  }

  /**
   * Create a new monster
   */
  private void createMonster() {
    // handle monster quantity
    // there is only one monster in this version
    // ?: this behaviour may change in future versions
    if (this.monsters.size() >= 0) {
      this.monsters.clear();
    }

    // create a new monster and add it to the monsters list
    Monster newMonster = new Monster();
    System.out.print("Monster name: ");
    String name = this.console.readNext();
    System.out.print("Monster health: ");
    int maxHealth = this.console.readInt(GameEngine.INT_INPUT_MSG);
    System.out.print("Monster damage: ");
    int damage = this.console.readInt(GameEngine.INT_INPUT_MSG);
    newMonster.create(name, maxHealth, damage);
    this.monsters.add(newMonster);
  }

  private void startGame() {
    // handle player not created
    if (this.player == null) {
      System.out.println(GameEngine.NO_PLAYER_MSG);
      return;
    }

    // handle monster not created
    if (this.monsters == null || this.monsters.size() <= 0) {
      System.out.println(GameEngine.NO_MONSTER_MSG);
      return;
    }

    // restore player health and add to the world
    if (this.player != null) {
      this.player.restoreHealth();
      this.world.setPlayer(this.player);
    }

    // restore monster health and add to the world
    for (Monster monster : this.monsters) {
      monster.restoreHealth();
    }
    this.world.setMonster(this.monsters);

    // start the world engine
    // initiate a battle if a player-monster collision is thrown within the world
    // catch if the world is not ready (player, monsters or console not set)
    // catch additional exceptions if they arise
    try {
      world.start();
    } catch (CharacterCollision c) {
      Battle battle = c.getBattle();
      battle.announce();
      battle.begin();
    } catch (WorldNotReady e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println(GameEngine.GEN_ERROR_MSG);
    }
  }
}
