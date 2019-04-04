package fr.ensibs.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Affichage extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;
	private int zone_de_dessin_x ;
	private int zone_de_dessin_y ;
	private int taille_pinceau ;
	private boolean reset ;
	
	public Affichage()
	{
		this.reset = true ;
		this.setPreferredSize( new Dimension( 600 , 400 ) ) ;
		this.zone_de_dessin_x = -100 ; // il arrive trop souvent que le fond ne se peint pas à l'initialisation
		this.zone_de_dessin_y = -100 ;
	}
	
	/**
	 * s'occupe de l'affichage
	 */
	public void paintComponent( Graphics g )
	{
		if ( reset == true || ( this.zone_de_dessin_x == -100 && this.zone_de_dessin_y == -100 ) )
		{
			//reset de l'écran
			g.setColor( new Color( 55 , 255 , 255 ) ) ;
			g.fillRect( 0 , 0 , 600 , 400 ) ;
			reset = false ;
		}
		else
		{
			// dessin
			g.setColor( new Color( 0 , 0 , 0 ) ) ;
			g.fillRect( (int) (this.zone_de_dessin_x - this.taille_pinceau*0.5) , (int) (this.zone_de_dessin_y - this.taille_pinceau*0.5) , this.taille_pinceau , this.taille_pinceau ) ;
		}
	}
	
	public void reset()
	{
		
	}

	public int getZone_de_dessin_x()
	{
		return zone_de_dessin_x ;
	}

	public void setZone_de_dessin_x( int zone_de_dessin_x )
	{
		this.zone_de_dessin_x = zone_de_dessin_x ;
	}

	public int getZone_de_dessin_y()
	{
		return zone_de_dessin_y ;
	}

	public void setZone_de_dessin_y( int zone_de_dessin_y )
	{
		this.zone_de_dessin_y = zone_de_dessin_y ;
	}

	public int getTaille_pinceau()
	{
		return taille_pinceau ;
	}

	public void setTaille_pinceau( int taille_pinceau )
	{
		this.taille_pinceau = taille_pinceau ;
	}

	public boolean isReset()
	{
		return reset ;
	}

	public void setReset( boolean reset )
	{
		this.reset = reset ;
	}
}
