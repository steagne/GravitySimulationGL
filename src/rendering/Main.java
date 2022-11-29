package rendering;

import events.*;
import objects.Textures;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;

public class Main
{
	public static final int WND_W_DEFAULT = 1280;
	public static final int WND_H_DEFAULT = 720;
	
	private boolean active;
	private long window;
	private int wndW;
	private int wndH;
	//CALLBACKS
	private GLFWMouseButtonCallback inputMouseButtons;
	private GLFWScrollCallback inputScroll;
	private GLFWCursorPosCallback inputCursorPos;
	private GLFWCursorEnterCallback inputCursorEnter;
	private GLFWKeyCallback inputKeyboard;
	
	private Model model;
	private Textures textures;
	private ScenarioMain scenarioMain;
	private Scenario[] switchScenario;
	
	public Main()
	{
		try
		{
			//GLFW INITIALIZATION
			if (!glfwInit())
				throw new Exception("Unable to start GLFW");

			//WINDOW INITIALIZATION
			wndW = WND_W_DEFAULT;
			wndH = WND_H_DEFAULT;
			window = glfwCreateWindow(wndW, wndH, "Gravity Simulation", 0, 0);
			if (window == 0)
				throw new Exception("Unable to create window");
			
			active = true;
			//COMMANDS
			//glfwSetKeyCallback(window, input = new events.Keyboard());
			glfwSetMouseButtonCallback(window, inputMouseButtons = new MouseButtons());
			glfwSetScrollCallback(window, inputScroll = new Scroll());
			glfwSetCursorPosCallback(window, inputCursorPos = new CursorPos());
			glfwSetCursorEnterCallback(window, inputCursorEnter = new CursorEnter());
			glfwSetKeyCallback(window, inputKeyboard = new Keyboard());
			//RESIZE CALLBACK
			glfwSetFramebufferSizeCallback(window, (window, width, height) ->
			{
				wndW = width;
				wndH = height;
				glViewport(0, 0, width, height);
			});
			//CENTERED WINDOW
			try (MemoryStack stack = stackPush())
			{
				IntBuffer pWidth = stack.mallocInt(1);
				IntBuffer pHeight = stack.mallocInt(1);
				glfwGetWindowSize(window, pWidth, pHeight);
				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
				glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
				);
			}
			glfwMakeContextCurrent(window);
			glfwSwapInterval(1); //V-SYNC
			glfwShowWindow(window);
			GL.createCapabilities(); //OPENGL-GLFW INTERACTION
			glEnable(GL_TEXTURE_2D);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, WND_W_DEFAULT, WND_H_DEFAULT, 0, 1, -1);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			model = new Model();
			textures = new Textures();
			scenarioMain = new ScenarioMain(model, textures);
			switchScenario = new Scenario[] { scenarioMain };
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public int getWindowWidth() { return wndW; }
	public int getWindowHeight() { return wndH; }
	public void loop()
	{
		while (active)
		{
			glfwPollEvents();
			glClearColor(0, 0, 0, 0);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			switchScenario[model.getState()].processCommands();
			switchScenario[model.getState()].render();
			switchScenario[model.getState()].updateModel();
			glfwSwapBuffers(window);
			if (glfwWindowShouldClose(window))
			{
				active = false;
				glfwSetWindowShouldClose(window, !active);
			}
		}
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	//MAIN
	public static void main(String[] args)
	{
		Main application;
		
		application = new Main();
		application.loop();
	}
}