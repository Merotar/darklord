package darklord.ui;

import org.lwjgl.opengl.GL11;

import darklord.game.Block;
import darklord.game.BlockType;
import darklord.game.Buildable;
import darklord.game.Darklord;
import darklord.game.Enemy;
import darklord.game.EnemyRandomMove;
import darklord.game.GameEngine;
import darklord.game.StaticEnemyCrystal;
import darklord.math.Vector2f;
import darklord.media.SpriteSheet;
import darklord.media.TextureRegion;

public class DevUIEnemies extends UI
{
	private UISelectionListType<ButtonEnemy> buildOptions;

	public DevUIEnemies(SpriteSheet s, float theRatio)
	{
		super(s, theRatio);
		
        setPosition(new Vector2f(0.f, 0.f));
        setSize(new Vector2f(1.f, 1.f));
		
		buildOptions = new UISelectionListType<ButtonEnemy>();
	}
	
	public void update(Vector2f mousePos, GameEngine world)
	{
		buildOptions.update(world);
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button)
	{
		buildOptions.mouseDownReaction(globalToLocal(globalPos), button);
	}
	
	public Enemy getActiveEnemy()
	{
		if (buildOptions.getActiveElement() != null ) return buildOptions.getActiveElement().getEnemy();
		return null;
	}
	
	public void draw()
	{
		super.draw();
		GL11.glPushMatrix();
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		buildOptions.draw(Darklord.chars);
		GL11.glPopMatrix();
	}
	
	public void init(GameEngine world)
	{
        UIObject panelLeft = new UIObject(new TextureRegion(0.f, 0.f, 3*128, 6*128), "panel left");
        panelLeft.setSize(new Vector2f(0.25f, 2.f));
        panelLeft.setPosition(new Vector2f(0.f, 0.f));
        addUIObject(panelLeft);
        
        ButtonEnemy buttonBuildEnemyRandomMove = new ButtonEnemy(new TextureRegion(0*128, 1*128, 128, 128), "enemy_random_move", new EnemyRandomMove());
        buildOptions.addUIObject(buttonBuildEnemyRandomMove, aspectRatio);
        
        ButtonEnemy buttonBuildStaticEnemyCrystal = new ButtonEnemy(new TextureRegion(0*128, 2*128, 128, 128), "enemy_static_crystal", new StaticEnemyCrystal());
        buildOptions.addUIObject(buttonBuildStaticEnemyCrystal, aspectRatio);
        
        
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
