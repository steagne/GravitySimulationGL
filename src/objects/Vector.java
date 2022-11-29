package objects;

public class Vector
{
	public double x;
	public double y;
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public Vector() { this(0, 0); }

	public double getX() { return x; }
	public double getY() { return y; }

	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	
	public double mod() { return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); }
	public void add(Vector vector)
	{
		x += vector.x;
		y += vector.y;
	}
	public Vector clone() { return new Vector(x, y); }
	public String toString()
	{
		return super.toString() + " (" + x + "; " + y + ")";
	}
}