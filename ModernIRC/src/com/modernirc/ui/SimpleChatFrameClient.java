package com.modernirc.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import com.modernirc.client.IntfSenderModel;

/**
 *
 * @author DDEBERT
 */
public class SimpleChatFrameClient extends JFrame {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Variables declaration - do not modify                     
    private javax.swing.JLabel userNameLabel;
	private javax.swing.JLabel aideLabel;
    private javax.swing.JLabel applicationTitleLabel;
    private javax.swing.JLabel channelChangeLabel;
    private javax.swing.JLabel channelLabel;
    private javax.swing.JLabel closeButton;
    private javax.swing.JButton envoyerButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> userList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea msgReceive;
    private javax.swing.JTextPane sendMsg;
    private javax.swing.JLabel maximizeButton;
    private javax.swing.JLabel minimizeButton;
    private javax.swing.JLabel modernIRCIcon;
    private javax.swing.JPanel orangePanel;
    private javax.swing.JLabel openChatRoomLabel;
    private javax.swing.JLabel parametreLabel;
	private static Document documentModel;
	private static ListModel<String> listModel;
	IntfSenderModel sender;
	private String senderName;	
	private final ResourceAction sendAction = new SendAction();
	private final ResourceAction lockAction = new LockAction();
	private boolean isScrollLocked=true;
    // End of variables declaration         
    
    int posX=0;
    int posY=0;
    
    /**
     * Creates new form SimpleChatFrameClient
     */
    public SimpleChatFrameClient() {
		this(null, new DefaultListModel<String>(), SimpleChatClientApp.defaultDocumentModel());
	}

	/**
	 * Create the frame.
	 */
	public SimpleChatFrameClient(IntfSenderModel sender, ListModel<String> clientListModel, Document documentModel) {
		this.sender=sender;
		SimpleChatFrameClient.setDocumentModel(documentModel);
		this.setListModel(clientListModel);
        //this.setExtendedState(MAXIMIZED_BOTH); 
        this.addMouseListener(new MouseAdapter()
        {
           public void mousePressed(MouseEvent e)
           {
              posX=e.getX();
              posY=e.getY();
           }
        });

        this.addMouseMotionListener(new MouseAdapter()
        {
             public void mouseDragged(MouseEvent evt)
             {
            	 //sets frame position when mouse dragged			
            	 setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);

             }
        });
                
        this.setUndecorated(true);
        initComponents();
    }

    public static void sendMessage(String user, String line, Style styleBI,
			Style styleGP) {
        try {
			getDocumentModel().insertString(getDocumentModel().getLength(), user+" : ", styleBI); //$NON-NLS-1$
			getDocumentModel().insertString(getDocumentModel().getLength(), line+"\n", styleGP); //$NON-NLS-1$
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}				        	
	}
	
	public void sendMessage() {
		sender.setMsgToSend(sendMsg.getText());
	}
                      
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        orangePanel = new javax.swing.JPanel();
        applicationTitleLabel = new javax.swing.JLabel();
        parametreLabel = new javax.swing.JLabel();
        aideLabel = new javax.swing.JLabel();
        maximizeButton = new javax.swing.JLabel();
        closeButton = new javax.swing.JLabel();
        minimizeButton = new javax.swing.JLabel();
        channelLabel = new javax.swing.JLabel();
        channelChangeLabel = new javax.swing.JLabel();
        openChatRoomLabel = new javax.swing.JLabel();
        modernIRCIcon = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userNameLabel = new javax.swing.JLabel();
        
        //Création de la liste utilisateurs
        userList = new JList<String>(getListModel());
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int iFirstSelectedElement=((JList)e.getSource()).getSelectedIndex();
				if(iFirstSelectedElement>=0 && iFirstSelectedElement<getListModel().getSize()){
					senderName=getListModel().getElementAt(iFirstSelectedElement);
					getUserNameLabel().setText(senderName);
				}
				else{
					getUserNameLabel().setText("?"); //$NON-NLS-1$
				}
			}
		});
        
        jScrollPane3 = new javax.swing.JScrollPane();
        msgReceive = new javax.swing.JTextArea((StyledDocument)getDocumentModel());
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sendMsg = new javax.swing.JTextPane();
        sendMsg.setBackground(Color.BLACK);
        sendMsg.setForeground(Color.WHITE);
        envoyerButton = new javax.swing.JButton(sendAction);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ModernIRC");
        setBackground(new java.awt.Color(50, 50, 50));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("SimpleChatFrameClient"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(50, 50, 50));

        orangePanel.setBackground(new java.awt.Color(0, 186, 255));

        javax.swing.GroupLayout oarngePanelLayout = new javax.swing.GroupLayout(orangePanel);
        orangePanel.setLayout(oarngePanelLayout);
        oarngePanelLayout.setHorizontalGroup(
            oarngePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        oarngePanelLayout.setVerticalGroup(
            oarngePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        applicationTitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        applicationTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        applicationTitleLabel.setText("ModernIRC");

        parametreLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        parametreLabel.setForeground(new java.awt.Color(187, 187, 187));
        parametreLabel.setText("PARAMETRES");
        parametreLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        parametreLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                parametreLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                parametreLabelMouseExited(evt);
            }
        });

        aideLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        aideLabel.setForeground(new java.awt.Color(187, 187, 187));
        aideLabel.setText("AIDE");
        aideLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        aideLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aideLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                aideLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                aideLabelMouseReleased(evt);
            }
        });

        maximizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\maximize16px.png"))); // NOI18N
        maximizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                maximizeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                maximizeButtonMouseExited(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\close16px.png"))); // NOI18N
        closeButton.setToolTipText("Fermer l'application");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButtonMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                closeButtonMouseReleased(evt);
            }
        });

        minimizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\minimize16px.png"))); // NOI18N
        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeButtonMouseExited(evt);
            }
        });
        minimizeButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                minimizeButtonKeyReleased(evt);
            }
        });

        channelLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        channelLabel.setForeground(new java.awt.Color(255, 255, 255));
        channelLabel.setText("GENERAL");

        channelChangeLabel.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        channelChangeLabel.setForeground(new java.awt.Color(255, 255, 255));
        channelChangeLabel.setText("#general");
        channelChangeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        openChatRoomLabel.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        openChatRoomLabel.setForeground(new java.awt.Color(0, 186, 255));
        openChatRoomLabel.setText("+ajouter un salon");
        openChatRoomLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        openChatRoomLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                openChatRoomLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                openChatRoomLabelMouseExited(evt);
            }
        });

        modernIRCIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\ModernIRC32px.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(50, 50, 50));

        userList.setBackground(new java.awt.Color(29, 29, 29));
        userList.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        userList.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        userList.setForeground(new java.awt.Color(255, 255, 255));
        userList.setSelectionBackground(new java.awt.Color(0, 186, 255));
        jScrollPane1.setViewportView(userList);

        msgReceive.setBackground(new java.awt.Color(29, 29, 29));
        msgReceive.setForeground(Color.WHITE);
        msgReceive.setColumns(20);
        msgReceive.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        msgReceive.setRows(5);
        msgReceive.setWrapStyleWord(true);
        msgReceive.setCaretColor(new java.awt.Color(0, 186, 255));
        msgReceive.setFocusable(false);
        jScrollPane3.setViewportView(msgReceive);
        
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 186, 255));
        jLabel1.setText("En ligne");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 186, 255));
        jLabel2.setText("Conversation");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel2)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)))
        );

        jPanel3.setBackground(new java.awt.Color(50, 50, 50));

        userNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        userNameLabel.setForeground(new java.awt.Color(0, 186, 255));
        userNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        sendMsg.setBackground(new java.awt.Color(29, 29, 29));
        sendMsg.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        sendMsg.setLogicalStyle(null);
        jScrollPane2.setViewportView(sendMsg);

        envoyerButton.setBackground(new java.awt.Color(0, 186, 255));
        envoyerButton.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        envoyerButton.setForeground(new java.awt.Color(255, 255, 255));
        envoyerButton.setText("Envoyer");
        envoyerButton.setToolTipText("Envoyer le message");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(envoyerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(envoyerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userNameLabel))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(channelChangeLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(orangePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(channelLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openChatRoomLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(modernIRCIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(applicationTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(parametreLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(aideLabel)
                .addGap(18, 18, 18)
                .addComponent(minimizeButton)
                .addGap(11, 11, 11)
                .addComponent(maximizeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(maximizeButton)
                                    .addComponent(closeButton)
                                    .addComponent(minimizeButton))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(parametreLabel)
                                    .addComponent(aideLabel)))
                            .addComponent(modernIRCIcon)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(applicationTitleLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(channelLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(channelChangeLabel)
                            .addComponent(openChatRoomLabel)))
                    .addComponent(orangePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void closeButtonMouseReleased(java.awt.event.MouseEvent evt) {                                          
        System.exit(0);
    }                                         

    private void parametreLabelMouseEntered(java.awt.event.MouseEvent evt) {                                            
        parametreLabel.setForeground(Color.white);
    }                                           

    private void parametreLabelMouseExited(java.awt.event.MouseEvent evt) {                                           
        Color fg;
        fg = new Color(187,187,187);
        parametreLabel.setForeground(fg);
    }                                          

    private void aideLabelMouseEntered(java.awt.event.MouseEvent evt) {                                       
        aideLabel.setForeground(Color.white);
    }                                      

    private void aideLabelMouseReleased(java.awt.event.MouseEvent evt) {                                        

    }                                       

    private void aideLabelMouseExited(java.awt.event.MouseEvent evt) {                                      
        Color fg;
        fg = new Color(187,187,187);
        aideLabel.setForeground(fg);    }                                     

    private void minimizeButtonKeyReleased(java.awt.event.KeyEvent evt) {                                           
    
    	
    }                                          

    private void minimizeButtonMouseEntered(java.awt.event.MouseEvent evt) {                                            
        minimizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\minimizewhite16px.png")));
    }                                           

    private void minimizeButtonMouseExited(java.awt.event.MouseEvent evt) {                                           
        minimizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\minimize16px.png")));
    }                                          

    private void maximizeButtonMouseEntered(java.awt.event.MouseEvent evt) {                                            
        maximizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\maximizewhite16px.png")));
    }                                           

    private void maximizeButtonMouseExited(java.awt.event.MouseEvent evt) {                                           
        maximizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\maximize16px.png")));
    }                                          

    private void closeButtonMouseEntered(java.awt.event.MouseEvent evt) {                                         
        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\closewhite16px.png")));
    }                                        

    private void closeButtonMouseExited(java.awt.event.MouseEvent evt) {                                        
        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("..\\..\\..\\resources\\com\\modernirc\\ui\\close16px.png")));
    }                                       

    private void openChatRoomLabelMouseEntered(java.awt.event.MouseEvent evt) {                                               
        openChatRoomLabel.setForeground(Color.white);
    }                                              

    private void openChatRoomLabelMouseExited(java.awt.event.MouseEvent evt) {                                              
        Color fg;
        fg = new Color(0,186,255);
        openChatRoomLabel.setForeground(fg);                                        
    }                                             

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(SimpleChatFrameClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(SimpleChatFrameClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(SimpleChatFrameClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(SimpleChatFrameClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            SimpleChatFrameClient simpleChatFrameClient=new SimpleChatFrameClient();
            simpleChatFrameClient.setVisible(true);
            simpleChatFrameClient.setLocationRelativeTo(null);
        }
        });
    }
    
    public javax.swing.JLabel getUserNameLabel() {
 		return userNameLabel;
 	}

 	public void setUserNameLabel(javax.swing.JLabel userNameLabel) {
 		this.userNameLabel = userNameLabel;
 	}
 	
 	public ResourceAction getLockAction() {
		return lockAction;
	}

	public static Document getDocumentModel() {
		return documentModel;
	}

	public static void setDocumentModel(Document documentModel) {
		SimpleChatFrameClient.documentModel = documentModel;
	}

	public static ListModel<String> getListModel() {
		return listModel;
	}

	public void setListModel(ListModel<String> listModel) {
		SimpleChatFrameClient.listModel = listModel;
	}

	private abstract class ResourceAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ResourceAction() {
		}
	}
 	
 	private class SendAction extends ResourceAction{	

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SendAction(){
			putValue(NAME, Messages.getString("SimpleChatFrameClient.3")); //$NON-NLS-1$
			putValue(SHORT_DESCRIPTION, Messages.getString("SimpleChatFrameClient.2")); //$NON-NLS-1$
		}
		public void actionPerformed(ActionEvent e) {
			sendMessage();
		}
	}
	
	private class LockAction extends ResourceAction{	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public LockAction(){
			putValue(NAME, Messages.getString("SimpleChatFrameClient.1")); //$NON-NLS-1$
			putValue(SHORT_DESCRIPTION, Messages.getString("SimpleChatFrameClient.0")); //$NON-NLS-1$
		}
		public void actionPerformed(ActionEvent e) {
			isScrollLocked=(!isScrollLocked);
		}
	}
	
}
