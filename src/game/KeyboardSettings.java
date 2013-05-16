package game;
import org.lwjgl.input.Keyboard;

/**
 * class to map keys
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class KeyboardSettings
{
	int KEY_1, KEY_2, KEY_3, KEY_4, KEY_W, KEY_A, KEY_S, KEY_D, KEY_ESCAPE, KEY_PERIOD,
	KEY_COMMA, KEY_F1, KEY_Q, KEY_E, KEY_LSHIFT, KEY_C, KEY_LCONTROL;
	
	public KeyboardSettings()
	{
		KEY_1 = Keyboard.KEY_LSHIFT; //Keyboard.KEY_1;
		KEY_2 = Keyboard.KEY_2;
		KEY_3 = Keyboard.KEY_3;
		KEY_4 = Keyboard.KEY_4;
		KEY_W = Keyboard.KEY_W;
		KEY_S = Keyboard.KEY_S;
		KEY_A = Keyboard.KEY_A;
		KEY_D = Keyboard.KEY_D;
		KEY_ESCAPE = Keyboard.KEY_ESCAPE;
		KEY_PERIOD = Keyboard.KEY_PERIOD;
		KEY_COMMA = Keyboard.KEY_COMMA;
		KEY_F1 = Keyboard.KEY_F1;
		KEY_Q = Keyboard.KEY_Q;
		KEY_E = Keyboard.KEY_E;
		KEY_LSHIFT = Keyboard.KEY_LSHIFT;
		KEY_C = Keyboard.KEY_C;
		KEY_LCONTROL = Keyboard.KEY_LCONTROL;
	}
	
}