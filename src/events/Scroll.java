package events;

import org.lwjgl.glfw.*;

public class Scroll extends GLFWScrollCallback
{
	private static double amountHorizontal;
	private static double amountVertical;
	
	@Override
	public void invoke(long window, double dx, double dy)
	{
		amountHorizontal = dx;
		amountVertical = dy;
	}

	public static double getAmountHorizontal()
	{
		double amount;
		
		amount = Scroll.amountHorizontal;
		Scroll.amountHorizontal = 0;
		
		return amount;
	}
	public static double getAmountVertical()
	{
		double amount;
		
		amount = Scroll.amountVertical;
		Scroll.amountVertical = 0;
		
		return amount;
	}
}