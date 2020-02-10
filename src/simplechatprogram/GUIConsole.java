/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplechatprogram;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author seo8556
 */
public class GUIConsole extends JFrame implements ChatIF{
    private ChatClient chatClient;
    //private ArrayList<String> roomList;
    
    private JButton closeB = new JButton("Close");
    private JButton openB = new JButton("Open");
    private JButton sendB = new JButton("Send");
    private JButton quitB = new JButton("Quit");
    private JButton joinB = new JButton("Join");
    private JButton pmB = new JButton("PM");
    private JButton yellB = new JButton("Yell");
    private JButton intercomB = new JButton("Intercom");
    private JButton isonB = new JButton("Ison");

    


    private JTextField portTxF = new JTextField("5555");
    private JTextField hostTxF = new JTextField("127.0.0.1");
    private JTextField messageTxF = new JTextField("");
    private JTextField joinTxF = new JTextField("");
    private JTextField pmTxF = new JTextField("");
    private JTextField yellTxF = new JTextField("");
    private JTextField intercomTxF = new JTextField("");
    private JTextField isonTxF = new JTextField("");



    private JLabel portLB = new JLabel("Port: ", JLabel.RIGHT);
    private JLabel hostLB = new JLabel("Host: ", JLabel.RIGHT);
    private JLabel messageLB = new JLabel("Message: ", JLabel.RIGHT);

    private JTextArea messageList = new JTextArea();
    private LoginUI loginUI;
    
    final public static int DEFAULT_PORT = 5555;
   
    
    public GUIConsole(String host, int port){
                
        
		super("Simple Chat GUI");
		setSize(300, 400);
		
		setLayout( new BorderLayout(5,5));
                
		Panel bottom = new Panel();
                Panel commandPanel = new Panel();
                
                JComboBox joinComboBox = new JComboBox();
                
		add( "Center", messageList );
		add( "South" , bottom);
                add("East" , commandPanel);
		
		bottom.setLayout( new GridLayout(5,2,5,5));
                commandPanel.setLayout(new GridLayout(5,2));

                
		bottom.add(hostLB); 		
                bottom.add(hostTxF);
		bottom.add(portLB); 		
                bottom.add(portTxF);
		bottom.add(messageLB); 	
                bottom.add(messageTxF);
		bottom.add(openB); 		
                bottom.add(sendB);
		bottom.add(closeB);
                bottom.add(quitB);
                
                commandPanel.add(joinB);
                commandPanel.add(joinComboBox);
                //commandPanel.add(joinTxF);
                commandPanel.add(pmB);
                commandPanel.add(pmTxF);
                commandPanel.add(yellB);
                commandPanel.add(yellTxF);
                commandPanel.add(intercomB);
                commandPanel.add(intercomTxF);
                commandPanel.add(isonB);
                commandPanel.add(isonTxF);
                
                
                
		setVisible(true);
                openB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loginUI = new LoginUI(chatClient); 
                        try{
                            chatClient.openConnection();
                            display("This is connected at "+chatClient.getHost());
                            joinComboBox.addItem("common");
                        }catch(IOException ioe){;
                            display("Server is not open. Waiting for connection...\n");
                        }
                    }
                });
                sendB.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                    
                        send(messageTxF.getText());
                        //display(messageTxF.getText()+"\n" );
                        messageTxF.setText("");
                    
                     }

                });
                closeB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            chatClient.closeConnection();
                        } catch (IOException ex) {
                            Logger.getLogger(GUIConsole.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                quitB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            chatClient.quit();
                        }
                        catch(Exception exception){

                        }}
                });
                
                joinB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       String string = joinTxF.getText();
                       send("#join "+string);
                       joinTxF.setText("");
                       joinComboBox.addItem(string);
                    }
                });
                
                pmB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       String string = pmTxF.getText();
                       send("#pm "+string);
                       pmTxF.setText("");
                    }
                });
                
                yellB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       String string = yellTxF.getText();
                       send("#yell "+string);
                       yellTxF.setText("");
                    }
                });
                
                intercomB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String string = intercomTxF.getText();
                        send("#intercom "+string);
                        intercomTxF.setText("");
                    }
                });
                
                isonB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String string = isonTxF.getText();
                        send("#ison "+string);
                        isonTxF.setText("");
                    }
                });
        
               try 
                {
                  chatClient= new ChatClient(host, port, this);
                  
                } 
                catch(IOException exception) 
                {
                  System.out.println("Error: Can't setup connection!"
                            + " Terminating client.");
                  System.exit(1);
                }

    }
    
    
    public void send(String message){
        try{
            chatClient.sendToServer(message);        
        }catch(IOException ioe){
            display("Can't send a message to server\n");
        }
    }
    public void display( String message ){
		messageList.insert(message, 0);
    }
    
    public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
        if(message.startsWith("#")){
            chatClient.handleCommand(message);
        }else{
            chatClient.handleMessageFromClientUI(message);

        }
            
        
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }
    
   
    
    public static void main(String[] args) 
  {
    String host = "";
    int port = 0;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    GUIConsole clientConsole= new GUIConsole(host, DEFAULT_PORT);
    clientConsole.accept();  //Wait for console data
    
  }

}
