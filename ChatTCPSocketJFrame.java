package socket;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ChatTCPSocketJFrame extends JFrame implements ActionListener{
//客户端程序以及聊天室的图形用户界面
	private String name;//网名
	private Socket socket;//该客户端的socket对象
	private JTextArea text_receiver;
	private JTextField text_sender;
	private JButton button_send, button_leave;
	private PrintWriter cout;//打印输出流
	
	public ChatTCPSocketJFrame(String name,Socket socket) throws Exception {//利用用户昵称和socket对象创建客户端
		// TODO Auto-generated constructor stub
		this.setTitle("聊天室  "+name+"  "+InetAddress.getLocalHost());
		this.setBounds(320, 240, 400, 240);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.text_receiver= new JTextArea();
		this.text_receiver.setEditable(false);//设置对话框不可编辑
		this.add(new JScrollPane(this.text_receiver));
		
		JPanel panel= new JPanel();
		this.add(panel,BorderLayout.SOUTH);
		this.text_sender= new JTextField(16);
		panel.add(this.text_sender);
		button_send= new JButton("发送");
		panel.add(button_send);
		button_send.addActionListener(this);
		button_leave= new JButton("离线");
		panel.add(button_leave);
		button_leave.addActionListener(this);
		this.setVisible(true);
		
		this.name= name;//发送方操作
		this.socket= socket;
		this.cout= new PrintWriter(socket.getOutputStream(),true);//获得socket的输出流，设置自动刷新缓冲区
		this.cout.println(name);//发送自己网名给对方
		
		
		//接收方操作
		BufferedReader cin= new BufferedReader(new InputStreamReader(socket.getInputStream()));//获取socket输入流，最后包装成缓冲流
		
		String aline= cin.readLine();//获取对方网名
		text_receiver.append("连接"+aline+"成功\r\n");
		aline= cin.readLine();//继续获取对方发来的内容
		while(aline!= null && !aline.contains("bye"))
		{
			text_receiver.append(aline+"\r\n");
			aline= cin.readLine();
		}
		cin.close();
		cout.close();
		socket.close();
		button_leave.setEnabled(false);
		button_send.setEnabled(false);
	}
	
	public ChatTCPSocketJFrame(String name, String host, int port) throws Exception//客户端向主机端口发送TCP连接请求
	{//直接调用这个构造方法，然后调用上述界面构造方法
		this(name,new Socket(host,port));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== button_send)
		{
			this.cout.println(name+":"+text_sender.getText());//通过流发送给对方
			text_receiver.append("我:"+text_sender.getText()+"\n");//同样内容在我方的显示
			text_sender.setText("");//输入框置空
		}
		if(e.getSource()== button_leave)
		{
			text_receiver.append("我离线\n");
			this.cout.println(name+"离线\n"+"bye");
			button_leave.setEnabled(false);
			button_send.setEnabled(false);
		}
	}
	public static void main(String[] args) throws Exception {
		new ChatTCPSocketJFrame("Bob", "127.0.0.1", 2001);//目标ip对应的端口号
	}
}