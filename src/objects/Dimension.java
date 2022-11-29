package objects;

public class Dimension
{
	public double x;
	public double y;
	
	public Dimension(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public Dimension() { this(0, 0); }

	public double getX() { return x; }
	public double getY() { return y; }

	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	
	public void scaleByFactor(double factor)
	{
		x *= factor;
		y *= factor;
	}
	public Dimension clone() { return new Dimension(x, y); }
}