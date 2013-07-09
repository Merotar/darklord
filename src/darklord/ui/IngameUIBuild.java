package darklord.ui;
import org.lwjgl.opengl.GL11;

import darklord.game.GameEngine;
import darklord.media.SpriteSheet;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

public class IngameUIBuild extends UI
{
	private UISelectionList buildOptions;

	public IngameUIBuild(SpriteSheet s, float theRatio)
	{
		super(s, theRatio);
		
        setPosition(new Vector2f(0.f, 0.f));
        setSize(new Vector2f(1.f, 1.f));
		
		buildOptions = new UISelectionList();
	}
	
	public void update(Vector2f mousePos, GameEngine world)
	{
		buildOptions.update(world);
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button)
	{
		buildOptions.mouseDownReaction(globalToLocal(globalPos), button);
	}
	
	public String getActiveBuildable()
	{
		return buildOptions.getActiveBuildable();
	}
	
	public void draw()
	{
		super.draw();
		GL11.glPushMatrix();
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		buildOptions.draw(spriteSheet);
		GL11.glPopMatrix();
	}
	
	public void init(GameEngine world)
	{       
        UIObject panelLeft = new UIObject(new TextureRegion(0, 0, 3*128, 6*128), "panel left");
        panelLeft.setSize(new Vector2f(0.25f, 2.f));
        panelLeft.setPosition(new Vector2f(0.f, 0.f));
        addUIObject(panelLeft);
        
        Button buttonBuildWall = new Button(new TextureRegion(1*128, 7*128, 128, 128), "build_wall");
        buttonBuildWall.setSize(new Vector2f(.05f, .05f*aspectRatio));
        buttonBuildWall.setPosition(new Vector2f(0.025f, .8f));
        buildOptions.addUIObject(buttonBuildWall);
        
        Button buttonBuildStoneFloor = new Button(new TextureRegion(2*128, 7*128, 128, 128), "build_floor_stone");
        buttonBuildStoneFloor.setSize(new Vector2f(.05f, .05f*aspectRatio));
        buttonBuildStoneFloor.setPosition(new Vector2f(0.025f+0.05f+0.025f, .8f));
        buildOptions.addUIObject(buttonBuildStoneFloor);
        
        Button buttonBuildGlassBlock = new Button(new TextureRegion(3*128, 7*128, 128, 128), "build_block_glass");
        buttonBuildGlassBlock.setSize(new Vector2f(.05f, .05f*aspectRatio));
        buttonBuildGlassBlock.setPosition(new Vector2f(0.025f+2.f*(0.05f+0.025f), .8f));
        buildOptions.addUIObject(buttonBuildGlassBlock);
        
        Button buttonBuildFloorCrystalRed = new Button(new TextureRegion(4*128, 7*128, 128, 128), "build_floor_crystal_red");
        buttonBuildFloorCrystalRed.setSize(new Vector2f(.05f, .05f*aspectRatio));
        buttonBuildFloorCrystalRed.setPosition(new Vector2f(0.025f, .8f-.05f*aspectRatio-0.025f));
        buildOptions.addUIObject(buttonBuildFloorCrystalRed);
	}
}
