package fr.ensibs.view;

import javax.swing.JLabel;

public class TimerThread extends Thread
{
	private JLabel chronometre ;
	private boolean running ;
	
	public TimerThread(  JLabel chronometre )
	{
		this.chronometre = chronometre ;
		this.running = true ;
	}
	
	public void run()
	{
		while ( this.running == true )
		{
			try 
			{
				Thread.sleep(1000) ;
			} catch (InterruptedException e) {e.printStackTrace();}
			this.chronometre.setText( Integer.toString( Integer.parseInt( this.chronometre.getText() ) - 1 ) ) ;
			if ( this.chronometre.getText().equals( "0" ) == true )
			{
				this.running = false ;
			}
		}
	}
}
