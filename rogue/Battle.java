/**
 * Battle mechanics
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class Battle {
  
  private GameCharacter attacker;
  private GameCharacter defender;
  private GameCharacter winner;
  private boolean inCombat;


  public Battle(GameCharacter attacker, GameCharacter defender) {
    this.attacker = attacker;
    this.defender = defender;
    this.inCombat = true;
    System.out.printf("%s encountered a %s!%n%n", 
      this.attacker.getName(), this.defender.getName()
    );
  }

  /** setters */

  public void setWinner(GameCharacter winner) {
    this.winner = winner;
    this.inCombat = false;
  }

  /** private */

  /** 
   * Outputs the attacker and defender stats to the screen
   * Bilbo 20/20 | Orc 15/15
   */
  public void displayStats() {
    System.out.printf("%s | %s%n", 
      this.attacker.getStats(), this.defender.getStats()
    );
  }

  /** public */
  
  /**
   * Initiates the battle loop. 
   * Attacker and defender exchange attacks. The health of the opponent 
   * is checked after each attack and loop is halted immediately 
   * and a winner is declared.
   */
  public void begin() {
    // ?: need to check health of attacker/defender before beginning?
    // ?: not in current brief
    combat : while (this.inCombat) {
      this.displayStats();

      boolean attackSuccess = this.attacker.attacks(this.defender, true);
      if (attackSuccess) { 
        this.setWinner(this.attacker);
        break combat; 
      }

      boolean counterSuccess = this.defender.attacks(this.attacker, true);
      if (counterSuccess) { 
        this.setWinner(this.defender);
        break combat; 
      }

      System.out.println();
    }

    if (this.winner != null) {
      System.out.printf("%s wins!%n", this.winner.getName());
    }
  }


}
