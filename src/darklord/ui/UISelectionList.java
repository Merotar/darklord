package darklord.ui;
import java.util.Iterator;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import darklord.game.GameEngine;
import darklord.media.SpriteSheet;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

public class UISelectionList
{
	private Vector2f position, size;
	private Vector<Button> elements;
	private TextureRegion selection;
	private int selectedObject;

	public UISelectionList()
	{
		position = new Vector2f();
		size = new Vector2f(1.f, 1.f);
		elements = new Vector<Button>();
		selection = new TextureRegion(0*128, 7*128, 128, 128);
		selectedObject = -1;
	}
	
	public void addUIObject(Button theObject)
	{
		elements.add(theObject);
	}
	
	public void select(Vector2f mousePos)
	{
		for (Button tmp: elements)
		{
			if (tmp.isInside(mousePos))
			{
				selectedObject = elements.indexOf(tmp);
			}
		}
	}
	
	public Vector2f globalToLocal(Vector2f globalPos)
	{
		Vector2f localPos = globalPos.sub(getPosition());
		localPos.setX(localPos.getX()/getSize().getX());
		localPos.setY(localPos.getY()/getSize().getY());
		
		return localPos;
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button)
	{
		Vector2f localPos = globalToLocal(globalPos);
		
		for (Button tmpButton : elements)
		{
			if (tmpButton.isInside(localPos))
			{
				int index = elements.indexOf(tmpButton);
				
				if (index == selectedObject)
				{
					selectedObject = -1;
				} else
				{
					selectedObject = index;
				}
			}
		}
	}
	
	public String getActiveBuildable()
	{
		if (selectedObject != -1)
		{
			return elements.get(selectedObject).getName();
		}
		return null;
	}
	
	public void draw(SpriteSheet spriteSheet)
	{
		GL11.glPushMatrix();
		spriteSheet.begin();
		
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		
		// draw elements
		for (Button tmp: elements)
		{
			spriteSheet.draw(tmp.getTextureRegion(), 
					tmp.getPosition().getX(), 
					tmp.getPosition().getY(), 
					tmp.getSize().getX(), 
					tmp.getSize().getY());
		}
		
		// draw selection
		if (selectedObject >= 0)
		{
			Button tmpObject = elements.get(selectedObject);
			spriteSheet.draw(selection, 
					tmpObject.getPosition().getX(), 
					tmpObject.getPosition().getY(), 
					tmpObject.getSize().getX(), 
					tmpObject.getSize().getY());
		}

		
		spriteSheet.end();
		GL11.glPopMatrix();
	}
	
	public void update(GameEngine world)
	{
//		for (Button tmpButton : elements)
//		{
//			if (tmpButton.isInside(mousePos))
//			{
//				if (tmpButton.getName().equals("build_wall"))
//				{
//					Print.outln("build wall clicked!");
////					world.ingameStatus
//				}
//			}
//		}
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
}
