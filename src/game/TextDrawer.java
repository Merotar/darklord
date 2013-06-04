package game;

import java.util.HashMap;
import java.util.Vector;

public class TextDrawer
{
	private SpriteSheet sprites;
	private HashMap<Character, TextureRegion> map;
	private float size;
	
	public TextDrawer()
	{
		sprites = new SpriteSheet("./img/font.png");
		size = 0.8f;
		map = new HashMap<Character, TextureRegion>();
		map.put('0', new TextureRegion(0*32, 0, 32, 64));
		map.put('1', new TextureRegion(1*32, 0, 32, 64));
		map.put('2', new TextureRegion(2*32, 0, 32, 64));
		map.put('3', new TextureRegion(3*32, 0, 32, 64));
		map.put('4', new TextureRegion(4*32, 0, 32, 64));
		map.put('5', new TextureRegion(5*32, 0, 32, 64));
		map.put('6', new TextureRegion(6*32, 0, 32, 64));
		map.put('7', new TextureRegion(7*32, 0, 32, 64));
		map.put('8', new TextureRegion(8*32, 0, 32, 64));
		map.put('9', new TextureRegion(9*32, 0, 32, 64));
		map.put(',', new TextureRegion(10*32, 0, 32, 64));
		map.put('.', new TextureRegion(11*32, 0, 32, 64));
		map.put('P', new TextureRegion(27*32, 0, 32, 64));
		map.put('X', new TextureRegion(3*32, 1*64, 32, 64));
		map.put('+', new TextureRegion(6*32, 1*64, 32, 64));
	}

	private void drawChar(Character c, float posX, float posY)
	{
		sprites.draw(map.get(c), posX, posY, 0.5f*size, size);
	}
	
	public void draw(String s, Vector2f pos)
	{
		sprites.begin();
		
		for (int i=0;i<s.length();i++)
		{
			drawChar(s.charAt(i), pos.getX() + i*0.5f*size, pos.getY());
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
}
