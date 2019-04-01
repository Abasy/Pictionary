package fr.ensibs.view;

import java.awt.BorderLayout;
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
	private Chat zone_de_chat ;
	private Mouse_listener ml ;
	private boolean playing ;
	private JTextField text ;
	
	public FrameAffichage()
	{
		this.fenetre = new Affichage() ;
		this.fenetre.setLayout( new BorderLayout() ) ;
		this.zone_de_chat = new Chat() ;
		this.zone_de_chat.setLayout( new BorderLayout() ) ;
		this.setLayout( new BorderLayout() ) ;
		this.ml = new Mouse_listener( this.fenetre ) ;
		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		this.setLocationRelativeTo( null ) ;
		this.setTitle( "Pictionary" ) ;
		this.add( this.fenetre , BorderLayout.CENTER ) ;
		this.fenetre.addMouseListener( this.ml ) ;
		this.fenetre.addMouseMotionListener( this.ml ) ;
		this.setResizable( false ) ;
		this.fenetre.setTaille_pinceau( 10 ) ;
		
		this.text = new JTextField( 20 ) ;
		this.text.setSize( 200 , 20 ) ;
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
		this.add( this.zone_de_chat , BorderLayout.EAST ) ;
		this.zone_de_chat.add( this.text , BorderLayout.SOUTH ) ;
		this.playing = true ;
		this.text.addActionListener( action ) ;
		this.pack() ;
		this.setVisible( true ) ;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
	}
	
	public void play()
	{
		while ( this.playing == true )
		{
			if ( this.fenetre.isReset() == true )
			{
				//this.fenetre.repaint() ;
				try
				{
					Thread.sleep( 50 ) ;
				} catch (InterruptedException e) {e.printStackTrace();}
				this.fenetre.setReset( false ) ;
			}
			try
			{
				Thread.sleep( 4 ) ;
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
}
