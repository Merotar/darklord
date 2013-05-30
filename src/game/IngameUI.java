package game;

public class IngameUI extends UI
{
	UIBar energyBarRed;
	UIBar energyBarBlue;
	UIBar energyBarGreen;
	UIBar energyBarYellow;
	
	public IngameUI(String string)
	{
		super(string);
	}

	public UIBar getEnergyBarRed() {
		return energyBarRed;
	}

	public void setEnergyBarRed(UIBar energyBarRed) {
		this.energyBarRed = energyBarRed;
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
	}
}
