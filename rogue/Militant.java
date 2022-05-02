/**
 * Minimum functionality for entities that can engage in fights. 
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 */
interface Militant {

  /**
   * Sets the maximum health value 
   * @param  maxHealth 
   */
  public void setMaxHealth(int maxHealth);

  /**
   * Restores the health value back to maximum 
   */
  public void restoreHealth();

  /**
   * Sets the damage value that can be applied to others 
   * @param  damage 
   */
  public void setDamage(int damage);

  /**
   * Gets the previously set damage value 
   * @return  damage value as int 
   */
  public int getDamage();

  /**
   * Attacks another Fightable object 
   */
  public boolean attacks(Fightable foe);

  /**
   * Reduces the health value of self by applying the specified damage value 
   * @param  damage 
   */
  public int receiveDamage(int damage); 

}