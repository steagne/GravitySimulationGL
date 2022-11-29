package events;

import org.lwjgl.glfw.*;

public class MouseButtons extends GLFWMouseButtonCallback
{
	private static boolean[] buttonDown = new boolean[3];
	
	@Override
	public void invoke(long window, int button, int action, int mods)
	{
		buttonDown[button] = action == 1;
	}

	public static boolean isLeftButtonDown() { return buttonDown[0]; }
	public static boolean isRightButtonDown() { return buttonDown[1]; }
	public static boolean isMidButtonDown() { return buttonDown[2]; }
}