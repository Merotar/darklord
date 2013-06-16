package darklord.ui;


import java.util.Iterator;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import darklord.game.GameEngine;
import darklord.media.SpriteSheet;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

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
	float aspectRatio;
	
	public UI(String fileName)
	{
		spriteSheet = new SpriteSheet(fileName);
		buttons = new Vector<Button>();
		UIObjects = new Vector<UIObject>();
	}
	
	public UI(SpriteSheet s, float theRatio)
	{
		spriteSheet = s;
		aspectRatio = theRatio;
		buttons = new Vector<Button>();
		UIObjects = new Vector<UIObject>();
	}
	
	public UI()
	{
		this("darklord/img/main_menu.png");
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
		GL11.glPushMatrix();
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		spriteSheet.begin();
		if (background != null) spriteSheet.draw(background);
		for (Iterator<Button> object = buttons.iterator();object.hasNext();)
		{
			Button tmpButton = object.next();
			spriteSheet.draw(tmpButton.getTextureRegion(), 
					tmpButton.getPosition().getX(), 
					tmpButton.getPosition().getY(), 
					tmpButton.getSize().getX(), 
					tmpButton.getSize().getY());
		}
		
		for (Iterator<UIObject> object = UIObjects.iterator();object.hasNext();)
		{
			UIObject tmpUIObject = object.next();
			spriteSheet.draw(tmpUIObject.getTextureRegion(), 
					tmpUIObject.getPosition().getX(), 
					tmpUIObject.getPosition().getY(), 
					tmpUIObject.getSize().getX(), 
					tmpUIObject.getSize().getY());
		}
		spriteSheet.end();
		GL11.glPopMatrix();
	}

	public void update(GameEngine world)
	{
		
	}

	public void mouseDownReaction(Vector2f globalPos, int button)
	{
		
	}
	
	public Vector2f globalToLocal(Vector2f globalPos)
	{
		Vector2f localPos = globalPos.sub(getPosition());
		localPos.setX(localPos.getX()/getSize().getX());
		localPos.setY(localPos.getY()/getSize().getY());
		
		return localPos;
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

	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
}