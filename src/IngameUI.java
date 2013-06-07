

import java.util.Iterator;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

/**
 * description of the specific darklord ingame ui
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class IngameUI extends UI
{
	UIBar energyBarRed;
	UIBar energyBarBlue;
	UIBar energyBarGreen;
	UIBar energyBarYellow;
	Vector<UIObject> uiObjectsIngame;
	StaticText<Integer> numCrystalsRed, numCrystalsBlue, numCrystalsGreen,
		numCrystalsYellow, numPlayerHealth, numPlayerXp, numPlayerLevel;
	Vector<StaticText<Integer>> staticTextsInt;
	
	public IngameUI(String string)
	{
		super(string);
		uiObjectsIngame = new Vector<UIObject>();
		staticTextsInt = new Vector<StaticText<Integer>>();
	}

	public UIBar getEnergyBarRed() {
		return energyBarRed;
	}

	public void setEnergyBarRed(UIBar energyBarRed) {
		this.energyBarRed = energyBarRed;
	}

	public void addUIObjectIngame(UIObject theObject)
	{
		uiObjectsIngame.add(theObject);
	}
	
	public void addStaticTextInt(StaticText<Integer> theText)
	{
//		theText.getPosition().setX(getPosition().getX()+theText.getPosition().getX()*getSize().getX());
//		theText.getPosition().setY(getPosition().getY()+theText.getPosition().getY()*getSize().getY());
//		theText.getSize().setX(theText.getSize().getX()*getSize().getX());
//		theText.getSize().setY(theText.getSize().getY()*getSize().getY());
		
		staticTextsInt.add(theText);
	}
	
	public void draw()
	{
		super.draw();
		
		spriteSheet.begin();
		// draw energy bars
		UIBar[] tmpUIBar = {energyBarRed, energyBarBlue, energyBarGreen, energyBarYellow};
		for (int i=0;i<tmpUIBar.length;i++)
		{
			if (tmpUIBar[i] != null)
			{
				TextureRegion tmpRegion= new TextureRegion(tmpUIBar[i].getTextureRegion());
				tmpRegion.setWidth(tmpRegion.getWidth()*tmpUIBar[i].getScale());
				spriteSheet.draw(tmpRegion, 
						getPosition().getX()+tmpUIBar[i].getPosition().getX()*getSize().getX(), 
						getPosition().getY()+tmpUIBar[i].getPosition().getY()*getSize().getY(), 
						tmpUIBar[i].getSize().getX()*getSize().getX()*tmpUIBar[i].getScale(), 
						tmpUIBar[i].getSize().getY()*getSize().getY());
			}
		}
		spriteSheet.end();
		
		// draw UIObjects
		
		Darklords.sprites01.begin();
		for (Iterator<UIObject> object = uiObjectsIngame.iterator();object.hasNext();)
		{
			UIObject tmp = object.next();
			
			Darklords.sprites01.draw(tmp.getTextureRegion(), 
						getPosition().getX()+tmp.getPosition().getX()*getSize().getX(), 
						getPosition().getY()+tmp.getPosition().getY()*getSize().getY(), 
						tmp.getSize().getX()*getSize().getX(), 
						tmp.getSize().getY()*getSize().getY());

		}
		Darklords.sprites01.end();
		
		// draw texts
		
		for (StaticText<Integer> tmpText : staticTextsInt)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(getPosition().getX(), getPosition().getY(), 0.f);
			GL11.glScalef(getSize().getX(), getSize().getY(), 1.f);
			tmpText.draw(Darklords.textDrawer);
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(getPosition().getX(), getPosition().getY(), 0.f);
		GL11.glScalef(getSize().getX(), getSize().getY(), 1.f);
		numCrystalsRed.draw(Darklords.textDrawer);
		numCrystalsBlue.draw(Darklords.textDrawer);
		numCrystalsGreen.draw(Darklords.textDrawer);
		numCrystalsYellow.draw(Darklords.textDrawer);
		numPlayerHealth.draw(Darklords.textDrawer);
		numPlayerXp.draw(Darklords.textDrawer);
		numPlayerLevel.draw(Darklords.textDrawer);
		GL11.glPopMatrix();
	}

	public UIBar getEnergyBarBlue() {
		return energyBarBlue;
	}

	public void setEnergyBarBlue(UIBar energyBarBlue) {
		this.energyBarBlue = energyBarBlue;
	}

	public UIBar getEnergyBarGreen() {
		return energyBarGreen;
	}

	public void setEnergyBarGreen(UIBar energyBarGreen) {
		this.energyBarGreen = energyBarGreen;
	}

	public UIBar getEnergyBarYellow() {
		return energyBarYellow;
	}

	public void setEnergyBarYellow(UIBar energyBarYellow) {
		this.energyBarYellow = energyBarYellow;
	}
	
	public void update(Player player)
	{
		// update energy bar lengths
		energyBarRed.setScale(1.f*player.getEnergyRed()/player.getEnergyRedMax());
		energyBarBlue.setScale(1.f*player.getEnergyBlue()/player.getEnergyBlueMax());
		energyBarGreen.setScale(1.f*player.getEnergyGreen()/player.getEnergyGreenMax());
		energyBarYellow.setScale(1.f*player.getEnergyYellow()/player.getEnergyYellowMax());
	
		// update crystal numbers
		
		numCrystalsRed.setValue(player.getCrystalsRed());
		numCrystalsBlue.setValue(player.getCrystalsBlue());
		numCrystalsGreen.setValue(player.getCrystalsGreen());
		numCrystalsYellow.setValue(player.getCrystalsYellow());
		
		// update players stats
		numPlayerHealth.setValue((int)player.getHp());
		numPlayerXp.setValue(player.getXp());
		numPlayerLevel.setValue(player.getLevel());
	}

	public StaticText<Integer> getNumCrystalsRed() {
		return numCrystalsRed;
	}

	public void setNumCrystalsRed(StaticText<Integer> numCrystalsRed) {
		this.numCrystalsRed = numCrystalsRed;
	}

	public StaticText<Integer> getNumCrystalsBlue() {
		return numCrystalsBlue;
	}

	public void setNumCrystalsBlue(StaticText<Integer> numCrystalsBlue) {
		this.numCrystalsBlue = numCrystalsBlue;
	}

	public StaticText<Integer> getNumCrystalsGreen() {
		return numCrystalsGreen;
	}

	public void setNumCrystalsGreen(StaticText<Integer> numCrystalsGreen) {
		this.numCrystalsGreen = numCrystalsGreen;
	}

	public StaticText<Integer> getNumCrystalsYellow() {
		return numCrystalsYellow;
	}

	public void setNumCrystalsYellow(StaticText<Integer> numCrystalsYellow) {
		this.numCrystalsYellow = numCrystalsYellow;
	}

	public StaticText<Integer> getNumPlayerHealth() {
		return numPlayerHealth;
	}

	public void setNumPlayerHealth(StaticText<Integer> playerHealthNum) {
		this.numPlayerHealth = playerHealthNum;
	}

	public StaticText<Integer> getNumPlayerXp() {
		return numPlayerXp;
	}

	public void setNumPlayerXp(StaticText<Integer> numPlayerXp) {
		this.numPlayerXp = numPlayerXp;
	}

	public StaticText<Integer> getNumPlayerLevel() {
		return numPlayerLevel;
	}

	public void setNumPlayerLevel(StaticText<Integer> numPlayerLevel) {
		this.numPlayerLevel = numPlayerLevel;
	}
}
