

import java.util.HashMap;
import java.util.Vector;

/**
 * loads and draws the graphics of a text
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class TextDrawer
{
	private SpriteSheet sprites;
	private HashMap<Character, TextureRegion> map;
	private float size;
	private int charSizeX, charSizeY;
	private float ratio;
	
	public TextDrawer(String fileName)
	{
		sprites = new SpriteSheet(fileName);
		size = 0.8f;
		
		charSizeX = 38;
		charSizeY = 54;
		ratio = 1.f * charSizeX /charSizeY;
		
		map = new HashMap<Character, TextureRegion>();
		map.put('0', new TextureRegion(0*charSizeX, 0, charSizeX, charSizeY));
		map.put('1', new TextureRegion(1*charSizeX, 0, charSizeX, charSizeY));
		map.put('2', new TextureRegion(2*charSizeX, 0, charSizeX, charSizeY));
		map.put('3', new TextureRegion(3*charSizeX, 0, charSizeX, charSizeY));
		map.put('4', new TextureRegion(4*charSizeX, 0, charSizeX, charSizeY));
		map.put('5', new TextureRegion(5*charSizeX, 0, charSizeX, charSizeY));
		map.put('6', new TextureRegion(6*charSizeX, 0, charSizeX, charSizeY));
		map.put('7', new TextureRegion(7*charSizeX, 0, charSizeX, charSizeY));
		map.put('8', new TextureRegion(8*charSizeX, 0, charSizeX, charSizeY));
		map.put('9', new TextureRegion(9*charSizeX, 0, charSizeX, charSizeY));
		map.put(',', new TextureRegion(10*charSizeX, 0, charSizeX, charSizeY));
		map.put('.', new TextureRegion(11*charSizeX, 0, charSizeX, charSizeY));

		map.put('A', new TextureRegion(12*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('B', new TextureRegion(13*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('C', new TextureRegion(14*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('D', new TextureRegion(15*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('E', new TextureRegion(16*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('F', new TextureRegion(17*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('G', new TextureRegion(18*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('H', new TextureRegion(19*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('I', new TextureRegion(20*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('J', new TextureRegion(21*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('K', new TextureRegion(22*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('L', new TextureRegion(23*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('M', new TextureRegion(24*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('N', new TextureRegion(25*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('O', new TextureRegion(26*charSizeX, 0*charSizeY, charSizeX, charSizeY));
		map.put('P', new TextureRegion(0*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('Q', new TextureRegion(1*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('R', new TextureRegion(2*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('S', new TextureRegion(3*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('U', new TextureRegion(4*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('V', new TextureRegion(6*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('W', new TextureRegion(7*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('X', new TextureRegion(8*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('Y', new TextureRegion(9*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('Z', new TextureRegion(10*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('+', new TextureRegion(11*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put(' ', new TextureRegion(12*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put(':', new TextureRegion(13*charSizeX, 1*charSizeY, charSizeX, charSizeY));
		map.put('-', new TextureRegion(14*charSizeX, 1*charSizeY, charSizeX, charSizeY));

	}

	private void drawChar(Character c, float posX, float posY)
	{
		sprites.draw(map.get(c), posX, posY, getRatio()*size, size);
	}
	
	public void draw(String s, Vector2f pos)
	{
		sprites.begin();
		
		for (int i=0;i<s.length();i++)
		{
			drawChar(s.charAt(i), pos.getX() + i*getRatio()*size-s.length()*size*getRatio()/2.f, pos.getY()-0.5f*size);
		}
		sprites.end();
	}
	
	public void draw(String s)
	{
		draw(s, new Vector2f());
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public int getCharSizeX() {
		return charSizeX;
	}

	public void setCharSizeX(int charSizeX) {
		this.charSizeX = charSizeX;
		if (charSizeY != 0) setRatio(1.f * charSizeX /charSizeY);
	}

	public int getCharSizeY() {
		return charSizeY;
	}

	public void setCharSizeY(int charSizeY) {
		this.charSizeY = charSizeY;
		if (charSizeY != 0) setRatio(1.f * charSizeX /charSizeY);
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}
}
