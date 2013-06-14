package darklord.ui;


/**
 * describes the ingame status of the game
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 16-05-2013
 * 
 */
public enum IngameStatus
{
	DEFAULT(0), BUILDING(1);

	int id;
	
	IngameStatus(int theId)
	{
		id = theId;
	}
}