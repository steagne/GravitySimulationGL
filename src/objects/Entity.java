package objects;

public class Entity
{
	protected double mass;
	protected double radius;
	protected double diameter;
	protected double volume;
	protected double density;
	protected Point location;
	protected Vector currentVector;
	protected Vector nextVector;
	
	public Entity(double mass, double diameter, Point location, Vector startingVector)
	{
		this.mass = mass;
		this.diameter = diameter;
		volume = diameter * Math.PI;
		radius = diameter / 2;
		density = mass / volume;
		this.location = location;
		currentVector = startingVector;
		nextVector = new Vector();
	}
	public Entity(double mass, double diameter, Point location) { this(mass, diameter, location, new Vector()); }

	public double getMass() { return mass; }
	public double getRadius() { return radius; }
	public double getDiameter() { return diameter; }
	public double getVolume() { return volume; }
	public double getDensity() { return density; }
	public Point getLocation() { return location; }
	public Vector getCurrentVector() { return currentVector; }
	public Vector getNextVector() {	return nextVector; }
	public void setMass(double mass)
	{
		this.mass = mass;
		density = mass / volume;
	}
	public void setVolume(double volume)
	{
		this.volume = volume;
		diameter = volume / Math.PI;
		radius = diameter / 2;
		density = mass / volume;
	}
	public void setDiameter(double diameter)
	{
		this.diameter = diameter;
		volume = diameter * Math.PI;
		radius = diameter / 2;
		density = mass / volume;
	}
	public void setLocation(Point location) { this.location = location;	}
	public void setCurrentVector(Vector currentVector) { this.currentVector = currentVector; }
	public void setNextVector(Vector nextVector) { this.nextVector = nextVector; }

	public double getDistanceFromEntity(Entity entity)
	{
		return (location.distanceFromPoint(entity.location) - radius - entity.radius);
	}
	public void collide(Entity entity)
	{
		currentVector.x = (mass * currentVector.x + entity.mass * entity.currentVector.x) / (mass + entity.mass);
		currentVector.y = (mass * currentVector.y + entity.mass * entity.currentVector.y) / (mass + entity.mass);
		setDiameter(diameter + entity.diameter);
		if (radius > entity.radius)
			location = entity.location;
		else if (radius == entity.radius)
		{
			location.x = (location.x + entity.location.x) / 2;
			location.y = (location.y + entity.location.y) / 2;
		}
		mass += entity.mass;
	}
	public void update()
	{
		currentVector.add(nextVector);
		location.x += currentVector.x;
		location.y += currentVector.y;
	}
	public Entity clone()
	{
		Entity clone;
		
		clone = new Entity(mass, diameter, location);
		
		return clone;
	}
}