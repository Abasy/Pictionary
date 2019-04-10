package fr.ensibs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import fr.ensibs.client.Client;
import fr.ensibs.model.User;

public class FrameAffichage extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Affichage fenetre ;
	private JPanel panel_global ; // necessaire pour le passage de l'ecran de connexion a l'ecran de jeu
	private JPanel ecran_connexion ;
	private JPanel zone_de_chat ;
	private JPanel zone_de_dessin ;
	private JPanel hud ;
	private JPanel zone_affichage_joueurs ;
	private JPanel zone_outil_dessin ;
	private Mouse_listener ml ;
	private boolean playing ;
	private JTextField text ;
	private JTextPane chat ;
	private JLabel reponse ;
	private JLabel chronometre ;
	private JButton start_et_reset_bouton ;
	private ArrayList<JLabel> affiche_joueur ;
	private ArrayList<User> joueurs ;
	public User moi ;
	private JLabel taille_pinceau ;
	private JButton plus_taille_pinceau ;
	private JButton moins_taille_pinceau ;
	private JLabel background_couleur ;
	private JComboBox<String> choix_couleur_background ;
	private JLabel pinceau_couleur ;
	private JComboBox<String> choix_couleur_pinceau ;
	private String[] nom_couleurs_disponibles = { "Black" , "Dark-gray" , "Gray" , "Light-gray" , "White" , "Red" , "Green" , "Blue" , "Orange" , "Yellow" , "Pink" , "Cyan" , "Magenta" } ;
	private Map<String,Color> couleurs_disponibles ;
	private Client client ;
	private String[] tag_joueur ;
	private String[] tag_new_joueur ;
	private String[] coordonee_pinceau ;
	
	public FrameAffichage( String host , String port )
	{
		
		this.couleurs_disponibles = new HashMap<String,Color>() ;
		this.couleurs_disponibles.put( "Black" , Color.BLACK ) ;
		this.couleurs_disponibles.put( "Dark-gray" , Color.DARK_GRAY ) ;
		this.couleurs_disponibles.put( "Gray" , Color.GRAY ) ;
		this.couleurs_disponibles.put( "Light-gray" , Color.LIGHT_GRAY ) ;
		this.couleurs_disponibles.put( "White" , Color.WHITE ) ;
		this.couleurs_disponibles.put( "Red" , Color.RED ) ;
		this.couleurs_disponibles.put( "Green" , Color.GREEN ) ;
		this.couleurs_disponibles.put( "Blue" , Color.BLUE ) ;
		this.couleurs_disponibles.put( "Orange" , Color.ORANGE ) ;
		this.couleurs_disponibles.put( "Yellow" , Color.YELLOW ) ;
		this.couleurs_disponibles.put( "Pink" , Color.PINK ) ;
		this.couleurs_disponibles.put( "Cyan" , Color.CYAN ) ;
		this.couleurs_disponibles.put( "Magenta" , Color.MAGENTA ) ;
		this.panel_global = new JPanel() ;
		this.panel_global.setLayout( new BorderLayout() ) ;
		this.add( this.panel_global ) ;
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		this.setLocationRelativeTo( null ) ;
		this.setTitle( "Pictionary" ) ;
		this.setResizable( false ) ;
		this.addWindowListener( new WindowAdapter()
			{
		        @Override
		        public void windowClosing(WindowEvent event)
		        {
		    		try
		    		{
		    			client.context.close() ;
		    			client.close() ;
		    		} catch (IOException | NamingException e) {e.printStackTrace();}
		        }
			});
		this.connexion() ;
		//this.play() ;
		this.client = new Client( host , Integer.parseInt(port) , this.moi.name ) ;
		try
		{
			client.context = new InitialContext() ;
			client.topic = (Topic) client.context.lookup( client.topicname ) ;
			TopicConnectionFactory topic_connection_factory = (TopicConnectionFactory) client.context.lookup( "ConnectionFactory" ) ;
			client.topic_connection = topic_connection_factory.createTopicConnection() ;
			this.tag_joueur = new String[1] ;
			this.tag_joueur[0] = "playing=true" ;
			client.filter( client.parseTags( this.tag_joueur , 0 ) ) ;
			this.tag_new_joueur = new String[1] ;
			this.tag_new_joueur[0] = "new=true" ;
			client.filter2( client.parseTags( this.tag_new_joueur , 0 ) ) ;
			this.coordonee_pinceau = new String[1] ;
			this.coordonee_pinceau[0] = "dessin=true" ;
			client.filter2( client.parseTags( this.coordonee_pinceau , 0 ) ) ;
			Properties tags = client.parseTags( this.tag_joueur , 0 ) ;
			client.share( "hello" , tags ) ;
		} catch (NamingException | JMSException e1) {e1.printStackTrace();}
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
							append_to_chat( "Word added by " + moi.name + "." , moi.colorUser ) ;
							break ;
						case "READY" :
							append_to_chat( moi.name + " : " + e.getActionCommand() , moi.colorUser ) ;
							break ;
						case "RESET" :
							append_to_chat( moi.name + " : " + e.getActionCommand() , moi.colorUser ) ;
							break ;
						case "ANSWER" :
							append_to_chat( moi.name + " : " + e.getActionCommand() , moi.colorUser ) ;
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
							/*if ( moi.isDrawer == false )
							{
								start_et_reset_bouton.setText( "ANSWER" ) ;
								start_et_reset_bouton.setVisible( false ) ;
							}
							else*/
								start_et_reset_bouton.setText( "RESET" ) ;
								moins_taille_pinceau.setVisible( true ) ;
								taille_pinceau.setVisible( true ) ;
								plus_taille_pinceau.setVisible( true ) ;
								background_couleur.setVisible( true ) ;
								choix_couleur_background.setVisible( true ) ;
								pinceau_couleur.setVisible( true ) ;
								choix_couleur_pinceau.setVisible( true ) ;
								ml.setDrawing( true ) ;
							//}
							reponse.setText( "_ _ _ _ _ E _" ) ;
							chronometre.setText( "60" ) ;
							TimerThread chrono = new TimerThread( chronometre ) ;
							chrono.start() ;
							break ;
						case "RESET" :
							fenetre.setReset( true ) ;
							fenetre.set_couleur_pinceau( (String) choix_couleur_background.getSelectedItem() ) ;
							fenetre.paint( fenetre.getGraphics() ) ;
							fenetre.set_couleur_pinceau( (String) choix_couleur_pinceau.getSelectedItem() ) ;
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
					taille_pinceau.setText( Integer.toString( fenetre.getTaille_pinceau() ) );
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
					taille_pinceau.setText( Integer.toString( fenetre.getTaille_pinceau() ) ) ;
				}
		    }
		};
		return action ;
	}
	
	public Action create_choix_couleur_pinceau_action()
	{
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L ;

			@Override
		    public void actionPerformed( ActionEvent e )
		    {
				fenetre.set_couleur_pinceau( (String) choix_couleur_pinceau.getSelectedItem() ) ;
		    }
		};
		return action ;
	}
	
	public Action create_jouer_bouton_action()
	{
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L ;

			@Override
		    public void actionPerformed( ActionEvent e )
		    {
				// TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
		    }
		};
		return action ;
	}
	
	public void connexion()
	{
		this.ecran_connexion = new JPanel() ;
		this.ecran_connexion.setLayout( new BorderLayout() ) ;
		this.panel_global.add( this.ecran_connexion , BorderLayout.CENTER ) ;
		JLabel titre = new JLabel("PICTIONARY") ;
		titre.setPreferredSize( new Dimension( 100 , 100 ) ) ;
		titre.setHorizontalAlignment( SwingConstants.CENTER ) ;
		titre.setVerticalAlignment( SwingConstants.CENTER ) ;
		this.ecran_connexion.add( titre , BorderLayout.NORTH ) ;
		JPanel panel_champ_a_remplir = new JPanel() ;
		panel_champ_a_remplir.setLayout( new BorderLayout() ) ;
		panel_champ_a_remplir.setPreferredSize( new Dimension( 300 , 90 ) ) ;
		this.ecran_connexion.add( panel_champ_a_remplir , BorderLayout.CENTER ) ;
		JPanel panel_username = new JPanel() ;
		panel_username.setLayout( new BorderLayout() ) ;
		panel_username.setPreferredSize( new Dimension( 300 , 30 ) ) ;
		panel_champ_a_remplir.add( panel_username , BorderLayout.NORTH ) ;
		JLabel texte_username = new JLabel( "Your username :" ) ;
		texte_username.setPreferredSize( new Dimension( 125 , 30 ) ) ;
		texte_username.setHorizontalAlignment( SwingConstants.CENTER ) ;
		texte_username.setVerticalAlignment( SwingConstants.CENTER ) ;
		panel_username.add( texte_username , BorderLayout.CENTER ) ;
		JTextField username = new JTextField() ;
		username.setPreferredSize( new Dimension( 165 , 20 ) ) ;
		panel_username.add( username , BorderLayout.EAST ) ;
		JPanel panel_color = new JPanel() ;
		panel_color.setLayout( new BorderLayout() ) ;
		panel_color.setPreferredSize( new Dimension( 300 , 30 ) ) ;
		panel_champ_a_remplir.add( panel_color , BorderLayout.CENTER ) ;
		JLabel texte_color = new JLabel( "Your color :" ) ;
		texte_color.setPreferredSize( new Dimension( 125 , 30 ) ) ;
		texte_color.setHorizontalAlignment( SwingConstants.CENTER ) ;
		texte_color.setVerticalAlignment( SwingConstants.CENTER ) ;
		panel_color.add( texte_color , BorderLayout.CENTER ) ;
		JComboBox<String> choix_couleur = new JComboBox<String>( this.nom_couleurs_disponibles ) ;
		choix_couleur.setPreferredSize( new Dimension( 165 , 30 ) ) ;
		panel_color.add( choix_couleur , BorderLayout.EAST ) ;
		JPanel panel_salon = new JPanel() ;
		panel_salon.setLayout( new BorderLayout() ) ;
		panel_salon.setPreferredSize( new Dimension( 300 , 30 ) ) ;
		panel_champ_a_remplir.add( panel_salon , BorderLayout.SOUTH ) ;
		JLabel texte_salon = new JLabel( "Your room :" ) ;
		texte_salon.setPreferredSize( new Dimension( 125 , 30 ) ) ;
		texte_salon.setHorizontalAlignment( SwingConstants.CENTER ) ;
		texte_salon.setVerticalAlignment( SwingConstants.CENTER ) ;
		panel_salon.add( texte_salon , BorderLayout.CENTER ) ;
		JTextField salon = new JTextField() ;
		salon.setPreferredSize( new Dimension( 165 , 20 ) ) ;
		panel_salon.add( salon , BorderLayout.EAST ) ;
		JButton jouer = new JButton() ;
		jouer.setText( "JOUER" ) ;
		jouer.setPreferredSize( new Dimension( 100 , 50 ) ) ;
		jouer.addActionListener( this.create_jouer_bouton_action() ) ;
		this.ecran_connexion.add( jouer , BorderLayout.SOUTH ) ;
		
		this.pack() ;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);System.out.println(this.getSize().width + " " + this.getSize().height);
	}
	
	public void play()
	{
		this.zone_de_dessin = new JPanel() ;
		this.fenetre = new Affichage( this.couleurs_disponibles ) ;
		this.zone_de_dessin.setLayout( new BorderLayout() ) ;
		this.zone_de_chat = new JPanel();
		this.zone_de_chat.setLayout( new BorderLayout() ) ;
		this.panel_global.add( this.zone_de_dessin , BorderLayout.CENTER ) ;
		this.ml = new Mouse_listener( this.fenetre ) ;
		this.fenetre.addMouseListener( this.ml ) ;
		this.fenetre.addMouseMotionListener( this.ml ) ;
		this.zone_de_dessin.add( this.fenetre , BorderLayout.CENTER ) ;
		this.fenetre.setTaille_pinceau( 10 ) ;
		this.text = new JTextField() ;
		this.text.setPreferredSize( new Dimension( 275 , 20 ) ) ;
		this.chat = new JTextPane() ;
		this.chat.setPreferredSize( new Dimension( 275 , 380 ) ) ;
		this.chat.setEditable( false ) ;
		JScrollPane scroll_bar = new JScrollPane( this.chat ) ;
		scroll_bar.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) ;
		scroll_bar.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ) ;
		scroll_bar.setPreferredSize( new Dimension( 275 , 380 ) ) ;
		this.panel_global.add( this.zone_de_chat , BorderLayout.EAST ) ;
		this.zone_de_chat.add( scroll_bar , BorderLayout.CENTER ) ;
		this.zone_de_chat.add( this.text , BorderLayout.SOUTH ) ;
		this.playing = true ;
		this.text.addActionListener( this.create_text_action() ) ;
		this.hud = new JPanel() ;
		this.hud.setLayout( new BorderLayout() ) ;
		this.zone_de_dessin.add( hud , BorderLayout.SOUTH ) ;
		this.reponse = new JLabel() ;
		this.reponse.setPreferredSize( new Dimension( 470 , 50 ) ) ;
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
		this.panel_global.add( this.zone_affichage_joueurs , BorderLayout.WEST ) ;
		this.affiche_joueur = new ArrayList<JLabel>() ;
		for ( JLabel joueur : this.affiche_joueur )
		{
			this.zone_affichage_joueurs.add( joueur ) ;
		}
		this.zone_outil_dessin = new JPanel() ;
		this.zone_outil_dessin.setPreferredSize( new Dimension( 600 , 200 ) ) ;
		this.zone_de_dessin.add( this.zone_outil_dessin , BorderLayout.NORTH ) ;
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
		this.background_couleur = new JLabel( "Background color (reset needed) :" ) ;
		this.background_couleur.setPreferredSize( new Dimension( 200 , 50 ) ) ;
		this.background_couleur.setVisible( false ) ;
		this.zone_outil_dessin.add( background_couleur ) ;
		this.choix_couleur_background = new JComboBox<String>( this.nom_couleurs_disponibles ) ;
		this.choix_couleur_background.setPreferredSize( new Dimension( 85 , 30 ) ) ;
		this.choix_couleur_background.setVisible( false ) ;
		this.zone_outil_dessin.add( choix_couleur_background ) ;
		this.pinceau_couleur = new JLabel( "Pencil color :" ) ;
		this.pinceau_couleur.setPreferredSize( new Dimension( 75 , 50 ) ) ;
		this.pinceau_couleur.setVisible( false ) ;
		this.zone_outil_dessin.add( pinceau_couleur ) ;
		this.choix_couleur_pinceau = new JComboBox<String>( this.nom_couleurs_disponibles ) ;
		this.choix_couleur_pinceau.setPreferredSize( new Dimension( 85 , 30 ) ) ;
		this.choix_couleur_pinceau.addActionListener( this.create_choix_couleur_pinceau_action() ) ;
		this.choix_couleur_pinceau.setVisible( false ) ;
		this.zone_outil_dessin.add( choix_couleur_pinceau ) ;
		this.pack() ;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);System.out.println(this.getSize().width + " " + this.getSize().height);
	}
	
	private void append_to_chat( String msg , Color c )
    {
        StyleContext sc = StyleContext.getDefaultStyleContext() ;
        AttributeSet aset = sc.addAttribute( SimpleAttributeSet.EMPTY , StyleConstants.Foreground , c ) ;

        aset = sc.addAttribute( aset , StyleConstants.FontFamily , "Lucida Console" ) ;
        aset = sc.addAttribute( aset , StyleConstants.Alignment , StyleConstants.ALIGN_JUSTIFIED ) ;

        int len = this.chat.getDocument().getLength() ;
        this.chat.setCaretPosition( len ) ;
        this.chat.setCharacterAttributes( aset , false ) ;
        try {
            this.chat.getStyledDocument().insertString( this.chat.getDocument().getLength() , msg + "\n" , aset ) ;
        } catch (BadLocationException e) { }
    }
}
