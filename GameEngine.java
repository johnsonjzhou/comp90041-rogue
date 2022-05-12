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

  public static final String PLAYER_SAVED_MSG = 
    "Player data saved.";

  public static final String PLAYER_LOADED_MSG = 
    "Player data loaded.";

  public static final String PLAYER_LOAD_ERROR = 
    "No player data found.";

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

  public static final String IO_LOAD_ERROR = 
    "An error occurred while loading the file.";

  public static final String IO_SAVE_ERROR = 
    "An error occurred while saving the file.";

  public static final String EXIT_MSG = 
    "Thank you for playing Rogue!";

  public static final String MENU_CMD_COMMANDS = "commands";
  public static final String MENU_CMD_HELP = "help";
  public static final String MENU_CMD_PLAYER = "player";
  public static final String MENU_CMD_LOAD = "load";
  public static final String MENU_CMD_SAVE = "save";
  public static final String MENU_CMD_MONSTER = "monster";
  public static final String MENU_CMD_START = "start";
  public static final String MENU_CMD_EXIT = "exit";

  private Player player;
  private Monster monster;
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
        this.console.clearBuffer();
        String input = this.console.readBufferedNext();
        
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
            this.startGameDefault();
            this.console.waitUserEnter();
            break commandLoop;

          case GameEngine.MENU_CMD_EXIT:
            System.out.println(GameEngine.EXIT_MSG);
            break menuLoop;

          case GameEngine.MENU_CMD_LOAD:
            this.loadPlayer();
            continue commandLoop;

          case GameEngine.MENU_CMD_SAVE:
            this.savePlayer();
            continue commandLoop;

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
      this.monster == null ? 
        GameEngine.NULL_CHARACTER_MSG : this.monster.getStats()
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
   * load
   * save
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
   * Loads player information from player.dat
   */
  public void loadPlayer() {
    // create a player if none already exist
    if (this.player == null) {
      this.player = new Player();
    }

    // read the save line from file 
    try {
      FileIO file = new FileIO("player.dat");
      if (file.exists() && file.canReadNextLine()) {
        this.player.load(file.readNextLine());
        System.out.println(GameEngine.PLAYER_LOADED_MSG);
        return;
      }
      System.out.println(GameEngine.PLAYER_LOAD_ERROR);
    } catch (IOExceptions e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Saves current player information to player.dat
   */
  private void savePlayer() {
    // handle player not created
    if (this.player == null) {
      System.out.println(GameEngine.NO_PLAYER_MSG);
      return;
    }

    // format playerData into saveable string 
    String playerData = String.format("%s %s", 
      this.player.getName(), this.player.getLevel()
    );
    
    // save to player.dat 
    try {
      FileIO file = new FileIO("player.dat");
      file.setWritable().overwrite(playerData);
      System.out.println(GameEngine.PLAYER_SAVED_MSG);
    } catch (IOExceptions e) {
      System.out.println(GameEngine.IO_SAVE_ERROR);
    }
  }

  /**
   * Create a new monster
   */
  private void createMonster() {
    // create a new monster and add it to the monsters list
    this.monster = new Monster();
    System.out.print("Monster name: ");
    String name = this.console.readNext();
    System.out.print("Monster health: ");
    int maxHealth = this.console.readInt(GameEngine.INT_INPUT_MSG);
    System.out.print("Monster damage: ");
    int damage = this.console.readInt(GameEngine.INT_INPUT_MSG);
    this.monster.create(name, maxHealth, damage);
  }

  /**
   * Starts a default game, which behaves identically to A1
   */
  private void startGameDefault() {
    ArrayList<Entity> entities = new ArrayList<Entity>();
    
    // handle player not created
    if (this.player == null) {
      System.out.println(GameEngine.NO_PLAYER_MSG);
      return;
    }

    // handle monster not created
    if (this.monster == null) {
      System.out.println(GameEngine.NO_MONSTER_MSG);
      return;
    }

    // restore player health and add to the world
    if (this.player != null) {
      this.player.restoreHealth();
      this.player.resetLocation();
    }

    // restore monster health and add to the world
    if (this.monster != null) {
      this.monster.restoreHealth();
      this.monster.resetLocation();
      entities.add(monster);
    }

    try {
      Map map = new Map();
      this.startWorld(map, this.player, entities);
    } catch (IOExceptions e) {
      // actually should not error here 
      System.out.println(e.getCause());
    }
  }

  /**
   * Starts a game by creating a new game world 
   * @param  map  a map instance 
   * @param  player  a player instance 
   * @param  entities  an ArrayList of Entity, one of which should be a monster
   */
  private void startWorld(Map map, Player player, ArrayList<Entity> entities) {
    try {
      World world = new World(map, player, entities);
      world.setConsole(this.console);
      world.start();
    } catch (WorldNotReady e) {
      System.out.println(e.getMessage());
    } catch (GameOver e) {
      // do nothing 
    } catch (Exception e) {
      System.out.println(GameEngine.GEN_ERROR_MSG);
    }
  }
}
