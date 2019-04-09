package fr.ensibs.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Mouse_listener implements MouseMotionListener , MouseListener
{
	private boolean drawing ;
	private Affichage fenetre ;

	public Mouse_listener( Affichage affichage )
	{
		this.drawing = false ;
		this.fenetre = affichage ;
	}

	@Override
	public void mouseDragged( MouseEvent e )
	{
		if ( this.isDrawing() == true )
		{
			this.fenetre.setZone_de_dessin_x( (int) e.getX() ) ;
			this.fenetre.setZone_de_dessin_y( (int) e.getY() ) ;
			this.fenetre.paint(this.fenetre.getGraphics()) ; // repaint bug
		}
	}

	@Override
	public void mouseMoved( MouseEvent e )
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked( MouseEvent e )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed( MouseEvent e )
	{
		if ( this.isDrawing() == true )
		{
			this.fenetre.setZone_de_dessin_x( (int) e.getX() ) ;
			this.fenetre.setZone_de_dessin_y( (int) e.getY() ) ;
			this.fenetre.paint( this.fenetre.getGraphics() ) ; // repaint bug
		}
	}

	@Override
	public void mouseReleased( MouseEvent e )
	{
		;
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
