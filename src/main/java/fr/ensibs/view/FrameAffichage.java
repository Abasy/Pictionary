package fr.ensibs.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class FrameAffichage extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Affichage fenetre ;
	private JPanel zone_de_chat ;
	private JPanel zone_de_dessin ;
	private JPanel hud ;
	private Mouse_listener ml ;
	private boolean playing ;
	private JTextField text ;
	private JTextArea chat ;
	private JLabel reponse ;
	private JLabel chronometre ;
	private JButton start_et_reset_bouton ;
	//private ArrayList<User> joueur ;
	
	public FrameAffichage()
	{
		this.addWindowListener(new WindowAdapter()
			{
		        @Override
		        public void windowClosing(WindowEvent event)
		        {
		        	System.out.println("e");
		        }
			});
		this.zone_de_dessin = new JPanel() ;
		this.fenetre = new Affichage() ;
		this.zone_de_dessin.setLayout( new BorderLayout() ) ;
		this.zone_de_chat = new JPanel();
		this.zone_de_chat.setLayout( new BorderLayout() ) ;
		this.setLayout( new BorderLayout() ) ;
		this.ml = new Mouse_listener( this.fenetre ) ;
		this.fenetre.addMouseListener( this.ml ) ;
		this.fenetre.addMouseMotionListener( this.ml ) ;
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		this.setLocationRelativeTo( null ) ;
		this.setTitle( "Pictionary" ) ;
		this.add( this.zone_de_dessin , BorderLayout.CENTER ) ;
		this.zone_de_dessin.add( this.fenetre , BorderLayout.CENTER ) ;
		this.setResizable( false ) ;
		this.fenetre.setTaille_pinceau( 10 ) ;
		this.text = new JTextField() ;
		this.text.setPreferredSize( new Dimension( 200 , 20 ) ) ;
		this.chat = new JTextArea() ;
		this.chat.setEditable( false ) ;
		this.chat.setLineWrap( true ) ;
		this.chat.setWrapStyleWord( true ) ;
		JScrollPane scroll_bar = new JScrollPane( this.chat ) ;
		scroll_bar.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) ;
		scroll_bar.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ) ;
		scroll_bar.setPreferredSize( new Dimension( 200 , 380 ) ) ;
		this.add( this.zone_de_chat , BorderLayout.EAST ) ;
		this.zone_de_chat.add( scroll_bar , BorderLayout.CENTER ) ;
		this.zone_de_chat.add( this.text , BorderLayout.SOUTH ) ;
		this.playing = true ;
		this.text.addActionListener( this.create_text_action() ) ;
		this.pack() ;
		this.hud = new JPanel() ;
		this.hud.setPreferredSize( new Dimension( 600 , 50 ) ) ;
		this.hud.setLayout( new BorderLayout() ) ;
		this.zone_de_dessin.add( hud , BorderLayout.SOUTH ) ;
		this.reponse = new JLabel() ;
		this.reponse.setHorizontalAlignment( SwingConstants.CENTER ) ;
		this.reponse.setVerticalAlignment( SwingConstants.CENTER ) ;
		this.reponse.setText( "" ) ;
		this.reponse.setFont( new Font( "TimesRoman" , Font.BOLD , 20 ) ) ;
		this.hud.add( reponse , BorderLayout.CENTER ) ;
		this.chronometre = new JLabel() ;
		this.chronometre.setPreferredSize( new Dimension( 50 , 50 ) ) ;
		this.chronometre.setHorizontalAlignment( SwingConstants.CENTER ) ;
		this.chronometre.setVerticalAlignment( SwingConstants.CENTER ) ;
		this.chronometre.setText( "" ) ;
		this.chronometre.setFont( new Font( "TimesRoman" , Font.BOLD , 20 ) ) ;
		this.hud.add( this.chronometre , BorderLayout.WEST ) ;
		this.start_et_reset_bouton = new JButton() ;
		this.start_et_reset_bouton.setText( "START" ) ;
		this.start_et_reset_bouton.addActionListener( this.create_start_et_reset_bouton_action() ) ;
		this.hud.add( this.start_et_reset_bouton , BorderLayout.EAST ) ;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	public Action create_text_action()
	{
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L ;

			@Override
		    public void actionPerformed( ActionEvent e )
		    {
				if ( e.getActionCommand().equals( "" ) == false )
				{
					System.out.println( e.getActionCommand() ) ; // le mettre dans le topic
					chat.setText( chat.getText() +  e.getActionCommand() + "\n" ) ;
					text.setText( "" ) ;
				}
		    }
		};
		return action ;
	}
	
	public Action create_start_et_reset_bouton_action()
	{
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L ;

			@Override
		    public void actionPerformed( ActionEvent e )
		    {
				if ( e.getActionCommand().equals( "" ) == false )
				{
					System.out.println( e.getActionCommand() ) ; // le mettre dans le topic
					if ( start_et_reset_bouton.getText().equals( "START" ) == true )
					{
						start_et_reset_bouton.setText( "READY" ) ;
					}
					else if ( start_et_reset_bouton.getText().equals( "READY" ) == true )
					{
						start_et_reset_bouton.setText( "RESET" ) ;
						/*if ( this.joueur.isDrawer == false )
						{
							start_et_reset_bouton.setVisible( false ) ;
						}*/
						ml.setDrawing( true ) ;
						reponse.setText( "_ _ _ _ _ E _" ) ;
						chronometre.setText( "60" ) ;
						chronometre.setAlignmentY( JLabel.CENTER_ALIGNMENT ) ;
					}
					else
					{
						fenetre.setReset( true ) ;

						fenetre.paint(fenetre.getGraphics()) ; // repaint bug
					}
				}
		    }
		};
		return action ;
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
}
