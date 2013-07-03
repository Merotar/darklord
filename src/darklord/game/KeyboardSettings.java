package darklord.game;

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
	int KEY_1, KEY_2, KEY_3, KEY_4, KEY_8, KEY_W, KEY_A, KEY_S, KEY_D, KEY_ESCAPE, KEY_PERIOD,
	KEY_COMMA, KEY_F1, KEY_F2, KEY_F5, KEY_Q, KEY_E, KEY_LSHIFT, KEY_C, KEY_LCONTROL,
	KEY_N, KEY_O, KEY_R, KEY_TAB, KEY_NUMPAD2, KEY_NUMPAD4, KEY_NUMPAD6, KEY_NUMPAD8;
	
	public KeyboardSettings()
	{
		KEY_1 = Keyboard.KEY_1;
		KEY_2 = Keyboard.KEY_2;
		KEY_3 = Keyboard.KEY_3;
		KEY_4 = Keyboard.KEY_4;
		KEY_NUMPAD2 = Keyboard.KEY_NUMPAD2;
		KEY_NUMPAD4 = Keyboard.KEY_NUMPAD4;
		KEY_NUMPAD6 = Keyboard.KEY_NUMPAD6;
		KEY_NUMPAD8 = Keyboard.KEY_NUMPAD8;
		KEY_W = Keyboard.KEY_W;
		KEY_S = Keyboard.KEY_S;
		KEY_A = Keyboard.KEY_A;
		KEY_D = Keyboard.KEY_D;
		KEY_ESCAPE = Keyboard.KEY_ESCAPE;
		KEY_PERIOD = Keyboard.KEY_PERIOD;
		KEY_COMMA = Keyboard.KEY_COMMA;
		KEY_F1 = Keyboard.KEY_F1;
		KEY_F2 = Keyboard.KEY_F2;
		KEY_F5 = Keyboard.KEY_F5;
		KEY_Q = Keyboard.KEY_Q;
		KEY_E = Keyboard.KEY_E;
		KEY_N = Keyboard.KEY_N;
		KEY_O = Keyboard.KEY_O;
		KEY_R = Keyboard.KEY_R;
		KEY_LSHIFT = Keyboard.KEY_LSHIFT;
		KEY_C = Keyboard.KEY_C;
		KEY_LCONTROL = Keyboard.KEY_LCONTROL;
		KEY_TAB = Keyboard.KEY_TAB;
	}
	
}