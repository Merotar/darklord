package darklord.game;
import org.lwjgl.opengl.GL11;

import darklord.media.TextureRegion;


public class Wall extends Buildable
{
	static final int WALL_TOP = 0;
	static final int WALL_BOTTOM = 1;
	static final int WALL_RIGHT = 2;
	static final int WALL_LEFT = 3;
	
	private boolean top, bottom, left, right;
	
	public Wall()
	{
		top = bottom = left = right = false;
		setTextureRegion(new TextureRegion(4*128, 4*128, 128, 128));
	}
	
	public void draw()
	{
		if (top)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(0.5, 0.5, 0.);
			GL11.glRotated(180, 0., 0., 1.);
			GL11.glTranslated(-0.5, -0.5, 0.);
			Darklord.textures.draw(getTextureRegion());
			GL11.glPopMatrix();
		}
		if (right)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(0.5, 0.5, 0.);
			GL11.glRotated(90, 0., 0., 1.);
			GL11.glTranslated(-0.5, -0.5, 0.);
			Darklord.textures.draw(getTextureRegion());
			GL11.glPopMatrix();
		}
		if (left)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(0.5, 0.5, 0.);
			GL11.glRotated(270, 0., 0., 1.);
			GL11.glTranslated(-0.5, -0.5, 0.);
			Darklord.textures.draw(getTextureRegion());
			GL11.glPopMatrix();
		}
		if (bottom)
		{
			GL11.glPushMatrix();
			Darklord.textures.draw(getTextureRegion());
			GL11.glPopMatrix();
		}
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
}
