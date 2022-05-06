public abstract class Entity {

  private int posX;
  private int posY; 

  public Entity() {}

  /** setters */ 

  /**
   * @param  x - x coordinate as int
   */
  public void setX(int x) {
    this.posX = x;
  }

  /**
   * @param  y - y coordinate as int
   */
  public void setY(int y) {
    this.posY = y;
  }

  /** getters */

  /**
   * @return  x coordinate as int
   */
  public int getX() {
    return this.posX;
  }

  /**
   * @return  y coordinate as int
   */
  public int getY() {
    return this.posY;
  }

  /** public */

  /**
   * Checks whether this entity has collided with another entity 
   * @return  true | false 
   */
  public boolean checkCollision(Entity otherEntity) {
    return (
      this.posX == otherEntity.getX() && this.posY == otherEntity.getY()
    );
  }

  /** abstract */

  /**
   * @return  a marker used to render in the world map
   */
  public abstract char getMapMarker();

  /** override */

  public String toString() {
    return String.valueOf(this.getMapMarker());
  }

}
