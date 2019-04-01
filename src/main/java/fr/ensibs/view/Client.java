package fr.ensibs.view;

import java.util.Scanner;
import java.util.regex.Pattern;

import fr.ensibs.river.RiverLookup;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.server.ServerTransaction;
import net.jini.core.transaction.server.TransactionManager;
import net.jini.space.JavaSpace;
/*
 * données basiques pour faire des tests rapidement :
add airport p p
add airport o o
add airport i i
add flight p 11/11/2222 p o
add flight o 11/11/2222 p i
add flight i 11/11/2222 o p
add flight u 11/11/2222 o i
add flight y 11/11/2222 i p
add flight t 11/11/2222 i o
add seat 22 p 1
add seat 11 p 2
add seat 1 p 3
add seat 22 o 4
add seat 11 o 5
add seat 22 i 6
add seat 11 i 7
add seat 22 u 8
add seat 11 u 9
add seat 22 y 10
add seat 11 y 11
add seat 22 t 12
add seat 11 t 13
add money 20
azerty

 */
/**
 * 
 * @author LUCK Bastien
 *
 */
public class Client
{
	
	/**
	 * J'ai vu trop tard pour le functionless et à cause des autres rendus, je n'ai pas le temps de régler ce problème, désolé.
	 * 
	 * @param args -h <server_IP> <server_port_number>
	 */
	public static void main( String[] args )
	{
		if ( args.length != 2 )
		{
			usage() ;
		}
		else
		{
			RiverLookup finder;
			try
			{
				String account_password = "azerty" ; // sert juste à delete le compte lorsque le client quitte l'application
				finder = new RiverLookup();
				JavaSpace space = (JavaSpace) finder.lookup( args[0] , Integer.parseInt( args[1] ) , JavaSpace.class ) ;
				TransactionManager tm = (TransactionManager) finder.lookup( args[0] , Integer.parseInt( args[1] ) , TransactionManager.class ) ;
				Bank_account user_account = new Bank_account( account_password ) ;
				space.write( user_account , null , Long.MAX_VALUE ) ;
				Scanner sc = new Scanner( System.in ) ;
				String action = "" ;
				System.out.println( "Available action :" ) ;
				System.out.println( "change password <account password>" ) ;
				System.out.println( "add money <amount>" ) ;
				System.out.println( "add airport <airport code> <city name>" ) ;
				System.out.println( "add flight <flight code> <departure date : dd/mm/yyyy> <departure airport code> <arrival airport code>" ) ;
				System.out.println( "add seat <seat number> <flight code> <seat price>" ) ;
				System.out.println( "search flight <departure date : dd/mm/yyyy> <departure airport code> <arrival airport code>" ) ;
				System.out.println( "reserve seat <flight code>" ) ;
				System.out.println( "delete all" ) ;
				System.out.println( "delete airport <airport code>" ) ;
				System.out.println( "delete flight <flight code>" ) ;
				System.out.println( "delete seat <seat number> <flight code>" ) ;
				System.out.println( "quit" ) ;
				System.out.println( "New here ? We just created a new account for you with azerty as password, don't forget to change it as soon as possible ;)." ) ;
				while ( action.equals( "quit" ) == false )
				{
					action = sc.nextLine() ;
					String[] check_action = action.split( " " ) ;
					if ( check_action[0].equals( "change" ) == true )
					{
						if ( check_action.length == 3 )
						{
							Bank_account bank_account_template = new Bank_account() ;
							System.out.println( "Enter your current password :" ) ;
							String mdp = sc.nextLine() ;
							bank_account_template.setAccount_code( mdp ) ;
							Bank_account check_user_money = (Bank_account) space.takeIfExists( bank_account_template , null , Long.MAX_VALUE ) ;
							if ( check_user_money != null )
							{
								account_password = check_action[2] ;
								check_user_money.setAccount_code( account_password ) ;
								space.write( check_user_money , null , 60*60*1000 ) ;
								System.out.println( "Your password have been changed successfully." ) ;
							}
							else
							{
								System.out.println( "Wrong account password." ) ;
							}
						}
						else
						{
							System.out.println( "Right syntax to change the password of your account :" ) ;
							System.out.println( "change password <account password>" ) ;
						}
					}
					else if ( check_action[0].equals( "add" ) == true )
					{
						if ( check_action.length > 1 )
						{
							if ( check_action[1].equals( "money" ) )
							{
								if ( check_action.length == 3 )
								{
									try
									{
										int added_money = Integer.parseInt( check_action[2] ) ;
										Bank_account bank_account_template = new Bank_account() ;
										System.out.println( "Enter your password :" ) ;
										String mdp = sc.nextLine() ;
										bank_account_template.setAccount_code( mdp ) ;
										Bank_account check_user_money = (Bank_account) space.takeIfExists( bank_account_template , null , Long.MAX_VALUE ) ;
										if ( check_user_money != null )
										{
											check_user_money.setBalance( check_user_money.getBalance() + added_money ) ;
											space.write( check_user_money , null , 60*60*1000 ) ;
											System.out.println( "Your balance is now " + check_user_money.getBalance() + "." ) ;
										}
										else
										{
											System.out.println( "Wrong account password." ) ;
										}
									}
									catch (NumberFormatException e)
									{
										System.out.println( "The given amount isn't valid." ) ;
									}
								}
								else
								{
									System.out.println( "Right syntax to add money to your account :" ) ;
									System.out.println( "add money <amount>" ) ;
								}
							}
							else if ( check_action[1].equals( "airport" ) == true )
							{
								if ( check_action.length == 4 )
								{
									Airport added_airport = new Airport( check_action[2] , check_action[3] ) ;
									if ( space.readIfExists( added_airport , null , Long.MAX_VALUE ) == null )
									{
										Airport check_airport_code = new Airport() ;
										check_airport_code.setAirport_code( check_action[2] ) ;
										if ( space.readIfExists( check_airport_code , null , Long.MAX_VALUE ) == null )
										{
											space.write( added_airport , null , 60*60*1000 ) ;
											System.out.println( "The airport is now available to customers." ) ;
										}
										else
										{
											System.out.println( "This airport code is already used by another airport." ) ;
										}
									}
									else
									{
										System.out.println( "This airport already exist." ) ;
									}
								}
								else
								{
									System.out.println( "Right syntax to add a new airport :" ) ;
									System.out.println( "add airport <airport code> <city name>" ) ;
								}
							}
							else if ( check_action[1].equals( "flight" ) == true )
							{
								if ( check_action.length == 6 )
								{
							        if ( Pattern.matches( "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)" , check_action[3] ) )
							        {
							        	if ( check_action[4].equals( check_action[5] ) == false )
										{
							        		Airport template_departure_airport = new Airport() ;
											template_departure_airport.setAirport_code( check_action[4] ) ;
											if ( space.readIfExists( template_departure_airport , null , Long.MAX_VALUE ) != null )
											{
												Airport template_arrival_airport = new Airport() ;
												template_arrival_airport.setAirport_code( check_action[5] ) ;
												if ( space.readIfExists( template_arrival_airport , null , Long.MAX_VALUE ) != null )
												{
													Flight added_flight = new Flight( check_action[2] , check_action[3] , check_action[4] , check_action[5] ) ;
													if ( space.readIfExists( added_flight , null , Long.MAX_VALUE ) == null )
													{
														Flight check_flight_code = new Flight() ;
														check_flight_code.setFlight_code( check_action[2] ) ;
														if ( space.readIfExists( check_flight_code , null , Long.MAX_VALUE ) == null )
														{
															space.write( added_flight , null , 60*60*1000 ) ;
															System.out.println( "The flight is now available to customers." ) ;
														}
														else
														{
															System.out.println( "This flight code is already used by another flight." ) ;
														}
													}
													else
													{
														System.out.println( "This flight already exist." ) ;
													}
												}
												else
												{
													System.out.println( "The airport arrival specified does not exist." ) ;
												}
											}
											else
											{
												System.out.println( "The airport departure specified does not exist." ) ;
											}
										}
										else
										{
											System.out.println( "The departure and arrival have to be distinct." ) ;
										}
							        }
							        else
							        {
							        	System.out.println( "The given date isn't following the syntax dd/mm/yyyy or isn't a valid date." ) ;
							        }
								}
								else
								{
									System.out.println( "Right syntax to add a new flight :" ) ;
									System.out.println( "add flight <flight code> <departure date : dd/mm/yyyy> <departure airport code> <arrival airport code>" ) ;
								}
							}
							else if ( check_action[1].equals( "seat" ) == true )
							{
								if ( check_action.length == 5 )
								{
									Flight template_flight = new Flight() ;
									template_flight.setFlight_code( check_action[3] ) ;
									if ( space.readIfExists( template_flight , null , Long.MAX_VALUE ) != null )
									{
										try
										{
											int num_seat = Integer.parseInt( check_action[2] ) ;
											try
											{
												int seat_price = Integer.parseInt( check_action[4] ) ;
												Seat added_seat = new Seat( num_seat , check_action[3] , seat_price ) ;
												if ( space.readIfExists( added_seat , null , Long.MAX_VALUE ) == null )
												{
													Seat added_seat_check_if_already_taken = new Seat( num_seat , check_action[3] , seat_price ) ;
													added_seat_check_if_already_taken.setReserved( true ) ;
													space.takeIfExists( added_seat_check_if_already_taken , null , Long.MAX_VALUE ) ;
													space.write( added_seat , null , 60*60*1000 ) ;
													System.out.println( "The seat is now available for customers." ) ;
												}
												else
												{
													System.out.println( "This seat already exist." ) ;
												}
											}
											catch (NumberFormatException e)
											{
												System.out.println( "The seat price isn't valid." ) ;
											}
										}
										catch (NumberFormatException e)
										{
											System.out.println( "The seat number isn't valid." ) ;
										}
									}
									else
									{
										System.out.println( "You can't add a seat on a flight that don't exist." ) ;
									}
								}
								else
								{
									System.out.println( "Right syntax to add a new seat :" ) ;
									System.out.println( "add seat <seat number> <flight code> <seat price>" ) ;
								}
							}
							else
							{
								System.out.println( "You can only add money to your account, an airport, a flight or a seat." ) ;
							}
						}
						else
						{
							System.out.println( "Please specify what you want to add :" ) ;
							System.out.println( "add money <amount>" ) ;
							System.out.println( "add airport <airport code> <city name>" ) ;
							System.out.println( "add flight <flight code> <departure date : dd/mm/yyyy> <departure airport code> <arrival airport code>" ) ;
							System.out.println( "add seat <seat number> <flight code> <seat price>" ) ;
						}
					}
					else if ( check_action[0].equals( "search" ) == true )
					{
						if ( check_action.length > 1 )
						{
							if ( check_action[1].equals( "flight" ) == true )
							{
								if ( check_action.length == 5 )
								{
									if ( Pattern.matches( "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)" , check_action[2] ) )
							        {
										if ( check_action[3].equals( check_action[4] ) == false )
										{
											Airport template_departure_airport = new Airport() ;
											template_departure_airport.setAirport_code( check_action[3] ) ;
											if ( space.readIfExists( template_departure_airport , null , Long.MAX_VALUE ) != null )
											{
												Airport template_arrival_airport = new Airport() ;
												template_arrival_airport.setAirport_code( check_action[4] ) ;
												if ( space.readIfExists( template_arrival_airport , null , Long.MAX_VALUE ) != null )
												{
													Flight template_flight = new Flight() ;
													template_flight.setDeparture_date( check_action[2] ) ;
													template_flight.setDeparture_airport_code( check_action[3] ) ;
													template_flight.setArrival_airport_code( check_action[4] ) ;
													Flight result_flight = (Flight) space.readIfExists( template_flight , null , Long.MAX_VALUE ) ;
													if ( result_flight != null )
													{
														System.out.println( result_flight.toString() ) ;
													}
													else
													{
														System.out.println( "No flight exist with those parameters yet." ) ;
													}
												}
												else
												{
													System.out.println( "The airport arrival specified does not exist." ) ;
												}
											}
											else
											{
												System.out.println( "The airport departure specified does not exist." ) ;
											}
										}
										else
										{
											System.out.println( "The departure and arrival have to be distinct." ) ;
										}
							        }
							        else
							        {
							        	System.out.println( "The given date isn't following the syntax dd/mm/yyyy or isn't a valid date." ) ;
							        }
								}
								else
								{
									System.out.println( "Research not valid, please follow this syntax :" ) ;
									System.out.println( "search flight <departure date : dd/mm/yyyy> <departure airport code> <arrival airport code>" ) ;
								}
							}
							else
							{
								System.out.println( "You can only search a flight." ) ;
							}
						}
						else
						{
							System.out.println( "Please specify which flight you want to search :" ) ;
							System.out.println( "search flight <departure date : dd/mm/yyyy> <departure airport code> <arrival airport code>" ) ;
						}
					}
					else if ( check_action[0].equals( "reserve" ) == true )
					{
						if ( check_action.length > 1 )
						{
							if ( check_action[1].equals( "seat" ) == true )
							{
								if ( check_action.length == 3 )
								{
									Flight template_flight = new Flight() ;
									template_flight.setFlight_code( check_action[2] ) ;
									if ( space.readIfExists( template_flight , null , Long.MAX_VALUE ) != null )
									{
										Seat template_seat = new Seat() ;
										template_seat.setFlight_code( check_action[2] ) ;
										template_seat.setReserved( false ) ;
										long transaction_id = tm.create( 60000 ).id ;
										Transaction t = new ServerTransaction( tm , transaction_id ) ;
										Seat result_seat = (Seat) space.takeIfExists( template_seat , t , Long.MAX_VALUE ) ;
										if ( result_seat != null )
										{
											Bank_account bank_account_template = new Bank_account() ;
											System.out.println( "Enter your password :" ) ;
											String mdp = sc.nextLine() ;
											bank_account_template.setAccount_code( mdp ) ;
											Bank_account check_user_money = (Bank_account) space.takeIfExists( bank_account_template , null , Long.MAX_VALUE ) ;
											if ( check_user_money != null )
											{
												if ( check_user_money.getBalance() >= result_seat.getPrice() )
												{
													t.commit() ;
													check_user_money.setBalance( check_user_money.getBalance() - result_seat.getPrice() ) ;
													space.write( check_user_money , null , 60*60*1000 ) ;
													result_seat.setReserved( true ) ;
													space.write( result_seat , null , 60*60*1000 ) ;
													System.out.println( "You've reserved the seat number " + result_seat.getSeat_number() + "." ) ;
												}
												else
												{
													space.write( check_user_money , null , 60*60*1000 ) ;
													t.abort() ;
													System.out.println( "You don't have enough money to buy a seat on that flight (" + ( result_seat.getPrice() - check_user_money.getBalance() ) + " is missing)." ) ;
												}
											}
											else
											{
												t.abort() ;
												System.out.println( "Wrong account password." ) ;
											}
										}
										else
										{
											System.out.println( "There is no more seat left for that flight." ) ;
										}
									}
									else
									{
										System.out.println( "There is no flight with with that code." ) ;
									}
								}
								else
								{
									System.out.println( "Command not valid, please follow this syntax :" ) ;
									System.out.println( "reserve seat <flight code>" ) ;
								}
							}
							else
							{
								System.out.println( "You can only reserve a seat." ) ;
							}
						}
						else
						{
							System.out.println( "Please specify which flight you want to reserve a seat from :" ) ;
							System.out.println( "reserve seat <flight code>" ) ;
						}
					}
					else if ( check_action[0].equals( "delete" ) == true )
					{
						if ( check_action.length > 1 )
						{
							if ( check_action[1].equals( "all" ) == true )
							{
								Airport remove_airport = new Airport() ;
								Flight remove_flight = new Flight() ;
								Seat remove_seat = new Seat() ;
								int nb_airport_deleted = 0 ;
								int nb_flight_deleted = 0 ;
								int nb_seat_deleted = 0 ;
								while ( space.takeIfExists( remove_airport , null , Long.MAX_VALUE ) != null )
								{
									nb_airport_deleted ++ ;
								}
								while ( space.takeIfExists( remove_flight , null , Long.MAX_VALUE ) != null )
								{
									nb_flight_deleted ++ ;
								}
								while ( space.takeIfExists( remove_seat , null , Long.MAX_VALUE ) != null )
								{
									nb_seat_deleted ++ ;
								}
								System.out.println( "Data deleted :" ) ;
								System.out.println( nb_airport_deleted + " airport has been deleted." ) ;
								System.out.println( nb_flight_deleted + " flight has been deleted." ) ;
								System.out.println( nb_seat_deleted + " seat has been deleted." ) ;
							}
							else if ( check_action[1].equals( "airport" ) == true )
							{
								if ( check_action.length == 3 )
								{
									Airport remove_airport = new Airport() ;
									remove_airport.setAirport_code( check_action[2] ) ;
									if ( space.takeIfExists( remove_airport , null , Long.MAX_VALUE ) != null )
									{
										System.out.println( "The airport has been deleted from the database." ) ;
										Flight remove_flight_departure = new Flight() ;
										remove_flight_departure.setDeparture_airport_code( check_action[2] ) ;
										Flight remove_flight_arrival = new Flight() ;
										remove_flight_arrival.setArrival_airport_code( check_action[2] ) ;
										Seat remove_seat = new Seat() ;
										int nb_flight_departure_deleted = 0 ;
										int nb_flight_arrival_deleted = 0 ;
										int nb_seat_deleted = 0 ;
										Flight store_data ;
										while ( ( store_data = (Flight) space.takeIfExists( remove_flight_departure , null , Long.MAX_VALUE ) ) != null )
										{
											nb_flight_departure_deleted ++ ;
											remove_seat.setFlight_code( store_data.getFlight_code() ) ;
											while ( space.takeIfExists( remove_seat , null , Long.MAX_VALUE ) != null )
											{
												nb_seat_deleted ++ ;
											}
										}
										while ( ( store_data = (Flight) space.takeIfExists( remove_flight_arrival , null , Long.MAX_VALUE ) ) != null )
										{
											nb_flight_arrival_deleted ++ ;
											remove_seat.setFlight_code( store_data.getFlight_code() ) ;
											while ( space.takeIfExists( remove_seat , null , Long.MAX_VALUE ) != null )
											{
												nb_seat_deleted ++ ;
											}
										}
										if ( nb_flight_departure_deleted != 0 )
										{
											System.out.println( nb_flight_departure_deleted + " flight scheduled to take off from this airport have been deleted from the database." ) ;
										}
										else
										{
											System.out.println( "There was no flight scheduled to take off from this airport to delete." ) ;
										}
										if ( nb_flight_arrival_deleted != 0 )
										{
											System.out.println( nb_flight_arrival_deleted + " flight scheduled to land on this airport have been deleted from the database." ) ;
										}
										else
										{
											System.out.println( "There was no flight scheduled to land on this airport to delete." ) ;
										}
										if ( nb_flight_departure_deleted != 0 || nb_flight_arrival_deleted != 0 )
										{
											if ( nb_seat_deleted != 0 )
											{
												System.out.println( "For a total of " + nb_seat_deleted + " seat deleted from the database." ) ;
											}
											else
											{
												System.out.println( "There was no seat to delete on those flight." ) ;
											}
										}
										else
										{
											System.out.println( "As there was no flight departure or arrival scheduled to this airport, no seat have been deleted." ) ;
										}
									}
									else
									{
										System.out.println( "This airport doesn't exist." ) ;
									}
								}
								else
								{
									System.out.println( "Right syntax to delete an airport :" ) ;
									System.out.println( "delete flight <flight code>" ) ;
								}
							}
							else if ( check_action[1].equals( "flight" ) == true )
							{
								if ( check_action.length == 3 )
								{
									Flight remove_flight = new Flight() ;
									remove_flight.setFlight_code( check_action[2] ) ;
									if ( space.takeIfExists( remove_flight , null , Long.MAX_VALUE ) != null )
									{
										System.out.println( "The flight has been deleted from the database." ) ;
										Seat remove_seat = new Seat() ;
										remove_seat.setFlight_code( check_action[2] ) ;
										int nb_seat_deleted = 0 ;
										while ( space.takeIfExists( remove_seat , null , Long.MAX_VALUE ) != null )
										{
											nb_seat_deleted ++ ;
										}
										if ( nb_seat_deleted != 0 )
										{
											System.out.println( nb_seat_deleted + " seat on that flight have been deleted from the database." ) ;
										}
										else
										{
											System.out.println( "There was no seat to delete on that flight." ) ;
										}
									}
									else
									{
										System.out.println( "This flight doesn't exist." ) ;
									}
								}
								else
								{
									System.out.println( "Right syntax to delete a flight :" ) ;
									System.out.println( "delete flight <flight code>" ) ;
								}
							}
							else if ( check_action[1].equals( "seat" ) == true )
							{
								if ( check_action.length == 4 )
								{
									Flight template_flight = new Flight() ;
									template_flight.setFlight_code( check_action[3] ) ;
									if ( space.readIfExists( template_flight , null , Long.MAX_VALUE ) != null )
									{
										try
										{
											int num_seat = Integer.parseInt( check_action[2] ) ;
											Seat deleted_seat = new Seat( num_seat , check_action[3] , null ) ;
											deleted_seat.setReserved( null ) ;
											if ( space.takeIfExists( deleted_seat , null , Long.MAX_VALUE ) != null )
											{
												System.out.println( "The seat has been deleted from the database." ) ;
											}
											else
											{
												System.out.println( "This seat doesn't exist." ) ;
											}
										}
										catch (NumberFormatException e)
										{
											System.out.println( "The seat number isn't valid." ) ;
										}
									}
									else
									{
										System.out.println( "You can't delete a seat on a flight that doesn't exist." ) ;
									}
								}
								else
								{
									System.out.println( "Right syntax to delete a seat :" ) ;
									System.out.println( "delete seat <seat number> <flight code>" ) ;
								}
							}
							else
							{
								System.out.println( "You can only delete all, an airport, a flight or a seat." ) ;
							}
						}
						else
						{
							System.out.println( "Please specify what you want to delete :" ) ;
							System.out.println( "delete all" ) ;
							System.out.println( "delete airport <airport code>" ) ;
							System.out.println( "delete flight <flight code>" ) ;
							System.out.println( "delete seat <seat number> <flight code>" ) ;
						}
					}
					else if ( check_action[0].equals( "quit" ) == false )
					{
						System.out.println( "Available action :" ) ;
						System.out.println( "change password <account password>" ) ;
						System.out.println( "add money <amount>" ) ;
						System.out.println( "add airport <airport code> <city name>" ) ;
						System.out.println( "add flight <flight code> <departure date : dd/mm/yyyy> <departure airport code> <arrival airport code>" ) ;
						System.out.println( "add seat <seat number> <flight code> <seat price>" ) ;
						System.out.println( "search flight <departure date : dd/mm/yyyy> <departure airport code> <arrival airport code>" ) ;
						System.out.println( "reserve seat <flight code>" ) ;
						System.out.println( "delete all" ) ;
						System.out.println( "delete airport <airport code>" ) ;
						System.out.println( "delete flight <flight code>" ) ;
						System.out.println( "delete seat <seat number> <flight code>" ) ;
						System.out.println( "quit" ) ;
					}
				}
				sc.close() ;
				Bank_account bank_account_template = new Bank_account() ;
				bank_account_template.setAccount_code( account_password ) ;
				space.takeIfExists( bank_account_template , null , Long.MAX_VALUE ) ;
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	private static void usage()
	{
		System.out.println( "flight_reservation/" ) ;
		System.out.println( "java -jar target/flight_reservation-1.0.jar <server_host_IP> <server_port_number> " ) ;
	}
}
