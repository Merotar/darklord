package darklord.ui;
import darklord.math.Vector2f;

import java.util.Iterator;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.game.Player;
import darklord.media.SpriteSheet;
import darklord.media.StaticText;
import darklord.media.TextureRegion;

/**
 * description of the specific darklord ingame ui
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class IngameUIStatus extends UI
{
	UIBar energyBarRed;
	UIBar energyBarBlue;
	UIBar energyBarGreen;
	UIBar energyBarYellow;
	Vector<UIObject> uiObjectsIngame;
	StaticText<Integer> numCrystalsRed, numCrystalsBlue, numCrystalsGreen,
		numCrystalsYellow, numPlayerHealth, numPlayerXp, numPlayerLevel;
	Vector<StaticText<Integer>> staticTextsInt;
	
	public IngameUIStatus(SpriteSheet s, float theRatio)
	{
		super(s, theRatio);
        setPosition(new Vector2f(0.f, 0.f));
        setSize(new Vector2f(1.f, 1.f));
        
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
	
	public void init(GameEngine world)
	{        
        UIObject panelLeft = new UIObject(new TextureRegion(0.f, 0.f, 3*128, 6*128), "panel left");
        panelLeft.setSize(new Vector2f(0.25f, 2.f));
        panelLeft.setPosition(new Vector2f(0.f, 0.f));
        addUIObject(panelLeft);
        
        UIBar energyBarRed = new UIBar(new TextureRegion(0.f, 6*128+0*32, 2*128, 32), "energy bar red");
        energyBarRed.setSize(new Vector2f(0.8f*0.25f, .02f*aspectRatio));
        energyBarRed.setPosition(new Vector2f(0.1f*0.25f, 0.35f));
        setEnergyBarRed(energyBarRed);
        
        UIBar energyBarBlue = new UIBar(new TextureRegion(0.f, 6*128+1*32, 2*128, 32), "energy bar red");
        energyBarBlue.setSize(new Vector2f(0.8f*0.25f, .02f*aspectRatio));
        energyBarBlue.setPosition(new Vector2f(0.1f*0.25f, 0.3f));
        setEnergyBarBlue(energyBarBlue);
        
        UIBar energyBarGreen = new UIBar(new TextureRegion(0.f, 6*128+2*32, 2*128, 32), "energy bar red");
        energyBarGreen.setSize(new Vector2f(0.8f*0.25f, .02f*aspectRatio));
        energyBarGreen.setPosition(new Vector2f(0.1f*0.25f, 0.25f));
        setEnergyBarGreen(energyBarGreen);
        
        UIBar energyBarYellow = new UIBar(new TextureRegion(0.f, 6*128+3*32, 2*128, 32), "energy bar red");
        energyBarYellow.setSize(new Vector2f(0.8f*0.25f, .02f*aspectRatio));
        energyBarYellow.setPosition(new Vector2f(0.1f*0.25f, 0.2f));
        setEnergyBarYellow(energyBarYellow);
        
        // add UIObjects
        
        UIObject crystalRed = new UIObject(new TextureRegion(4*128, 3*128, 128, 128), "crystal red");
        crystalRed.setSize(new Vector2f(0.05f, .05f*aspectRatio));
        crystalRed.setPosition(new Vector2f(0.01f, .45f));
        addUIObjectIngame(crystalRed);
        
        UIObject crystalBlue = new UIObject(new TextureRegion(5*128, 3*128, 128, 128), "crystal red");
        crystalBlue.setSize(new Vector2f(0.05f, .05f*aspectRatio));
        crystalBlue.setPosition(new Vector2f(0.07f, .45f));
        addUIObjectIngame(crystalBlue);
        
        UIObject crystalGreen = new UIObject(new TextureRegion(3*128, 3*128, 128, 128), "crystal red");
        crystalGreen.setSize(new Vector2f(0.05f, .05f*aspectRatio));
        crystalGreen.setPosition(new Vector2f(0.13f, .45f));
        addUIObjectIngame(crystalGreen);
        
        UIObject crystalYellow = new UIObject(new TextureRegion(2*128, 3*128, 128, 128), "crystal red");
        crystalYellow.setSize(new Vector2f(0.05f, .05f*aspectRatio));
        crystalYellow.setPosition(new Vector2f(0.19f, .45f));
        addUIObjectIngame(crystalYellow);
        
        // add texts
        
        StaticText<Integer> numCrystalsRed = new StaticText<Integer>("$", world.mainPlayer.getCrystalsRed());
        numCrystalsRed.setSize(new Vector2f(0.05f, 0.05f*aspectRatio));
        numCrystalsRed.setPosition(new Vector2f(0.033f, 0.455f));
        setNumCrystalsRed(numCrystalsRed);
        
        StaticText<Integer> numCrystalsBlue = new StaticText<Integer>("$", world.mainPlayer.getCrystalsRed());
        numCrystalsBlue.setSize(new Vector2f(0.05f, 0.05f*aspectRatio));
        numCrystalsBlue.setPosition(new Vector2f(0.091f, 0.455f));
        setNumCrystalsBlue(numCrystalsBlue);
        
        StaticText<Integer> numCrystalsGreen = new StaticText<Integer>("$", world.mainPlayer.getCrystalsRed());
        numCrystalsGreen.setSize(new Vector2f(0.05f, 0.05f*aspectRatio));
        numCrystalsGreen.setPosition(new Vector2f(0.148f, 0.455f));
        setNumCrystalsGreen(numCrystalsGreen);
        
        StaticText<Integer> numCrystalsYellow = new StaticText<Integer>("$", world.mainPlayer.getCrystalsRed());
        numCrystalsYellow.setSize(new Vector2f(0.05f, 0.05f*aspectRatio));
        numCrystalsYellow.setPosition(new Vector2f(0.205f, 0.455f));
        setNumCrystalsYellow(numCrystalsYellow);
        
        StaticText<Integer> numPlayerHealth = new StaticText<Integer>("$HP", (int)world.mainPlayer.getHp());
        numPlayerHealth.setSize(new Vector2f(0.05f, 0.05f*aspectRatio));
        numPlayerHealth.setPosition(new Vector2f(0.125f, 0.1f));
        setNumPlayerHealth(numPlayerHealth);
        
        StaticText<Integer> numPlayerXp = new StaticText<Integer>("$XP", world.mainPlayer.getXp());
        numPlayerXp.setSize(new Vector2f(0.05f, 0.05f*aspectRatio));
        numPlayerXp.setPosition(new Vector2f(0.125f, 0.8f));
        setNumPlayerXp(numPlayerXp);
        
        StaticText<Integer> numPlayerLevel = new StaticText<Integer>("LEVEL:$", world.mainPlayer.getLevel());
        numPlayerLevel.setSize(new Vector2f(0.05f, 0.05f*aspectRatio));
        numPlayerLevel.setPosition(new Vector2f(0.125f, 0.7f));
        setNumPlayerLevel(numPlayerLevel);
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
		
		Darklord.sprites01.begin();
		for (Iterator<UIObject> object = uiObjectsIngame.iterator();object.hasNext();)
		{
			UIObject tmp = object.next();
			
			Darklord.sprites01.draw(tmp.getTextureRegion(), 
						getPosition().getX()+tmp.getPosition().getX()*getSize().getX(), 
						getPosition().getY()+tmp.getPosition().getY()*getSize().getY(), 
						tmp.getSize().getX()*getSize().getX(), 
						tmp.getSize().getY()*getSize().getY());

		}
		Darklord.sprites01.end();
		
		// draw texts
		
		for (StaticText<Integer> tmpText : staticTextsInt)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(getPosition().getX(), getPosition().getY(), 0.f);
			GL11.glScalef(getSize().getX(), getSize().getY(), 1.f);
			tmpText.draw(Darklord.textDrawer);
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(getPosition().getX(), getPosition().getY(), 0.f);
		GL11.glScalef(getSize().getX(), getSize().getY(), 1.f);
		numCrystalsRed.draw(Darklord.textDrawer);
		numCrystalsBlue.draw(Darklord.textDrawer);
		numCrystalsGreen.draw(Darklord.textDrawer);
		numCrystalsYellow.draw(Darklord.textDrawer);
		numPlayerHealth.draw(Darklord.textDrawer);
		numPlayerXp.draw(Darklord.textDrawer);
		numPlayerLevel.draw(Darklord.textDrawer);
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
	
	public void update(GameEngine world)
	{
		Player player = world.mainPlayer;
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
