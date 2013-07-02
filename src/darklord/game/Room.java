package darklord.game;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import darklord.enemies.Enemy;
import darklord.rules.Condition;
import darklord.rules.Reaction;
import darklord.rules.Rule;


/**
 * describes the architecture of an level with blocks and enemies
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Room implements Serializable
{
	private Block[][] theGrid;
	private Vector<Enemy> enemies;
	public int gridSizeX, gridSizeY;
//	private float[][] fogMap;
	private int fogDensity, posX, posY;
	public Vector<Collectable> collectableObjects;
	public Vector<Chest> chests;
	private Room gridTop, gridBottom, gridLeft, gridRight;
	private int levelRequirement;
	public Vector<Rule> rules;
	private String name;
	
	public Room()
	{
		this(10, 10);
	}
	
	public Room(int x, int y, int thePosX, int thePosY, BlockType t)
	{
		gridSizeX = x;
		gridSizeY = y;
		
		posX = thePosX;
		posY = thePosY;
		
		gridTop = gridBottom = gridLeft = gridRight = null;
		
		theGrid = new Block[gridSizeX][gridSizeY];
		rules = new Vector<Rule>();
		
		this.setFogDensity(1);
//		fogMap = new float[getGridSizeX()*getFogDensity()][getGridSizeY()*getFogDensity()];
//		for (int i=0;i<getGridSizeX()*getFogDensity();i++)
//		{
//			for (int j=0;j<getGridSizeY()*getFogDensity();j++)
//			{
//				fogMap[i][j] = 1.f;
//			}
//		}
		
		enemies = new Vector<Enemy>();
		collectableObjects = new Vector<Collectable>();
		chests = new Vector<Chest>();
		
		initDefault(t);
		name ="defaultRoom";
//		rules = readRulesFromTextFile(name+"Rules.txt");
	}
	
	public Room(int x, int y)
	{
		this(x, y, 0, 0, BlockType.BLOCK_NONE);
	}
	
	public void addCollectable(Collectable theCollectable)
	{
		collectableObjects.add(theCollectable);
	}
	
	public void addChest(Chest theChest)
	{
		chests.add(theChest);
	}
	
	public void addRule(Rule theRule)
	{
		rules.add(theRule);
	}
	
	public void addEnemy(Enemy theEnemy)
	{
		enemies.add(theEnemy);
	}
	
	private void initDefault(BlockType t)
	{
		for (int i=0;i<gridSizeX;i++)
		{
			for (int j=0; j<gridSizeY;j++)
			{
				if ((i==0) || (j==0) || (i==gridSizeX-1) || (j==gridSizeY-1))
				{
					theGrid[i][j] = new Block(BlockType.BLOCK_ROCK);
				} else // inner level
				{
					theGrid[i][j] = new Block(t);
				}
				if (i == 0 && j == gridSizeY/2) theGrid[i][j].setType(BlockType.BLOCK_NONE);
				if (i == gridSizeX-1 && j == gridSizeY/2) theGrid[i][j].setType(BlockType.BLOCK_NONE);
				if (i == gridSizeX/2 && j == 0) theGrid[i][j].setType(BlockType.BLOCK_NONE);
				if (i == gridSizeX/2 && j == gridSizeY-1) theGrid[i][j].setType(BlockType.BLOCK_NONE);
			}
		}
	}
	
	public Block getBlockAt(int x, int y)
	{
		return theGrid[x][y];
	}
	
//	private void initTest()
//	{
//		Random rnd = new Random();
//		
//		for (int i=0;i<gridSizeX;i++)
//		{
//			for (int j=0;j<gridSizeY;j++)
//			{
//				theGrid[i][j] = new Block(BlockType.BLOCK_DIRT);
//			}
//		}
//		
//		for (int i=0;i<gridSizeX;i++)
//		{
//			for (int j=0;j<gridSizeY;j++)
//			{
////				grid[i][j] = new Block();
//				if ((i==0) || (j==0) || (i==gridSizeX-1) || (j==gridSizeY-1))
//				{
//					theGrid[i][j].setType(BlockType.BLOCK_ROCK);
//				} else // inner level
//				{
//					if (rnd.nextGaussian() < 0.5)
//					{
//						theGrid[i][j].setType(BlockType.BLOCK_DIRT);
//					}
//					if (rnd.nextGaussian() > 1.0)
//					{
//						int rndType = (int)Math.floor((1.f*rnd.nextInt()/Integer.MAX_VALUE+1)*0.5*3+3);
////						System.out.println(rndType);
//						theGrid[i][j].setType(BlockType(rndType));
//						if (rnd.nextGaussian() > 0.3)
//						{
//							if ((i+1)<gridSizeX-1)
//							{
////								System.out.println(i+1+ ", "+j);
//								theGrid[i+1][j].setType(rndType);
//							}
//						}
//					}
//				}
//			}
//		}
//		theGrid[1][4].setType(0);
//		theGrid[1][3].setType(0);
//		theGrid[2][3].setType(0);
//		theGrid[7][7].setType(0);
////		enemies.add(new StaticEnemy(1.f, 4.f));
//		enemies.add(new EnemyRandomMove(1.f, 4.f));
//		enemies.add(new StaticEnemy(7.f, 7.f));
////		grid[(int)Math.round(mainPlayer.getPosX())][(int)Math.round(mainPlayer.getPosY())].setType(0);
//		
////		collectableObjects.add(new Collectable(CollectableType.ABILITY_TELEPORT, 7.25f, 3.25f));
////		collectableObjects.add(new Collectable(CollectableType.ABILITY_DIGGING, 6.25f, 2.25f));
//	}

	public boolean setTypeAt(int x_int, int y_int, BlockType t)
	{
		if (x_int > 0 && x_int < getGridSizeX() && y_int > 0 && y_int < getGridSizeY())
		{
			theGrid[x_int][y_int].setType(t);
			return true;
		}
		return false;
	}
	
	public void update(GameEngine engine, Player thePlayer)
	{	
		for (Rule tmpRule : rules)
		{
			tmpRule.apply(engine, thePlayer);
		}
	}

	public Vector<Enemy> getEnemies()
	{
		return this.enemies;
	}

	public int getGridSizeX() {
		return gridSizeX;
	}

	public void setGridSizeX(int gridSizeX) {
		this.gridSizeX = gridSizeX;
	}

	public int getGridSizeY() {
		return gridSizeY;
	}

	public void setGridSizeY(int gridSizeY) {
		this.gridSizeY = gridSizeY;
	}

	public int getFogDensity() {
		return fogDensity;
	}

	public void setFogDensity(int fogDensity)
	{
		this.fogDensity = fogDensity;
	}

	public float getFogAt(int x, int y)
	{
		if (x >= 0 && x < getGridSizeX()*getFogDensity() && y >= 0 && y < getGridSizeY()*getFogDensity())
		{
			return theGrid[x][y].getFogValue();
		}
		return 0.f;
	}

	public void setFogAt(int x, int y, float value)
	{
		if (value >= 1.f) value = 1.f;
		if (value < 0.f) value = 0.f;
		this.theGrid[x][y].setFogValue(value);
	}
	
	public void setBlockAt(int x, int y, Block theBlock)
	{
		theGrid[x][y] = theBlock;
	}
	
//	public void drawFog(int x, int y)
//	{
//		GL11.glBegin(GL11.GL_QUADS);
//		
//		float posX = 0.f;
//		float posY = 0.f;
//		float sizeX = 1.f/getFogDensity();
//		float sizeY = 1.f/getFogDensity();
//				
//		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x, y));
//
////		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x, y+1));
//		GL11.glVertex2f(posX, posY+sizeY);
////		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x+1, y+1));
//		GL11.glVertex2f(posX+sizeX, posY+sizeY);
////		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x+1, y));
//		GL11.glVertex2f(posX+sizeX, posY);
////		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x, y));
//		GL11.glVertex2f(posX, posY);
//		GL11.glEnd();
//	}

	public Vector<Collectable> getCollectableObjects() {
		return collectableObjects;
	}

	public void setCollectableObjects(Vector<Collectable> collectableObjects) {
		this.collectableObjects = collectableObjects;
	}

	public Vector<Chest> getChests() {
		return chests;
	}

	public void setChests(Vector<Chest> chests) {
		this.chests = chests;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setEnemies(Vector<Enemy> enemies) {
		this.enemies = enemies;
	}

	public Room getGridTop() {
		return gridTop;
	}

	public void setGridTop(Room gridTop) {
		this.gridTop = gridTop;
	}

	public Room getGridBottom() {
		return gridBottom;
	}

	public void setGridBottom(Room gridBottom) {
		this.gridBottom = gridBottom;
	}

	public Room getGridLeft() {
		return gridLeft;
	}

	public void setGridLeft(Room gridLeft) {
		this.gridLeft = gridLeft;
	}

	public Room getGridRight() {
		return gridRight;
	}

	public void setGridRight(Room gridRight) {
		this.gridRight = gridRight;
	}

	public int getLevelRequirement() {
		return levelRequirement;
	}

	public void setLevelRequirement(int levelRequirement) {
		this.levelRequirement = levelRequirement;
	}
	
//	public Vector<Rule> readRulesFromTextFile(String fileName)
//	{
//		Vector<Rule> theRules = new Vector<Rule>();
//		File file = new File(fileName);
//		try
//		{
//			Scanner s = new Scanner(file);
//			String line;
//			String[] words;
//
//			while (s.hasNextLine())
//			{
//                line = s.nextLine();
//                words = line.split("\\s+");
//
//            	if (words[0].equals(Parser.newRule))
//            	{
//            		Rule tmpRule = new Rule();
//            		
//        			while (s.hasNextLine())
//        			{
//                        line = s.nextLine();
//                        words = line.split("\\s+");
//                        
//                        // new condition
//                        if (words[0].equals(Parser.Condition))
//                        {
//                        	Condition tmpCondition = Parser.parseCondition(words);
//                        	if (tmpCondition != null) tmpRule.addCondition(tmpCondition);
//                        }
//                        
//                        // new reaction
//                        if (words[0].equals(Parser.Reaction))
//                        {
//                        	Reaction tmpReaction = Parser.parseReaction(words);
//                        	if (tmpReaction != null) tmpRule.addReaction(tmpReaction);
//                        }
//                        
//                        // finalize rule
//                        if (words[0].equals(Parser.endRule))
//                        {
//                        	theRules.add(tmpRule);
//                        	break;
//                        }
//        			}
//            	}
//			}
//		}  catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return theRules;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Rule> getRules() {
		return rules;
	}

	public void setRules(Vector<Rule> rules) {
		this.rules = rules;
	}
	
}