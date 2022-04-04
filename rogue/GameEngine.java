/**
 * The Rogue game engine.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
import java.lang.reflect.Field;

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

  private GameCharacter player;
  // ?: there may be more than one monster in the future
  private GameCharacter[] monsters;
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
        String input = this.console.readNext();
        
        switch(input) {
          case GameEngine.MENU_CMD_COMMANDS:
            this.displayCommands();
            continue commandLoop;

          case GameEngine.MENU_CMD_HELP:
            this.displayHelp();
            continue commandLoop;

          case GameEngine.MENU_CMD_PLAYER:
            if (this.player == null) {
              this.createPlayer();
            } else {
              this.displayPlayer();
            }
            this.console.waitUserEnter();
            break commandLoop;

          case GameEngine.MENU_CMD_MONSTER:
            this.createMonster();
            this.console.waitUserEnter();
            break commandLoop;

          case GameEngine.MENU_CMD_START:
            this.startGame();
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
      this.monsters == null ? 
        GameEngine.NULL_CHARACTER_MSG : 
        this.monsters[(this.monsters.length - 1)].getStats()
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

  private void displayPlayer() {
    //todo
  }

  private void createPlayer() {
    //todo
  }

  private void createMonster() {
    //todo
  }

  private void startGame() {
    //todo
  }
}
