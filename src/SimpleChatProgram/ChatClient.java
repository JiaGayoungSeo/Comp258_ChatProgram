package SimpleChatProgram;





import java.io.*;
import java.util.Scanner;

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
            if(!isConnected()){
                this.setHost(userHostSetting()); 
            }else{
                clientUI.display("Can't set host while connection is on");
            }
            
        }else if(s.startsWith("#setport")){
            if(!isConnected()){
                this.setPort(userPortSetting());
            }else{
                clientUI.display("Can't set port while connection is on");
            }
            
        }else if(s.startsWith("#gethost")){
            clientUI.display("The host: "+this.getHost());
            
        }else if(s.startsWith("#getport")){
            clientUI.display("The port: "+this.getPort());
            
        }else if(s.startsWith("#join")){
            sendToServer(s);
        }else if(s.startsWith("#yell")){
            sendToServer(s);
        }else if(s.startsWith("#ison")){
            sendToServer(s);
        }else if(s.startsWith("#intercom")){
            sendToServer(s);
        }
        else{
            clientUI.display("We couldn't find the command. Try again!");
        }
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client. This is in handlecommand method");
      quit();
    }
  }
  
  public String userHostSetting(){
      Scanner input = new Scanner(System.in);
      clientUI.display("Type host WARNING! IT MUCST BE APPROPRIATE IP ADDRESS OR LOCAL HOST");
      return input.next();
  }
  
  public int userPortSetting(){
      Scanner input = new Scanner(System.in);
      clientUI.display("Type port number");
      return input.nextInt();
  }
  
  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
      if(!isConnected()){
          if(message.startsWith("#")){
          handleCommand(message);
          }
          clientUI.display("Waiting for connection(#login)...");
      }
      else if(isConnected()){
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
