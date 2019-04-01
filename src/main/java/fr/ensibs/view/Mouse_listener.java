package fr.ensibs.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse_listener implements MouseListener
{
	private boolean drawing ;

	public Mouse_listener()
	{
		this.drawing = false ;
	}
	
	@Override
	public void mouseClicked( MouseEvent e )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed( MouseEvent e )
	{
		this.drawing = true ;
	}

	@Override
	public void mouseReleased( MouseEvent e )
	{
		this.drawing = false ;
	}

	@Override
	public void mouseEntered( MouseEvent e )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited( MouseEvent e )
	{
		// TODO Auto-generated method stub
		
	}
	
	public boolean isDrawing()
	{
		return drawing ;
	}

	public void setDrawing( boolean drawing )
	{
		this.drawing = drawing ;
	}
}
