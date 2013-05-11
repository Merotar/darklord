abstract class Collidable
{
	private float posX, posY, sizeX, sizeY;
	
	public abstract boolean collide(Collidable obj);

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getSizeX() {
		return sizeX;
	}

	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}
}