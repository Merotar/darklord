package darklord.ui;

import java.util.Vector;

import org.lwjgl.opengl.GL11;

import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.game.Print;
import darklord.math.Vector2f;
import darklord.media.SpriteSheet;
import darklord.media.TextureRegion;

public class UISelectionListType<T extends ButtonBlock>
{
	private Vector2f position, size;
	private Vector<T> elements;
	private TextureRegion selection;
	private int selectedObject;

	public UISelectionListType()
	{
		position = new Vector2f();
		size = new Vector2f(1.f, 1.f);
		elements = new Vector<T>();
		selection = new TextureRegion(0*128, 7*128, 128, 128);
		selectedObject = -1;
	}
	
	public void addUIObject(T theObject, float aspectRatio)
	{
		int posX = elements.size() % 3;
		int posY = elements.size() / 3;
		
        theObject.setSize(new Vector2f(.05f, .05f*aspectRatio));
        theObject.setPosition(new Vector2f(0.025f+posX*(0.05f+0.025f), .8f-posY*.1f));
		elements.add(theObject);
	}
	
	public void select(Vector2f mousePos)
	{
		for (T tmp: elements)
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
		
		for (T tmpButton : elements)
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
	
	public T getActiveBuildable()
	{
		if (selectedObject != -1)
		{
			return elements.get(selectedObject);
		}
		return null;
	}
	
	public void draw(SpriteSheet spriteSheet)
	{
		GL11.glPushMatrix();

		
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		
		Darklord.sprites01.begin();
		// draw elements
		for (T tmp: elements)
		{
			Darklord.sprites01.draw(tmp.getTextureRegion(), 
					tmp.getPosition().getX(), 
					tmp.getPosition().getY(), 
					tmp.getSize().getX(), 
					tmp.getSize().getY());
		}
		Darklord.sprites01.end();
		
		spriteSheet.begin();
		
		// draw selection
		if (selectedObject >= 0)
		{
			T tmpObject = elements.get(selectedObject);
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
//			for (Button tmpButton : elements)
//			{
//				if (tmpButton.isInside(mousePos))
//				{
//					if (tmpButton.getName().equals("build_wall"))
//					{
//						Print.outln("build wall clicked!");
////						world.ingameStatus
//					}
//				}
//			}
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
