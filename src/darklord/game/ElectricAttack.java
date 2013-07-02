package darklord.game;
import java.io.Serializable;

import org.lwjgl.opengl.GL11;

import darklord.enemies.Enemy;
import darklord.game.Darklord;
import darklord.game.TimeStore;
import darklord.math.Vector2f;
import darklord.media.Animation;
import darklord.media.Drawable;
import darklord.media.Sprite;
import darklord.media.TextureRegion;


public class ElectricAttack implements Serializable
{
	Vector2f start, sizeEnd;
	Drawable appearance;
	boolean active;
	private float damagePerSecond;
	private Enemy targetEnemy;
	
	
	public ElectricAttack(Vector2f theStart, Enemy theEnemy)
	{
		targetEnemy = theEnemy;
		start = theStart;
		sizeEnd = new Vector2f(1.5f, 1.5f);
		active = true;
		damagePerSecond = 1.f;
		appearance = new Sprite();
		((Sprite)appearance).setTextureRegion(new TextureRegion(6*128, 4*128, 128, 128));
	}
	
	public void draw()
	{
		GL11.glPushMatrix();

		GL11.glTranslatef(targetEnemy.getCenter().getX(), targetEnemy.getCenter().getY(), 0.f);

		GL11.glScaled(sizeEnd.getX(), sizeEnd.getY(), 1.f);
		GL11.glTranslatef(-0.5f, -0.5f, 0.f);
//		Print.outln("draw!");
		Darklord.sprites01.begin();
		((Sprite)appearance).draw();
		Darklord.sprites01.end();
		GL11.glPopMatrix();
	}

	public boolean damageEnemy(float time)
	{
		return targetEnemy.decreaseHp(damagePerSecond*time);
	}
	
	public Vector2f getStart() {
		return start;
	}

	public void setStart(Vector2f start) {
		this.start = start;
	}

	public Vector2f getSizeEnd() {
		return sizeEnd;
	}

	public void setSizeEnd(Vector2f sizeEnd) {
		this.sizeEnd = sizeEnd;
	}

	public Drawable getAppearance() {
		return appearance;
	}

	public void setAppearance(Drawable appearance) {
		this.appearance = appearance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getDamagePerSecond() {
		return damagePerSecond;
	}

	public void setDamagePerSecond(float damagePerSecond) {
		this.damagePerSecond = damagePerSecond;
	}

	public Enemy getTargetEnemy() {
		return targetEnemy;
	}

	public void setTargetEnemy(Enemy targetEnemy) {
		this.targetEnemy = targetEnemy;
	}
}
