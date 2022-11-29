package rendering;

import objects.Textures;

public abstract class Scenario
{
	protected Textures textures;
	protected Model model;

	public abstract void render();
	public abstract void processCommands();
	public abstract void updateModel();
}