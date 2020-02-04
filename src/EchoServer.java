

import java.io.*;


/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.

 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    String message = msg.toString();
    if(message.startsWith("#")){
        handleCommandFromClient(message, client);
    }else{
        System.out.println("Message received: " + msg + " from " + client);
        this.sendToAllClients(msg);
    }
  }
    
    
    
  public void handleCommandFromClient(String msg, ConnectionToClient client){
     
      if(msg.startsWith("#login")){
          String userId = msg.substring(msg.indexOf(" ")+1,msg.length());
          userId=userId.trim();
          System.out.println(">>>"+userId+" Entered!");  
          client.setInfo("userId",userId);
          sendToAllClients(userId+" Just logged in! ");
      }
      else if(msg.startsWith("#pm")){
          String target ="";
          String pmMessage="";
          String msgWOCommand = msg.substring(msg.indexOf(" ")+1,msg.length());
          target = msgWOCommand.substring(0, msgWOCommand.indexOf(" ")); // grab the user's id to send the pm
          pmMessage = msgWOCommand.substring(msgWOCommand.indexOf(" ")+1,msgWOCommand.length());
          sentToAClient(pmMessage,target,client);
      }else if(msg.startsWith("#join")){
          String room = msg.substring(msg.indexOf(" ")+1,msg.length());
          String userId = client.getInfo("userId").toString();
          room=room.trim();
          System.out.println(">>>"+client.getInfo("userId")+" joined in "+room);  
          client.setInfo("room",room);
          sendToAllClients(userId+" Just joined room: "+ room);
      }else if(msg.startsWith("#yell")){
          sentToAllClientsInRoom(msg, client.getInfo("room").toString(), client);
      } else if(msg.startsWith("#ison")){
          
          String userId = msg.substring(msg.indexOf(" ")+1,msg.length());
          userId=userId.trim();
          System.out.println(userId);
          //onConnection(userId,client);
      }
  }
  /*
  public void onConnection(String userId,ConnectionToClient client){
     
      Thread[] clientThreadList = getClientConnections();
       
       for(int i=0; i<clientThreadList.length;i++){
          ConnectionToClient user = ((ConnectionToClient)clientThreadList[i]);
          if(user.getInfo(userId).equals(userId)){
              String msg = user.getInfo("userId") + "is on in "+user.getInfo("room");
              sendToAllClients(msg);
          }
       }
       
  }
  */
  public void sentToAClient(Object message, String target, ConnectionToClient client){
    
    Thread[] clientThreadList = getClientConnections();

        for (int i=0; i<clientThreadList.length; i++)
        {
          ConnectionToClient user = ((ConnectionToClient)clientThreadList[i]);
          if(user.getInfo("userId").equals(target)){
            try
            {
                String msg = client.getInfo("userId")+" has sent you a pm: "+message;
                user.sendToClient(msg);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
          }
          
        }
  
  }
  
   public void sentToAllClientsInRoom(Object message, String room, ConnectionToClient client){
    
    Thread[] clientThreadList = getClientConnections();

        for (int i=0; i<clientThreadList.length; i++)
        {
          ConnectionToClient user = ((ConnectionToClient)clientThreadList[i]);
          if(user.getInfo("room").equals(room)){
            try
            {
                user.sendToClient("This message can be seen only in "+room);
                user.sendToClient(message);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
          }
          
        }
  
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.This is serverStopped() method in Echo Server class");
  }
  
  protected void serverClosed(){
      System.out.println("This is serverClosed method in Echo Server class");
  }
  
  protected void listeningException(Throwable exception) {
      System.out.println("listening Exception class in Echo Server class");
             
  }
  
   protected void clientConnected(ConnectionToClient client) {
      
        System.out.println("Client "+client.getName()+" is connected "+ client.getInetAddress());  
   }
  
   
   synchronized protected void clientDisconnected(
    ConnectionToClient client) {
           System.out.println("Client "+client.getName()+" is disconnected "+ client.getInetAddress());  

   }
   
  protected void clientException(ConnectionToClient client,Throwable exception){
      System.out.println("The client "+client.getName()+" has closed connection.");
  }
  
  
  //Class methods ***************************************************

  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
