package fr.ensibs.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import fr.ensibs.model.User;
import fr.ensibs.view.FrameAffichage;

public class MyListener2 implements MessageListener
{
	FrameAffichage jeu ;
	
	public MyListener2( FrameAffichage jeu )
	{
		this.jeu = jeu ;
	}
	@Override
	public void onMessage( Message message )
	{
		try
		{
			TextMessage myMessage = (TextMessage) message ; // faire un selector pour les tags
			String nom_joueur = (String) myMessage.getBody( String.class ) ;
			System.out.println(nom_joueur);
			this.jeu.joueurs.add( new User( nom_joueur ) ) ;
		} catch (ClassCastException | JMSException e) {e.printStackTrace();}
	}

}
