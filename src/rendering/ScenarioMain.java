package rendering;

import events.CursorEnter;
import events.CursorPos;
import events.MouseButtons;
import objects.Entity;
import objects.Point;
import objects.Textures;

import static org.lwjgl.opengl.GL11.*;

public class ScenarioMain extends Scenario
{
	public static final double 	ENTITY_DEFAULT_MASS = Math.pow(10, 6);
	public static final double 	ENTITY_DEFAULT_DIAMETER = 10;
	public static final double	VECTOR_SCALE_FACTOR = Math.pow(5, -2);
	public static final Entity DEFAULT_ENTITY = new Entity(ENTITY_DEFAULT_MASS, ENTITY_DEFAULT_DIAMETER, new Point());
	
	private boolean showCursor;
	private Entity newEntityCursor;
	private boolean creatingNewEntity;
	
	public ScenarioMain(Model model, Textures textures)
	{
		this.model = model;
		this.textures = textures;
		newEntityCursor = DEFAULT_ENTITY.clone();
	}
	
	public void render()
	{
		int nVertex, i;
		double th, dth, r;
		Point location;
		
		location = null;
		if (creatingNewEntity)
		{
			location = newEntityCursor.getLocation();
			newEntityCursor.getCurrentVector().x = (CursorPos.getX() - location.x) * VECTOR_SCALE_FACTOR;
			newEntityCursor.getCurrentVector().y = (CursorPos.getY() - location.y) * VECTOR_SCALE_FACTOR;
		}
		if (showCursor)
		{
			if (location == null)
				location = CursorPos.getPos();
			th = 0;
			r = newEntityCursor.getRadius();
			nVertex = (4 * (int)newEntityCursor.getDiameter());
			dth = (double)360 / nVertex;
			glBegin(GL_POINTS);
			for (i = 0; i < nVertex; i++)
			{
				th += dth;
				glVertex2d(location.x + r * Math.cos(th), location.y + r * Math.sin(th));
			}
			glEnd();
		}
		for (Entity entity : model.getEntities())
		{
			location = entity.getLocation();
			th = 0;
			r = entity.getRadius();
			nVertex = (4 * (int)entity.getDiameter());
			dth = (double)360 / nVertex;
			glBegin(GL_POINTS);
			for (i = 0; i < nVertex; i++)
			{
				th = (double)i * 360 / nVertex;
				glVertex2d(location.x + r * Math.cos(th), location.y + r * Math.sin(th));
			}
			glEnd();
		}
	}
	public void processCommands()
	{
		showCursor = CursorEnter.isEntered();
		if (MouseButtons.isLeftButtonDown() && !creatingNewEntity)
		{
			creatingNewEntity = true;
			newEntityCursor.setLocation(CursorPos.getPos());
		}
		if (creatingNewEntity && MouseButtons.isRightButtonDown())
			creatingNewEntity = false;
		if (creatingNewEntity && !MouseButtons.isLeftButtonDown())
		{
			creatingNewEntity = false;
			model.addEntity(newEntityCursor);
			newEntityCursor = DEFAULT_ENTITY.clone();
		}
	}
	public void updateModel()
	{
		model.calculateInteractions();
		model.updateEntities();
		model.checkCollisions();
	}
}