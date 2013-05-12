package game;

import java.io.Serializable;

/**
 * basic description of an 2D float vector
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Vector2f implements Serializable
{
	float x,y;

	public Vector2f(){
		x = 0;
		y = 0;
	}
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f orig){
		x = orig.x;
		y = orig.y;
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
	
//	public void add(Vector2f v){
//		this.x += v.getX();
//		this.y += v.getY();
//	}
	
	public void addX(float x){
		this.x += x;
	}
	
	public void addY(float y){
		this.y += y;
	}
	
	public Vector2f sub(Vector2f v)
	{
		return new Vector2f(this.getX()-v.getX(), this.getY()-v.getY());
	}
	
	public Vector2f add(Vector2f v)
	{
		return new Vector2f(this.getX()+v.getX(), this.getY()+v.getY());
	}
	
	public Vector2f mul(float v)
	{
		return new Vector2f(this.getX()*v, this.getY()*v);
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

	public void set(int x2, int y2)
	{
		x = x2;
		y = y2;
	}

	public void set(float f, float g)
	{
		x = f;
		y = g;
	}

	public void normalize()
	{
		float length = (float) Math.sqrt(x*x+y*y);
		x /= length;
		y /= length;
	}
	
}