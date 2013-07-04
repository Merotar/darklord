package darklord.ui;

import org.lwjgl.opengl.GL11;

import darklord.collectables.Collectable;
import darklord.collectables.CrystalBlueCollectable;
import darklord.collectables.CrystalGreenCollectable;
import darklord.collectables.CrystalRedCollectable;
import darklord.collectables.CrystalYellowCollectable;
import darklord.collectables.OrbBlueCollectable;
import darklord.collectables.OrbGreenCollectable;
import darklord.collectables.OrbYellowCollectable;
import darklord.collectables.UncoverMapCollectable;
import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.math.Vector2f;
import darklord.media.SpriteSheet;
import darklord.media.TextureRegion;

public class DevUICollectables extends UI
{
	private UISelectionListType<ButtonCollectable> buildOptions;

	public DevUICollectables(SpriteSheet s, float theRatio)
	{
		super(s, theRatio);
		
        setPosition(new Vector2f(0.f, 0.f));
        setSize(new Vector2f(1.f, 1.f));
		
		buildOptions = new UISelectionListType<ButtonCollectable>();
	}
	
	public void update(Vector2f mousePos, GameEngine world)
	{
		buildOptions.update(world);
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button)
	{
		buildOptions.mouseDownReaction(globalToLocal(globalPos), button);
	}
	
	public Collectable getActiveCollectable()
	{
		if (buildOptions.getActiveElement() != null ) return buildOptions.getActiveElement().getCollectable();
		return null;
	}
	
	public void draw()
	{
		super.draw();
		GL11.glPushMatrix();
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		buildOptions.draw(Darklord.textures);
		GL11.glPopMatrix();
	}
	
	public void init(GameEngine world)
	{
        UIObject panelLeft = new UIObject(new TextureRegion(0.f, 0.f, 3*128, 6*128), "panel left");
        panelLeft.setSize(new Vector2f(0.25f, 2.f));
        panelLeft.setPosition(new Vector2f(0.f, 0.f));
        addUIObject(panelLeft);
        
        ButtonCollectable buttonCollectableCrystalRed = new ButtonCollectable(new TextureRegion(4*128, 3*128, 128, 128), "collectable_crystal_red", new CrystalRedCollectable());
        buildOptions.addUIObject(buttonCollectableCrystalRed, aspectRatio);
        
        ButtonCollectable buttonCollectableCrystalBLue = new ButtonCollectable(new TextureRegion(5*128, 3*128, 128, 128), "collectable_crystal_blue", new CrystalBlueCollectable());
        buildOptions.addUIObject(buttonCollectableCrystalBLue, aspectRatio);
        
        ButtonCollectable buttonCollectableCrystalGreen = new ButtonCollectable(new TextureRegion(3*128, 3*128, 128, 128), "collectable_crystal_green", new CrystalGreenCollectable());
        buildOptions.addUIObject(buttonCollectableCrystalGreen, aspectRatio);
        
        ButtonCollectable buttonCollectableCrystalYellow = new ButtonCollectable(new TextureRegion(2*128, 3*128, 128, 128), "collectable_crystal_yellow", new CrystalYellowCollectable());
        buildOptions.addUIObject(buttonCollectableCrystalYellow, aspectRatio);
        
        ButtonCollectable buttonCollectableOrbBlue = new ButtonCollectable(new TextureRegion(1*128, 4*128, 128, 128), "collectable_orb_blue", new OrbBlueCollectable());
        buildOptions.addUIObject(buttonCollectableOrbBlue, aspectRatio);
        
        ButtonCollectable buttonCollectableOrbGreen = new ButtonCollectable(new TextureRegion(2*128, 4*128, 128, 128), "collectable_orb_green", new OrbGreenCollectable());
        buildOptions.addUIObject(buttonCollectableOrbGreen, aspectRatio);
        
        ButtonCollectable buttonCollectableOrbYellow = new ButtonCollectable(new TextureRegion(3*128, 4*128, 128, 128), "collectable_orb_yellow", new OrbYellowCollectable());
        buildOptions.addUIObject(buttonCollectableOrbYellow, aspectRatio);
        
        ButtonCollectable buttonCollectableUncoverMap = new ButtonCollectable(new TextureRegion(7*128, 4*128, 128, 128), "collectable_uncover_map", new UncoverMapCollectable());
        buildOptions.addUIObject(buttonCollectableUncoverMap, aspectRatio);
	}
}
