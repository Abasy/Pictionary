package fr.ensibs.view;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class FrameAffichage extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Affichage fenetre ;
	private Mouse_listener ml ;
	private boolean playing ;
	private int largueur_ecran ;
	private int hauteur_ecran ;
	private int largueur_bordure ;
	private int hauteur_bordure ;
	private JTextField text ;
	
	public FrameAffichage()
	{
		this.largueur_ecran = 800 ;
		this.hauteur_ecran = 400 ;
		this.fenetre = new Affichage() ;
		this.ml = new Mouse_listener() ;
		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		this.setLocationRelativeTo( null ) ;
		this.setTitle( "Pictionary" ) ;
		this.setContentPane( this.fenetre ) ;
		this.addMouseListener( this.ml ) ;
		this.setVisible( true ) ;
		this.largueur_bordure = getWidth()-getContentPane ().getWidth() ;
		this.hauteur_bordure = getHeight()-getContentPane().getHeight() ;
		this.setSize( this.largueur_ecran + this.largueur_bordure , this.hauteur_ecran + this.hauteur_bordure ) ;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable( false ) ;
		this.fenetre.setTaille_pinceau( 10 ) ;
		
		this.text = new JTextField( 20 ) ;
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L ;

			@Override
		    public void actionPerformed( ActionEvent e )
		    {
				System.out.println( e.getActionCommand() ) ; // le mettre dans le topic
				text.setText( "" ) ;
		    }
		};
		this.fenetre.add( text ) ;
		this.playing = true ;
		this.text.addActionListener( action ) ;
		this.text.revalidate() ;
		this.text.setBounds( 600 , 380 , 20*10 , 20 ) ;
		this.text.repaint() ;
	}
	
	public void play()
	{
		while ( this.playing == true )
		{
			if ( this.fenetre.isReset() == true )
			{
				this.fenetre.repaint() ;
				try
				{
					Thread.sleep( 50 ) ;
				} catch (InterruptedException e) {e.printStackTrace();}
				this.fenetre.setReset( false ) ;
			}
			else if ( this.ml.isDrawing() == true )
			{
				this.fenetre.setZone_de_dessin_x( (int) (MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x - this.largueur_bordure * 0.5) ) ;
				this.fenetre.setZone_de_dessin_y( (int) (MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y - this.hauteur_bordure + this.largueur_bordure * 0.5) ) ;
				this.fenetre.repaint() ;
				System.out.println( this.text.getColumns() + " " + this.text.getHeight() + " " + this.text.getWidth() + " " + this.text.getX() + " " + this.text.getY() ) ;
			}
			try
			{
				Thread.sleep( 4 ) ;
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
}
