package game;

/**
 * describes the status of the game
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 16-05-2013
 * 
 */
public enum GameStatus
{
	MAIN_MENU(0), INGAME(1);

	int id;
	
	GameStatus(int theId)
	{
		id = theId;
	}
}