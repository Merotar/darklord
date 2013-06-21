package darklord.ui;

import org.lwjgl.opengl.GL11;

import darklord.game.Block;
import darklord.game.BlockType;
import darklord.game.Buildable;
import darklord.game.GameEngine;
import darklord.math.Vector2f;
import darklord.media.SpriteSheet;
import darklord.media.TextureRegion;

public class DevUIBlocks extends UI
{
	private UISelectionListType<ButtonBlock> buildOptions;

	public DevUIBlocks(SpriteSheet s, float theRatio)
	{
		super(s, theRatio);
		
        setPosition(new Vector2f(0.f, 0.f));
        setSize(new Vector2f(1.f, 1.f));
		
		buildOptions = new UISelectionListType<ButtonBlock>();
	}
	
	public void update(Vector2f mousePos, GameEngine world)
	{
		buildOptions.update(world);
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button)
	{
		buildOptions.mouseDownReaction(globalToLocal(globalPos), button);
	}
	
	public Buildable getActiveBuildable()
	{
		if (buildOptions.getActiveBuildable() != null ) return buildOptions.getActiveBuildable().getBlock();
		return null;
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
        UIObject panelLeft = new UIObject(new TextureRegion(0.f, 0.f, 3*128, 6*128), "panel left");
        panelLeft.setSize(new Vector2f(0.25f, 2.f));
        panelLeft.setPosition(new Vector2f(0.f, 0.f));
        addUIObject(panelLeft);
        
        ButtonBlock buttonBuildBlockNone = new ButtonBlock(new TextureRegion(0*128, 0*128, 128, 128), "build_block_none", new Block(BlockType.BLOCK_NONE));
//        buttonBuildBlockNone.setSize(new Vector2f(.05f, .05f*aspectRatio));
//        buttonBuildBlockNone.setPosition(new Vector2f(0.025f, .8f));
        buildOptions.addUIObject(buttonBuildBlockNone, aspectRatio);
        
        ButtonBlock buttonBuildBlockDirt = new ButtonBlock(new TextureRegion(1*128, 0*128, 128, 128), "build_block_rock", new Block(BlockType.BLOCK_ROCK));
        buildOptions.addUIObject(buttonBuildBlockDirt, aspectRatio);
        
        ButtonBlock buttonBuildBlockRock = new ButtonBlock(new TextureRegion(2*128, 0*128, 128, 128), "build_block_dirt", new Block(BlockType.BLOCK_DIRT));
        buildOptions.addUIObject(buttonBuildBlockRock, aspectRatio);
        
        
        ButtonBlock buttonBuildBlockRed = new ButtonBlock(new TextureRegion(3*128, 0*128, 128, 128), "build_block_red", new Block(BlockType.BLOCK_RED));
        buildOptions.addUIObject(buttonBuildBlockRed, aspectRatio);
        
        ButtonBlock buttonBuildBlockBlue = new ButtonBlock(new TextureRegion(4*128, 0*128, 128, 128), "build_block_blue", new Block(BlockType.BLOCK_BLUE));
        buildOptions.addUIObject(buttonBuildBlockBlue, aspectRatio);
        
        ButtonBlock buttonBuildBlockGreen = new ButtonBlock(new TextureRegion(5*128, 0*128, 128, 128), "build_block_green", new Block(BlockType.BLOCK_GREEN));
        buildOptions.addUIObject(buttonBuildBlockGreen, aspectRatio);
        
        
        ButtonBlock buttonBuildBlockYellow = new ButtonBlock(new TextureRegion(0*128, 1*128, 128, 128), "build_block_yellow", new Block(BlockType.BLOCK_YELLOW));
        buildOptions.addUIObject(buttonBuildBlockYellow, aspectRatio);
        
        ButtonBlock buttonBuildBlockWhite = new ButtonBlock(new TextureRegion(6*128, 0*128, 128, 128), "build_block_white", new Block(BlockType.BLOCK_WHITE));
        buildOptions.addUIObject(buttonBuildBlockWhite, aspectRatio);
        
        ButtonBlock buttonBuildBlockGlass = new ButtonBlock(new TextureRegion(6*128, 1*128, 128, 128), "build_block_glass", new Block(BlockType.BLOCK_GLASS));
        buildOptions.addUIObject(buttonBuildBlockGlass, aspectRatio);
        
//        Button buttonBuildWall = new Button(new TextureRegion(1*128, 7*128, 128, 128), "build_wall");
//        buttonBuildWall.setSize(new Vector2f(.05f, .05f*aspectRatio));
//        buttonBuildWall.setPosition(new Vector2f(0.025f, .8f));
//        buildOptions.addUIObject(buttonBuildWall);
//        
//        Button buttonBuildStoneFloor = new Button(new TextureRegion(1*128, 7*128, 128, 128), "build_floor_stone");
//        buttonBuildStoneFloor.setSize(new Vector2f(.05f, .05f*aspectRatio));
//        buttonBuildStoneFloor.setPosition(new Vector2f(0.025f+0.05f+0.025f, .8f));
//        buildOptions.addUIObject(buttonBuildStoneFloor);
//        
//        Button buttonBuildGlassBlock = new Button(new TextureRegion(1*128, 7*128, 128, 128), "build_block_glass");
//        buttonBuildGlassBlock.setSize(new Vector2f(.05f, .05f*aspectRatio));
//        buttonBuildGlassBlock.setPosition(new Vector2f(0.025f+2.f*(0.05f+0.025f), .8f));
//        buildOptions.addUIObject(buttonBuildGlassBlock);
//        
//        Button buttonBuildFloorCrystalRed = new Button(new TextureRegion(1*128, 7*128, 128, 128), "build_floor_crystal_red");
//        buttonBuildFloorCrystalRed.setSize(new Vector2f(.05f, .05f*aspectRatio));
//        buttonBuildFloorCrystalRed.setPosition(new Vector2f(0.025f, .8f-.05f*aspectRatio-0.025f));
//        buildOptions.addUIObject(buttonBuildFloorCrystalRed);
	}
}
