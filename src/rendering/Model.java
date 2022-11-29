package rendering;

import objects.Dimension;
import objects.Entity;
import objects.Point;
import objects.Vector;

import java.util.*;

public class Model
{
	//UNIT: METERS!!!
	public static final double    	UA = 1.496 * Math.pow(10, 11);
	public static final Point DEFAULT_SPACE_LOCATION = new Point(0, 0); 			//CENTER OF THE UNIVERSE
	public static final Dimension DEFAULT_SPACE_REGION = new Dimension(80 * UA, 80 * UA * ((double)Main.WND_H_DEFAULT) / Main.WND_W_DEFAULT);
	public static final double  	GRAVITATIONAL_CONSTANT = 6.67408 * Math.pow(10, -11);
	public static final double  	TIME_SCALE_FACTOR = Math.pow(10, 6);

	private int state;
	private Point location;
	private Dimension region;
	private ArrayList<Entity> entities;
	private Stack<Integer> removeList;
	
	public Model()
	{
		state = 0;
		location = DEFAULT_SPACE_LOCATION.clone();
		region = DEFAULT_SPACE_REGION.clone();
		entities = new ArrayList<>();
		removeList = new Stack<>();
		addEntity(new objects.Entity(70000, 10, new Point(150, 150), new Vector(2, 0)));
		addEntity(new objects.Entity(20000, 40, new Point(300, 300), new Vector(-0.5, -1)));
		//addEntity(new objects.Entity(Double.MAX_VALUE, 1, new objects.Point(320, 240)));
	}

	public int getState() { return state; }
	public ArrayList<Entity> getEntities() { return entities; }
	public int getEntitiesCount() { return entities.size(); }
	
	public void calculateInteractions()
	{
		int i, j;
		Object[] entities;
		Entity entA, entB;
		double modAB, modBA, th, r;
		
		entities = this.entities.toArray();
		for (i = 0; i < entities.length; i++)
			for (j = 0; j < i; j++)
			{
				entA = (Entity)entities[i];
				entB = (Entity)entities[j];
				modAB = GRAVITATIONAL_CONSTANT * entA.getMass();
				modBA = GRAVITATIONAL_CONSTANT * entB.getMass();
				r = Math.pow(entA.getDistanceFromEntity(entB), 2);
				modAB /= r;
				modBA /= r;
				th = Math.atan((entB.getLocation().y - entA.getLocation().y) / (entB.getLocation().x - entA.getLocation().x)) + 
					(entB.getLocation().x - entA.getLocation().x < 0 ? Math.toRadians(180) : 0);
				entA.getCurrentVector().add(new Vector(modBA * Math.cos(th) * TIME_SCALE_FACTOR, modBA * Math.sin(th) * TIME_SCALE_FACTOR));
				entB.getCurrentVector().add(new Vector(-modAB * Math.cos(th) * TIME_SCALE_FACTOR, -modAB * Math.sin(th) * TIME_SCALE_FACTOR));
			}
	}
	public void updateEntities()
	{
		for (Entity entity : entities)
		{
			entity.update();
		}
	}
	public void checkCollisions()
	{
		int i, j;
		Object[] entities;
		Entity entA, entB;
		
		entities = this.entities.toArray();
		for (i = 0; i < entities.length; i++)
			for (j = 0; j < i; j++)
			{
				entA = (Entity)entities[i];
				entB = (Entity)entities[j];
				if (entA == null || entB == null) continue;
				if (entA.getDistanceFromEntity(entB) <= 0)
				{
					entA.collide(entB);
					entities[j] = null;
					removeList.push(j);
				}
			}
		while (!removeList.isEmpty())
			this.entities.remove((int)removeList.pop());
	}
	public void addEntity(Entity entity)
	{
		entities.add(entity);
	}
}