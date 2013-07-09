package darklord.ui;

import org.lwjgl.opengl.GL11;

import darklord.blocks.Block;
import darklord.enemies.BounceEnemy;
import darklord.enemies.ChasingBlockEnemy;
import darklord.enemies.Enemy;
import darklord.enemies.EnemyRandomMove;
import darklord.enemies.StaticEnemyCrystal;
import darklord.enemies.StaticEnemyOneShot;
import darklord.game.BlockType;
import darklord.game.Buildable;
import darklord.game.Darklord;
import darklord.game.GameEngine;
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
        UIObject panelLeft = new UIObject(new TextureRegion(0, 0, 3*128, 6*128), "panel left");
        panelLeft.setSize(new Vector2f(0.25f, 2.f));
        panelLeft.setPosition(new Vector2f(0.f, 0.f));
        addUIObject(panelLeft);
        
        ButtonEnemy buttonBuildEnemyRandomMove = new ButtonEnemy(new TextureRegion(0*128, 1*128, 128, 128), "enemy_random_move", new EnemyRandomMove());
        buildOptions.addUIObject(buttonBuildEnemyRandomMove, aspectRatio);
        
        ButtonEnemy buttonBuildStaticEnemyCrystal = new ButtonEnemy(new TextureRegion(0*128, 2*128, 128, 128), "enemy_static_crystal", new StaticEnemyCrystal());
        buildOptions.addUIObject(buttonBuildStaticEnemyCrystal, aspectRatio);
        
        ButtonEnemy buttonBuildStaticEnemyOneShot = new ButtonEnemy(new TextureRegion(0*128, 3*128, 128, 128), "enemy_static_one_shot", new StaticEnemyOneShot());
        buildOptions.addUIObject(buttonBuildStaticEnemyOneShot, aspectRatio);
        
        ButtonEnemy buttonBuildChasingBlockEnemy = new ButtonEnemy(new TextureRegion(3*128, 4*128, 128, 128), "enemy_chasing_block", new ChasingBlockEnemy());
        buildOptions.addUIObject(buttonBuildChasingBlockEnemy, aspectRatio);
        
        ButtonEnemy buttonBuildBounceEnemy = new ButtonEnemy(new TextureRegion(0*128, 5*128, 128, 128), "enemy_bounce", new BounceEnemy());
        buildOptions.addUIObject(buttonBuildBounceEnemy, aspectRatio);
        
	}
}
