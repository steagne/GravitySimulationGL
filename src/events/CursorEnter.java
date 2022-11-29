package events;

import org.lwjgl.glfw.*;

public class CursorEnter extends GLFWCursorEnterCallback
{
	private static boolean entered;
	
	@Override
	public void invoke(long window, boolean entered)
	{
		CursorEnter.entered = entered;
	}
	
	public static boolean isEntered() { return entered; }
}