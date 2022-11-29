package objects;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class Canvas
	extends JPanel
	implements MouseListener, MouseWheelListener
{
	private static final long serialVersionUID = 1L;
	private static final double DEFAULT_ENTITY_DIAMETER = 10;
	private static boolean FINISHED_DRAWING_PROCESS = true;
	
	private BufferedImage picture;
	
	//INTERACTION
	private boolean pressed;
	private boolean canceled;
	private Point pointPressed;
	private Entity generatedEntity;
	
	public Canvas()
	{
		super();

		addMouseListener(this);
		addMouseWheelListener(this);
	}
	
	public Entity getGeneratedEntity() { return generatedEntity; }
	public BufferedImage getPicture() { return picture; }
	public void setPicture(BufferedImage picture)
	{
		this.picture = picture;
		if (picture != null)
			setBounds(getX(), getY(), picture.getWidth(), picture.getHeight());
		repaint();
	}

    public void addActionListener(ActionListener listener) { listenerList.add(ActionListener.class, listener); }
    public void removeActionListener(ActionListener listener) { listenerList.remove(ActionListener.class, listener); }
	private void fireActionPerformed()
	{
        ActionListener[] listeners;
        
        listeners = listenerList.getListeners(ActionListener.class);
        if (listeners != null)
			if (listeners.length > 0)
				listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
    }
	private void fireActionPerformed(int input)
	{
        ActionListener[] listeners;
        
        listeners = listenerList.getListeners(ActionListener.class);
        if (listeners != null)
			if (listeners.length > 0)
				listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "" + input));
    }
	
	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		
		if (picture != null)
			picture.getGraphics().drawImage(picture.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
	}
	@Override
	public void paintComponent(Graphics gd)
	{
		super.paintComponent(gd);
		
		if (picture != null)
		{
			gd.drawImage(picture, 0, 0, getWidth(), getHeight(), this);
		}
		if (pressed)
		{
			gd.setColor(Color.WHITE);
			gd.drawOval((int)(generatedEntity.getLocation().x - generatedEntity.getRadius()),
					    (int)(generatedEntity.getLocation().y - generatedEntity.getRadius()),
					    (int)generatedEntity.getDiameter(),
					    (int)generatedEntity.getDiameter());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e)
	{
		pressed = e.getButton() == MouseEvent.BUTTON1;
		canceled = !pressed;
		if (pressed)
			generatedEntity = new Entity(10000, DEFAULT_ENTITY_DIAMETER, Point.fromPoint(e.getPoint()));
		new Thread()
		{
			public void run()
			{
				while (pressed)
				{
					generatedEntity.getCurrentVector().x = (getMousePosition().getX() - e.getPoint().getX()) / 10;
					generatedEntity.getCurrentVector().y = (getMousePosition().getY() - e.getPoint().getY()) / 10;
				}
			}
		}.start();
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{
		Point pointReleased, temp;
		
		pressed = false;
		if (!canceled)
			fireActionPerformed();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if (pressed)
			generatedEntity.setDiameter(generatedEntity.getDiameter() + (e.getWheelRotation() < 0 ? 1 : -1));
	}
}