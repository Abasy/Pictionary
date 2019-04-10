package fr.ensibs.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyListener3 implements MessageListener
{
	
	public MyListener3()
	{
		
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
		} catch (ClassCastException | JMSException e) {e.printStackTrace();}
	}

}
