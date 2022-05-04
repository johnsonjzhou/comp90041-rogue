import java.lang.reflect.Field;

public class TestHarness {
  public static void main(String[] args) {
    Player player = new Player();
    Monster monster = new Monster();
    //monster.create("Orc", 15, 2);
    //System.out.println(monster.create("Slimy", 12, 1));
    monster.create("Slimy", 12, 1);
    player.create("Bilbo");
    // player.displayCharacterInfo();
    /* test.attacks(test2);
    System.out.println(test);
    System.out.println(test2); */
    //System.out.println(player.getMapMarker());
    //System.out.println(monster.displayCharacterInfo());
    //World world = new World();
    //world.setMonster(monster);
    //world.setPlayer(player);
    //world.start();
    
    UserConsole console = new UserConsole();

    //console.printPrompt();
    //console.readInt("Must be a number");
    
    /* World world = new World();
    world.setPlayer(player);
    world.setMonster(player);
    world.setConsole(console);
    try {
      world.start();
    } catch (CharacterCollision c) {
      Battle battle = c.getBattle();
      battle.announce();
      battle.begin();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    console.waitUserEnter(); */

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
}
