
//*****************************************************************************
// A simple 2 dimensional vector with some vectorfunctions
//*****************************************************************************

public class Vector2f{
	float x,y;

	public Vector2f(){
		x = 0;
		y = 0;
	}
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	
	public boolean compareTo(Vector2f v){
		if ((x == v.getX()) && (y == v.getY())){
			return true;
		} else {
			return false;
		}
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void add(Vector2f v){
		this.x += v.getX();
		this.y += v.getY();
	}
	
	public void addX(float x){
		this.x += x;
	}
	
	public void addY(float y){
		this.y += y;
	}
	
	public void round()
	{
		x = Math.round(x);
		y = Math.round(y);
	}
	
	public void print()
	{
		System.out.println("("+x+"; "+y+")");
	}
	
	public static Vector2f add(Vector2f a, Vector2f b){
		return new Vector2f(a.getX()+b.getX(),a.getY()+b.getY());
	}
	
}