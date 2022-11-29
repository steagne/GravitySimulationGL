package objects;

public class Point
{
	public double x;
	public double y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public Point() { this(0, 0); }

	public double getX() { return x; }
	public double getY() { return y; }

	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	
	public double distanceFromPoint(Point point)
	{
		double dx, dy;
		
		dx = point.x - x;
		dy = point.y - y;
		
		return (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));
	}
	public Point clone() { return new Point(x, y); }
	
	public static Point fromPoint(java.awt.Point point) { return new Point(point.getX(), point.getY()); }
}