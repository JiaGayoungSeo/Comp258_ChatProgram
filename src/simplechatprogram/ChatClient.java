




import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *

 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

    public ChatClient() {
        super(null, 0);
    }
    
    
  public void handleCommand(String s){
     try
    {
        if(s.startsWith("#login")){
                openConnection();
                sendToServer(s);

        }else if(s.startsWith("#pm")){
            sendToServer(s);
        }else if(s.startsWith("#quit")){
            quit();
        }else if(s.startsWith("#logoff")){
            closeConnection();
        }else if(s.startsWith("#sethost")){
            
        }else if(s.startsWith("#setpost")){
            
        }else if(s.startsWith("#getport")){
            
        }else if(s.startsWith("#gethost")){
            
        }else{
            clientUI.display("We couldn't find the command. Try again!");
        }
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  
  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
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
  
  protected void connectionClosed() {
      System.out.println("Connection has been closed");
  }
}
//End of ChatClient class
