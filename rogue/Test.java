public class Test {
  public static void main(String[] args) {
    Player test = new Player("johnson");
    Player test2 = new Player("");
    test.attacks(test2);
    System.out.println(test);
    System.out.println(test2);
  }
}
