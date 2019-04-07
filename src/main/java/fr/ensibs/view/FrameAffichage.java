package fr.ensibs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
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
	private JPanel zone_affichage_joueurs ;
	private JPanel zone_outil_dessin ;
	private Mouse_listener ml ;
	private boolean playing ;
	private JTextField text ;
	private JTextArea chat ;
	private JLabel reponse ;
	private JLabel chronometre ;
	private JButton start_et_reset_bouton ;
	private ArrayList<JLabel> affiche_joueur ;
	//private ArrayList<User> joueur ;
	private JLabel taille_pinceau ;
	private JButton plus_taille_pinceau ;
	private JButton moins_taille_pinceau ;
	
	
	public FrameAffichage()
	{
		this.setLayout( new BorderLayout() ) ;
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		this.setLocationRelativeTo( null ) ;
		this.setTitle( "Pictionary" ) ;
		this.setResizable( false ) ;
		this.setPreferredSize( new Dimension( 1000 , 500 ) ) ;
		this.addWindowListener(new WindowAdapter()
			{
		        @Override
		        public void windowClosing(WindowEvent event)
		        {
		        	System.out.println("e");
		        }
			});
		this.zone_de_dessin = new JPanel() ;
		this.fenetre = new Affichage( Color.BLACK ) ;
		this.zone_de_dessin.setLayout( new BorderLayout() ) ;
		this.zone_de_dessin.setPreferredSize( new Dimension( 600 , 450 ) ) ;
		this.zone_de_chat = new JPanel();
		this.zone_de_chat.setLayout( new BorderLayout() ) ;
		this.add( this.zone_de_dessin , BorderLayout.CENTER ) ;
		this.ml = new Mouse_listener( this.fenetre ) ;
		this.fenetre.addMouseListener( this.ml ) ;
		this.fenetre.addMouseMotionListener( this.ml ) ;
		this.zone_de_dessin.add( this.fenetre , BorderLayout.CENTER ) ;
		this.fenetre.setTaille_pinceau( 10 ) ;
		this.text = new JTextField() ;
		this.text.setPreferredSize( new Dimension( 275 , 20 ) ) ;
		this.chat = new JTextArea() ;
		this.chat.setEditable( false ) ;
		this.chat.setLineWrap( true ) ;
		this.chat.setWrapStyleWord( true ) ;
		this.chat.setFont( new Font( "TimesRoman" , Font.BOLD , 14 ) ) ;
		JScrollPane scroll_bar = new JScrollPane( this.chat ) ;
		scroll_bar.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) ;
		scroll_bar.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ) ;
		scroll_bar.setPreferredSize( new Dimension( 275 , 380 ) ) ;
		this.add( this.zone_de_chat , BorderLayout.EAST ) ;
		this.zone_de_chat.add( scroll_bar , BorderLayout.CENTER ) ;
		this.zone_de_chat.add( this.text , BorderLayout.SOUTH ) ;
		this.playing = true ;
		this.text.addActionListener( this.create_text_action() ) ;
		this.pack() ;
		this.hud = new JPanel() ;
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
		this.start_et_reset_bouton.setPreferredSize( new Dimension( 80 , 50 ) ) ;
		this.hud.add( this.start_et_reset_bouton , BorderLayout.EAST ) ;
		this.zone_affichage_joueurs = new JPanel() ;
		this.zone_affichage_joueurs.setLayout( new BoxLayout( this.zone_affichage_joueurs , BoxLayout.Y_AXIS ) ) ;
		this.zone_affichage_joueurs.setPreferredSize( new Dimension( 125 , 650 ) ) ;
		this.add( this.zone_affichage_joueurs , BorderLayout.WEST ) ;
		this.affiche_joueur = new ArrayList<JLabel>() ;
		this.affiche_joueur.add( new JLabel("ordonnateur : 9990") ) ;
		this.affiche_joueur.add( new JLabel("fort : 100") ) ;
		this.affiche_joueur.add( new JLabel("grand : 25") ) ;
		this.affiche_joueur.add( new JLabel("editeur : 9990") ) ;
		this.affiche_joueur.add( new JLabel("fort : 100") ) ;
		this.affiche_joueur.add( new JLabel("grand : 25") ) ;
		this.affiche_joueur.add( new JLabel("editeur : 9990") ) ;
		this.affiche_joueur.add( new JLabel("fort : 100") ) ;
		this.affiche_joueur.add( new JLabel("grand : 25") ) ;
		this.affiche_joueur.add( new JLabel("editeur : 9990") ) ;
		this.affiche_joueur.add( new JLabel("fort : 100") ) ;
		this.affiche_joueur.add( new JLabel("grand : 25") ) ;
		this.affiche_joueur.add( new JLabel("editeur : 9990") ) ;
		this.affiche_joueur.add( new JLabel("fort : 100") ) ;
		this.affiche_joueur.add( new JLabel("grand : 25") ) ;
		this.affiche_joueur.add( new JLabel("editeur : 9990") ) ;
		this.affiche_joueur.add( new JLabel("fort : 100") ) ;
		this.affiche_joueur.add( new JLabel("grand : 25") ) ;
		this.affiche_joueur.add( new JLabel("editeur : 9990") ) ;
		this.affiche_joueur.add( new JLabel("fort : 100") ) ;
		this.affiche_joueur.add( new JLabel("grand : 25") ) ;
		this.affiche_joueur.add( new JLabel("editeur : 9990") ) ;
		this.affiche_joueur.add( new JLabel("fort : 100") ) ;
		this.affiche_joueur.add( new JLabel("grand : 25") ) ;
		for ( JLabel joueur : this.affiche_joueur )
		{
			this.zone_affichage_joueurs.add( joueur ) ;
		}
		this.zone_outil_dessin = new JPanel() ;
		this.zone_outil_dessin.setPreferredSize( new Dimension( 600 , 50 ) ) ;
		this.zone_de_dessin.add( this.zone_outil_dessin  , BorderLayout.NORTH ) ;
		this.moins_taille_pinceau = new JButton() ;
		this.moins_taille_pinceau.setText( "-" ) ;
		this.moins_taille_pinceau.setPreferredSize( new Dimension( 45 , 25 ) ) ;
		this.moins_taille_pinceau.addActionListener( this.create_moins_taille_pinceau_bouton_action() ) ;
		this.moins_taille_pinceau.setVisible( false ) ;
		this.zone_outil_dessin.add( this.moins_taille_pinceau ) ;
		this.taille_pinceau = new JLabel( Integer.toString(this.fenetre.getTaille_pinceau()) ) ;
		this.taille_pinceau.setPreferredSize( new Dimension( 20 , 50 ) ) ;
		this.taille_pinceau.setHorizontalAlignment( SwingConstants.CENTER ) ;
		this.taille_pinceau.setVerticalAlignment( SwingConstants.CENTER ) ;
		this.taille_pinceau.setVisible( false ) ;
		this.zone_outil_dessin.add( this.taille_pinceau ) ;
		this.plus_taille_pinceau = new JButton() ;
		this.plus_taille_pinceau.setText( "+" ) ;
		this.plus_taille_pinceau.setPreferredSize( new Dimension( 45 , 25 ) ) ;
		this.plus_taille_pinceau.addActionListener( this.create_plus_taille_pinceau_bouton_action() ) ;
		this.plus_taille_pinceau.setVisible( false ) ;
		this.zone_outil_dessin.add( this.plus_taille_pinceau ) ;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);System.out.println(this.getSize().width);
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
					switch ( start_et_reset_bouton.getText() )
					{
						case "START" :
							chat.setText( chat.getText() +  "word added by " + "\n" ) ;
							break ;
						case "READY" :
							chat.setText( chat.getText() +  e.getActionCommand() + "\n" ) ;
							break ;
						case "RESET" :
							chat.setText( chat.getText() +  e.getActionCommand() + "\n" ) ;
							break ;
						case "ANSWER" :
							chat.setText( chat.getText() +  e.getActionCommand() + "\n" ) ;
							break ;
						default :
							break ;
					}
					System.out.println( e.getActionCommand() ) ; // le mettre dans le topic
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
					switch ( start_et_reset_bouton.getText() )
					{
						case "START" :
							start_et_reset_bouton.setText( "READY" ) ;
							break ;
						case "READY" :
							start_et_reset_bouton.setText( "RESET" ) ;
							/*if ( this.joueur.isDrawer == false )
							{
								start_et_reset_bouton.setVisible( false ) ;
							}
							else
							{*/
								moins_taille_pinceau.setVisible( true ) ;
								taille_pinceau.setVisible( true ) ;
								plus_taille_pinceau.setVisible( true ) ;
							//}
							ml.setDrawing( true ) ;
							reponse.setText( "_ _ _ _ _ E _" ) ;
							chronometre.setText( "60" ) ;
							TimerThread chrono = new TimerThread( chronometre ) ;
							chrono.start() ;
							break ;
						case "RESET" :
							fenetre.setReset( true ) ;
							fenetre.paint(fenetre.getGraphics()) ; // repaint bug
							break ;
						case "ANSWER" :
							break ;
						default :
							break ;
					}
				}
		    }
		};
		return action ;
	}
	
	public Action create_moins_taille_pinceau_bouton_action()
	{
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L ;

			@Override
		    public void actionPerformed( ActionEvent e )
		    {
				if ( fenetre.getTaille_pinceau() > 1 )
				{
					fenetre.setTaille_pinceau( fenetre.getTaille_pinceau() - 1 ) ;
					taille_pinceau.setText( Integer.toString(fenetre.getTaille_pinceau()) );
				}
				
		    }
		};
		return action ;
	}
	
	public Action create_plus_taille_pinceau_bouton_action()
	{
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L ;

			@Override
		    public void actionPerformed( ActionEvent e )
		    {
				if ( fenetre.getTaille_pinceau() < 20 )
				{
					fenetre.setTaille_pinceau( fenetre.getTaille_pinceau() + 1 ) ;
					taille_pinceau.setText( Integer.toString(fenetre.getTaille_pinceau()) );
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
