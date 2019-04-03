package fr.ensibs.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class FrameAffichage extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Affichage fenetre ;
	private JPanel zone_de_chat ;
	private Mouse_listener ml ;
	private boolean playing ;
	private JTextField text ;
	private JTextArea chat ;
	
	public FrameAffichage()
	{
		this.fenetre = new Affichage() ;
		this.fenetre.setLayout( new BorderLayout() ) ;
		this.zone_de_chat = new JPanel();
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
		
		this.text = new JTextField() ;
		this.text.setPreferredSize( new Dimension( 200 , 20 ) ) ;
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L ;

			@Override
		    public void actionPerformed( ActionEvent e )
		    {
				gestion_chat( e.getActionCommand() ) ;
		    }
		};
		this.chat = new JTextArea() ;
		this.chat.setPreferredSize( new Dimension( 200 , 377 ) ) ;
		this.chat.setEditable( false ) ;
		this.chat.setWrapStyleWord( true ) ;
		JScrollPane scroll_bar = new JScrollPane( this.chat ) ;
		scroll_bar.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) ;
		scroll_bar.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ) ;
		scroll_bar.setPreferredSize( new Dimension( 200 , 380 ) ) ;
		this.add( this.zone_de_chat , BorderLayout.EAST ) ;
		this.zone_de_chat.add( scroll_bar , BorderLayout.CENTER ) ;
		this.zone_de_chat.add( this.text , BorderLayout.SOUTH ) ;
		this.playing = true ;
		this.text.addActionListener( action ) ;
		this.pack() ;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
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
			try
			{
				Thread.sleep( 4 ) ;
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void gestion_chat( String message )
	{
		if ( message.equals( "" ) == false )
		{
			System.out.println( message ) ; // le mettre dans le topic
			this.chat.setText( this.chat.getText() +  message + "\n" ) ;
			if ( this.chat.getLineCount() > 25 )
			{
				this.chat.setPreferredSize( new Dimension( this.chat.getWidth() , this.chat.getHeight() + 16 ) ) ;
			}
			else if ( this.chat.getLineCount() == 25 )
			{
				this.chat.setPreferredSize( new Dimension( this.chat.getWidth() , this.chat.getHeight() + 8 ) ) ;
			}
			this.text.setText( "" ) ;
		}
	}
	
}
