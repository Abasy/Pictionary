package fr.ensibs.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

public class Affichage extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;
	private boolean reset ;
	private int zone_de_dessin_x ;
	private int zone_de_dessin_y ;
	private int taille_pinceau ;
	private Color couleur_reset ;
	private Color couleur_pinceau ;
	private Map<String,Color> couleurs_disponibles ;
	
	public Affichage( Map<String,Color> couleurs_disponibles )
	{
		this.reset = true ;
		this.setPreferredSize( new Dimension( 600 , 400 ) ) ;
		this.zone_de_dessin_x = -100 ; // il arrive trop souvent que le fond ne se peint pas à l'initialisation
		this.zone_de_dessin_y = -100 ;
		this.couleur_reset = Color.BLACK ;
		this.couleur_pinceau = Color.BLACK ;
		this.couleurs_disponibles = couleurs_disponibles ;
	}
	
	/**
	 * s'occupe de l'affichage
	 */
	public void paintComponent( Graphics g )
	{
		if ( reset == true || ( this.zone_de_dessin_x == -100 && this.zone_de_dessin_y == -100 ) )
		{
			//reset de l'écran
			g.setColor( this.couleur_reset ) ;
			g.fillRect( 0 , 0 , 600 , 400 ) ;
			reset = false ;
		}
		else
		{
			// dessin
			g.setColor( this.couleur_pinceau ) ;
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

	public Color getCouleur_pinceau()
	{
		return this.couleur_pinceau ;
	}

	public void setCouleur_pinceau( Color couleur )
	{
		this.couleur_pinceau = couleur ;
	}

	public void setCouleur_reset( Color couleur )
	{
		this.couleur_reset = couleur ;
	}
	
	public void set_couleur_pinceau( String nom_couleur )
	{
		this.setCouleur_pinceau( this.couleurs_disponibles.get( nom_couleur ) ) ;
	}
	
	public void set_couleur_reset( String nom_couleur )
	{
		this.setCouleur_reset( this.couleurs_disponibles.get( nom_couleur ) ) ;
	}
}
