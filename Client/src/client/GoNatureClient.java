package client;
import java.io.IOException;

import entity.Employee;
import gui.GUIControl;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;
import ocsf.client.AbstractClient;
/*
 * This class controls the communication between the client and the server using handleMessageFromClientUI and handleMessageFromClient
 */
public class GoNatureClient extends AbstractClient {
	  //Instance variables **********************************************
	  
	  /**
	   * The interface type variable.  It allows the implementation of 
	   * the display method in the client.
	   */
	  public static boolean awaitResponse = false;
	  private GUIControl guiControl=GUIControl.getInstance();

	  //Constructors ****************************************************
	  
	  /**
	   * Constructs an instance of the chat client.
	   *
	   * @param host The server to connect to.
	   * @param port The port number to connect on.
	   * @param clientUI The interface type variable.
	   */
		 
	  public GoNatureClient(String host, int port) throws IOException {
	    super(host, port); //Call the superclass constructor
	    openConnection();
	  }

	  //Instance methods ************************************************
	    
	  /**
	   * This method handles all data that comes in from the server.
	   *
	   * @param msg The message from the server.
	 * @throws Exception 
	   */
	  public void handleMessageFromServer(Object msg) 
	  {
		  System.out.println("--> handleMessageFromServer");
		  if(msg instanceof ServerMessage) {
			  ServerMessage serverMsg=(ServerMessage)msg;
			  switch(serverMsg.getType()) {
			  case LOGIN:
				  guiControl.setServerMsg(serverMsg);
				  break;
			  case SERVER_ERROR:
				  if(serverMsg.getMessage()==null) {
					  GUIControl.popUpError("Server encountered an error!");
					  guiControl.setServerMsg(serverMsg);
				  }
				  else {
					  GUIControl.popUpError((String)serverMsg.getMessage());
					  try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  System.exit(0);
				  }
				  break;
			  case PARK_LIST:
				  guiControl.setServerMsg(serverMsg);
				  break;
			  case LOGOUT_SUCCESS:
				  GUIControl.popUpMessage("Logged out");
				  break;
			  }
		  }
		  awaitResponse=false;
		  
	  }

	  /**
	   * This method handles all data coming from the UI            
	   *
	   * @param message The message from the UI.    
	   */
	  
	  public void handleMessageFromClientUI(Object message)  
	  {
	    try
	    {
	    	openConnection();//in order to send more than one message
	    	if(message instanceof ClientMessage) {
	    		if(((ClientMessage) message).getType().equals(ClientMessageType.DISCONNECTED)){
	    			sendToServer(message);
	    			return;
	    		}
	    	}
	       	awaitResponse = true;
	    	sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	      quit();
	    }
	  }

	  
	  /**
	   * This method terminates the client.
	   */
	  public void quit()
	  {
	    try
	    {
	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	  }
}
