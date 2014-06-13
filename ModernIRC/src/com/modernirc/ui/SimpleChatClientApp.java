package com.modernirc.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.modernirc.client.ClientToServerThread;
import com.modernirc.server.BroadcastThread;

public class SimpleChatClientApp {
    static String[] ConnectOptionNames = { "Connect" };	
    static String   ConnectTitle = "Connection Information";
    Socket socketClientServer;
    int serverPort;
    String serverName;
    String clientName;
    String clientPwd;
	private static ClientToServerThread clientToServerThread;
	private static Scanner sc;
	private SimpleChatFrameClient frame;
	public StyledDocument documentModel=new DefaultStyledDocument();
	DefaultListModel<String> clientListModel=new DefaultListModel<String>();
	
    public static final String BOLD_ITALIC = "BoldItalic";
    public static final String GRAY_PLAIN = "Gray";

	public static DefaultStyledDocument defaultDocumentModel() {
		DefaultStyledDocument res=new DefaultStyledDocument();
	    
	    Style styleDefault = (Style) res.getStyle(StyleContext.DEFAULT_STYLE);
	    
	    res.addStyle(BOLD_ITALIC, styleDefault);
	    Style styleBI = res.getStyle(BOLD_ITALIC);
	    StyleConstants.setBold(styleBI, true);
	    StyleConstants.setItalic(styleBI, true);
	    StyleConstants.setForeground(styleBI, Color.white);	    

	    res.addStyle(GRAY_PLAIN, styleDefault);
        Style styleGP = res.getStyle(GRAY_PLAIN);
        StyleConstants.setBold(styleGP, false);
        StyleConstants.setItalic(styleGP, false);
        StyleConstants.setForeground(styleGP, Color.lightGray);

		return res;
	}
			
	public SimpleChatClientApp(){
		
	}
	
	public void displayClient() {

		this.frame=new SimpleChatFrameClient(getClientToServerThread(), clientListModel, documentModel);
		this.frame.setTitle(this.frame.getTitle()+" : "+clientName+" connected to "+serverName+":"+serverPort);
		((JFrame)this.frame).setVisible(true);
		this.frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				quitApp(SimpleChatClientApp.this);
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void hideClient() {
		
		// Init GUI
		((JFrame)this.frame).setVisible(false);
	}
	
    void displayConnectionDialog() {
    	ConnectionPanelOld connectionPanel=new ConnectionPanelOld();
		if (JOptionPane.showOptionDialog(null, connectionPanel, ConnectTitle,
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, ConnectOptionNames, ConnectOptionNames[0]) == 0) {
			serverPort=Integer.parseInt(connectionPanel.getServerPortField().getText());
			serverName=connectionPanel.getServerField().getText();
			clientName=connectionPanel.getUserNameField().getText();
			clientPwd=connectionPanel.getPasswordField().getText();
		}
	}
    
    private void connectClient() {
		System.out.println("Establishing connection. Please wait ...");
		try {
			socketClientServer = new Socket(this.serverName, this.serverPort);
			// Start connection services
			setClientToServerThread(new ClientToServerThread(documentModel, clientListModel,socketClientServer,clientName, clientPwd));
			getClientToServerThread().start();

			System.out.println("Connected: " + socketClientServer);
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
	}
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final SimpleChatClientApp app = new SimpleChatClientApp();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app.displayConnectionDialog();

					app.connectClient();
					
					app.displayClient();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			
		});
		
		sc = new Scanner(System.in);
		String line="";
		while(!line.equals(".bye")){
			line=sc.nextLine();			
		}
		
		quitApp(app);
	}

	private static void quitApp(final SimpleChatClientApp app) {
		try {
			app.getClientToServerThread().quitServer();
			app.socketClientServer.close();
			app.hideClient();
			System.out.println("SimpleChatClientApp : fermée");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ClientToServerThread getClientToServerThread() {
		return clientToServerThread;
	}

	public static void setClientToServerThread(ClientToServerThread clientToServerThread) {
		SimpleChatClientApp.clientToServerThread = clientToServerThread;
	}

}
