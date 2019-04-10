package fr.ensibs.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import fr.ensibs.view.FrameAffichage;

public class MyListener5 implements MessageListener
{
	private FrameAffichage jeu ;
	
	public MyListener5( FrameAffichage jeu )
	{
		this.jeu = jeu ;
	}
	@Override
	public void onMessage( Message message )
	{
		try
		{
			TextMessage myMessage = (TextMessage) message ; // faire un selector pour les tags
			String point = (String) myMessage.getBody( String.class ) ;
			String[] x_et_y = point.split( " " ) ;
			System.out.println(point);
			this.jeu.append_to_chat( "Word added by " + this.jeu.moi.name + "." , this.jeu.moi.colorUser ) ;
		} catch (ClassCastException | JMSException e) {e.printStackTrace();}
	}

}
