package darklord.ui;

import darklord.game.Print;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

/**
 * description of a general UI object
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 30-05-2013
 * 
 */

public class UIObject {
		private Vector2f position, size;
		private TextureRegion texture;
		String name;
		boolean down;
		
		public UIObject(TextureRegion region, String theName)
		{
			setTextureRegion(region);
			setName(theName);
		}
		
		public UIObject()
		{
			down = false;
		}

		/**
		 * 
		 * @param thePosition position in the menu coordinate system
		 * @return true if button is hit
		 */
		public boolean isInside(Vector2f thePosition)
		{
			boolean inside = true;
			
			if (thePosition.getX()>getPosition().getX()+getSize().getX()) inside = false;
			if (thePosition.getX()<getPosition().getX()) inside = false;
			if (thePosition.getY()>getPosition().getY()+getSize().getY()) inside = false;
			if (thePosition.getY()<getPosition().getY()) inside = false;
//			if (inside) Print.outln("inside of button "+getName());
			
			return inside;
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

		public TextureRegion getTextureRegion() {
			return texture;
		}

		public void setTextureRegion(TextureRegion textureRegion) {
			this.texture = textureRegion;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	
		public void print()
		{
			Print.outln(getName());
		}
}
