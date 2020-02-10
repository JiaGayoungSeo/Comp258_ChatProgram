/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplechatprogram;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.event.*;
import java.io.IOException;
/**
 *
 * @author seo8556
 */
public class LoginUI extends JFrame {
    public boolean confirm = false;
    public JTextField idText;
    public JTextField pwText;
    public JButton loginBtn, signUpBtn;
    public MemberUI mem;
    public JButton ipBtn;
    public ChatClient client;
    public GUIConsole guiClientConsole;

    public LoginUI(ChatClient client) {
        setTitle("Login Window");
      
        this.client = client;
        setBounds(100, 100, 335, 218);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(12, 10, 295, 160);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("ID");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(60, 55, 57, 15);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("PW");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(60, 86, 57, 15);
        panel.add(lblNewLabel_1);

        idText = new JTextField();
        idText.setBounds(129, 52, 116, 21);
        panel.add(idText);
        idText.setColumns(10);

        pwText = new JTextField();
        
        pwText.setBounds(129, 83, 116, 21);
        panel.add(pwText);
        pwText.setColumns(10);

        loginBtn = new JButton("LOGIN");
        loginBtn.addActionListener((ActionEvent arg0) -> {
        });
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {    
               String userId = idText.getText();
               String userPw = pwText.getText();
                msgSummit(userId);
                dispose();
            }
        });
        loginBtn.setBounds(50, 111, 97, 23);
        panel.add(loginBtn);

        signUpBtn = new JButton("REGISTER");
        signUpBtn.addActionListener((ActionEvent e) -> {
        });
        signUpBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                mem = new MemberUI(client);
            }
        });
        signUpBtn.setBounds(149, 111, 97, 23);
        panel.add(signUpBtn);

        JLabel lblNewLabel_2 = new JLabel("Login");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(12, 10, 78, 15);
        panel.add(lblNewLabel_2);

        ipBtn = new JButton("ip address");
        ipBtn.addActionListener((ActionEvent arg0) -> {
            setVisible(false);
        });
        ipBtn.setBounds(93, 6, 97, 23);
        panel.add(ipBtn);
        setVisible(true);
    }

    private void msgSummit(String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (client.isConnected()) {
                    try {
                        
                        client.sendToServer("#login "+msg);
                    } catch (IOException e1) {
                    }
                }
            }
        }).start();
    }
}
