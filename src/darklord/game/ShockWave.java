package darklord.game;

import org.lwjgl.opengl.GL11;

import darklord.math.Vector2f;
import darklord.media.Sprite;

public class ShockWave extends Projectile
{
	private long id;
	private Vector2f basicSize;
	
	public ShockWave(Vector2f pos, Vector2f dir, int type)
	{
		super(pos, dir, new Vector2f(4.f, 1.f), type);
		((Sprite)appearance).setTextureRegion(0*128, 3*128+64, 256, 64);
		setDamage(0.5f);
		setRange(3.f);
		setEnergyCosts(1.f);
		basicSize = (new Vector2f(getSizeVec())).mul(0.5f);
		setAngle((float) (Math.atan2(dir.getY(), dir.getX())*180./Math.PI-90.f));
		id = System.nanoTime();
	}
	public void draw()
	{
//		Print.outln("rot sizeX: "+getRotatedSizeX());
		GL11.glPushMatrix();
		GL11.glTranslated(getPosX(), getPosY(), 0.);
		GL11.glTranslated(getSizeX()/2.f, getSizeY()/2.f, 0.);
		GL11.glRotated(getAngle(), 0., 0., 1.);
//		GL11.glTranslated(-getSizeX()/2.f, -getSizeX()/2.f, 0.);
//		GL11.glScaled(getSizeX(), getSizeY(), 1.);
		Darklord.textures.begin();
		appearance.draw(-getSizeX()/2.f, -getSizeY()/2.f, getSizeX(), getSizeY());
		Darklord.textures.end();
		GL11.glPopMatrix();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public void update(float dt)
	{
		super.update(dt);
//		setSizeVec(basicSize.mul((float)(1.f+time.getFraction())));
	}
}
