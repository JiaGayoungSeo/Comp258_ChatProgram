/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleChatProgram;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author seo8556
 */
public class MemberUI extends JFrame {
    public boolean confirm = false;
    public JTextField idText;
    public JTextField pwText;
    public JButton signUpBtn, cancelBtn;
    //private ConnectionToClient client;
    private ChatClient chatClient;

    public MemberUI(ChatClient chatClient) {
        setTitle("Register");
        this.chatClient = chatClient;

        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 335, 197);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(12, 10, 295, 138);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("ID");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(60, 38, 57, 15);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("PW");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(60, 63, 57, 15);
        panel.add(lblNewLabel_1);

        idText = new JTextField();
        idText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setVisible(false);
                }
            }
        });
        idText.setBounds(129, 35, 116, 21);
        panel.add(idText);
        idText.setColumns(10);

        pwText = new JTextField();
        pwText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    msgSummit();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setVisible(false);
                }
            }
        });
        pwText.setBounds(129, 60, 116, 21);
        panel.add(pwText);
        pwText.setColumns(10);

        signUpBtn = new JButton("Sign up");
        signUpBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        msgSummit();
                    }
                }).start();
                dispose();
            }
        });
        signUpBtn.setBounds(50, 88, 97, 23);
        panel.add(signUpBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });
        cancelBtn.setBounds(148, 88, 97, 23);
        panel.add(cancelBtn);
        setVisible(true);
    }

    private void msgSummit() {// ���ϻ���
        if (chatClient.isConnected()) {
            try {

                chatClient.sendToServer("Test msg Summit method");
                setVisible(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
