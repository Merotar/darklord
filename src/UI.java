

import java.util.Iterator;
import java.util.Vector;

/**
 * representation of an ui
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 16-05-2013
 * 
 */

public class UI
{
	Vector2f position, size;
	SpriteSheet spriteSheet;
	TextureRegion background;
	Vector<Button> buttons;
	Vector<UIObject> UIObjects;
	
	public UI(String fileName)
	{
		spriteSheet = new SpriteSheet(fileName);
		buttons = new Vector<Button>();
		UIObjects = new Vector<UIObject>();
	}
	
	public UI()
	{
		this("./img/main_menu.png");
	}
	
	public void setBackground(TextureRegion t)
	{
		background = t;
	}
	
	public void addButton(Button theButton)
	{
		buttons.add(theButton);
	}
	
	public void addUIObject(UIObject theUIObject)
	{
		UIObjects.add(theUIObject);
	}
	
	public void draw()
	{
		spriteSheet.begin();
		if (background != null) spriteSheet.draw(background, getPosition().getX(), getPosition().getY(), getSize().getX(), getSize().getY());
		for (Iterator<Button> object = buttons.iterator();object.hasNext();)
		{
			Button tmpButton = object.next();
			spriteSheet.draw(tmpButton.getTextureRegion(), 
					getPosition().getX()+tmpButton.getPosition().getX()*getSize().getX(), 
					getPosition().getY()+tmpButton.getPosition().getY()*getSize().getY(), 
					tmpButton.getSize().getX()*getSize().getX(), 
					tmpButton.getSize().getY()*getSize().getY());
		}
		
		for (Iterator<UIObject> object = UIObjects.iterator();object.hasNext();)
		{
			UIObject tmpUIObject = object.next();
			spriteSheet.draw(tmpUIObject.getTextureRegion(), 
					getPosition().getX()+tmpUIObject.getPosition().getX()*getSize().getX(), 
					getPosition().getY()+tmpUIObject.getPosition().getY()*getSize().getY(), 
					tmpUIObject.getSize().getX()*getSize().getX(), 
					tmpUIObject.getSize().getY()*getSize().getY());
		}
		spriteSheet.end();
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public Vector<Button> getButtons() {
		return buttons;
	}

	public void setButtons(Vector<Button> buttons) {
		this.buttons = buttons;
	}
}