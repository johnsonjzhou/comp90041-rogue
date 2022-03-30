/**
 * The Rogue game engine.
 * @author  Johnson Zhou 1302442 <zhoujj@student.unimelb.edu.au>
 *
 */
public class GameEngine {
	
	
	public static void main(String[] args) {
		
		// TODO: Some starter code has been provided below.
		// Edit this method as you find appropriate.
		
		// Creates an instance of the game engine.
		GameEngine gameEngine = new GameEngine();
		
		// Runs the main game loop.
		gameEngine.runGameLoop();
		
	}
	
	
	/*
	 *  Logic for running the main game loop.
	 */
	private void runGameLoop() {
		
		// Print out the title text.
		displayTitleText();
		
		// TODO: Implement your code here.
		
	}
	
	/*
	 *  Displays the title text.
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

	}

}
