import java.io.*;
import java.net.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Server extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private ServerSocket server;
	private Socket connection;
	
	//Constructor
	public Server(){
		super("Nico Ladsen");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				}
				
				);
		add(userText,BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(500,300);
		setVisible(true);
	}

	//Set up and run the server
	
	public void startRunning(){
		
		try{
			server = new ServerSocket(6789,2);
			while(true){
				try{
					waitForConnection();
					setupStreams();
					whileChatting();
				}catch(EOFException eofException){
					showMessage("\n Server ended the connection");
				}finally{
					closeCrap();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//Wait for connection,
	
	private void waitForConnection() throws IOException{
		showMessage("\n Waiting for someone to connect");
		connection = server.accept();
		showMessage("\n Now connected to" + connection.getInetAddress().getHostName());
		
	}
	
	//Get stream to send and receive data
	
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		
		showMessage("\n Dude your streams are now good to go!");
	}
	
	//While chatting method
	
	private void whileChatting() throws IOException{
		String message = "\n You're now connected";
		sendMessage(message);
		ableToType(true);
		
		do{
			//Have a conversation
			try{
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\n Idk wtf that user send");
			}
			
		}while(!message.equals("Client - END"));{
			
		}
	}
	
	//CloseCrap Method
	
	private void closeCrap(){
		showMessage("\n Closing connection");
		ableToType(false);
		
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//The send message method (to the client)
	
	private void sendMessage(String message){
		try{
			output.writeObject("\n Server - "+ message);
			output.flush();
			showMessage("Server -" + message);
		}catch(IOException ioException){
			chatWindow.append("\n Can't send that message");
		}
	}
	
	//Show message - Updates chatwindow
	
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(text);
					}
				}
				);		
	}
	
	//Able to type (When there is a connection)
	
	public void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						userText.setEditable(tof);;
				}
			}
		);		
	}
}
