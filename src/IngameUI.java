import org.lwjgl.opengl.GL11;


public class IngameUI
{
	private Vector2f position, size;
	private IngameUIStatus statusUI;
	private IngameUIBuild buildUI;
	private UI activeUI;
	private SpriteSheet uiSpriteSheet;
	private CountdownTimer clickTimer;

	public IngameUI(float theRatio)
	{
		position = new Vector2f(-1.f, -1.f);
		size = new Vector2f(2.f, 2.f);
		clickTimer = new CountdownTimer(0.2f);
		uiSpriteSheet = new SpriteSheet("./img/ui.png");
		statusUI = new IngameUIStatus(uiSpriteSheet, theRatio);
		buildUI = new IngameUIBuild(uiSpriteSheet, theRatio);
		activeUI = statusUI;
	}
	
	public void init(Level world)
	{
		statusUI.init(world);
		buildUI.init(world);
	}
	
	public Vector2f globalToLocal(Vector2f globalPos)
	{
		Vector2f localPos = globalPos.sub(position);
		localPos.setX(localPos.getX()/getSize().getX());
		localPos.setY(localPos.getY()/getSize().getY());
		
		return localPos;
	}
	
	public String getActiveBuildable()
	{
		return buildUI.getActiveBuildable();
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button)
	{
		if (clickTimer.isDone())
		{
			activeUI.mouseDownReaction(globalToLocal(globalPos), button);
			clickTimer.reset();
		}
	}
	
	public void update(Level world, float dt)
	{
		activeUI.update(world);
		clickTimer.update(dt);
	}
	
	public void draw()
	{
		GL11.glPushMatrix();
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		activeUI.draw();
		GL11.glPopMatrix();
	}
	
	public void switchActiveUI()
	{
		if (activeUI instanceof IngameUIStatus)
		{
			activeUI = buildUI;
		} else
		{
			if (activeUI instanceof IngameUIBuild) activeUI = statusUI;
		}
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}
}
