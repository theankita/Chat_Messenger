import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;

class ChatServerFrame implements ActionListener 
{
    JFrame fobj;
    JTextField tobj;
    JButton bobj;
    JLabel Message, Resultlabel;
    PrintStream pobj;

    public ChatServerFrame(String title, int width, int height, PrintStream ps) {
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

        Resultlabel = new JLabel("Client Says:");
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
        Resultlabel.setText("Client Says: " + msg);
    }
}

public class ChatServerLoopGUI 
{
    public static void main(String[] args) throws Exception {
        ServerSocket ssobj = new ServerSocket(5100);
        System.out.println("Server waiting at port 5100...");

        Socket sobj = ssobj.accept();
        System.out.println("Client connected.");

        PrintStream pobj = new PrintStream(sobj.getOutputStream());
        BufferedReader bobj1 = new BufferedReader(new InputStreamReader(sobj.getInputStream()));

        ChatServerFrame cobj = new ChatServerFrame("Server", 400, 300, pobj);

        String str1;
        while ((str1 = bobj1.readLine()) != null) {
            System.out.println("Client Says: " + str1);
            cobj.display(str1);  
        }

        sobj.close();
        ssobj.close();
    }
}
