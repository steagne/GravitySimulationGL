package events;

import objects.Point;
import org.lwjgl.glfw.*;

public class CursorPos extends GLFWCursorPosCallback
{
	private static double x;
	private static double y;
	
	@Override
	public void invoke(long window, double x, double y)
	{
		CursorPos.x = x;
		CursorPos.y = y;
	}

	public static double getX() { return x; }
	public static double getY() { return y; }
	public static Point getPos() { return new Point(x, y); }
}