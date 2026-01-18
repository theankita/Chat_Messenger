import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;

class ChatClientFrame implements ActionListener 
{
    JFrame fobj;
    JTextField tobj;
    JButton bobj;
    JLabel Message, Resultlabel;
    PrintStream pobj;

    public ChatClientFrame(String title, int width, int height, PrintStream ps) {
        pobj = ps;

        fobj = new JFrame(title);
        fobj.setLayout(null);

        Message = new JLabel("Message:");
        Message.setBounds(50, 50, 100, 30);
        fobj.add(Message);

        tobj = new JTextField();
        tobj.setBounds(150, 50, 150, 30);
        fobj.add(tobj);

        bobj = new JButton("SEND");
        bobj.setBounds(50, 100, 100, 40);
        fobj.add(bobj);

        Resultlabel = new JLabel("Server Says:");
        Resultlabel.setBounds(50, 160, 300, 30);
        fobj.add(Resultlabel);

        bobj.addActionListener(this);

        fobj.setSize(width, height);
        fobj.setVisible(true);
        fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        String msg = tobj.getText();
        pobj.println(msg);  
        tobj.setText("");   
    }

    public void display(String msg) {
        Resultlabel.setText("Server Says: " + msg);
    }
}

public class ChatClientLoopGUI 
{
    public static void main(String[] args) throws Exception {
        Socket sobj = new Socket("localhost", 5100);
        System.out.println("Connected with server...");

        PrintStream pobj = new PrintStream(sobj.getOutputStream());
        BufferedReader bobj1 = new BufferedReader(new InputStreamReader(sobj.getInputStream()));

        ChatClientFrame cobj = new ChatClientFrame("Client", 400, 300, pobj);

        String str1;
        while ((str1 = bobj1.readLine()) != null) {
            System.out.println("Server Says: " + str1);
            cobj.display(str1); 
        }

        sobj.close();
    }
}
