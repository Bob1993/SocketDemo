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
//�ͻ��˳����Լ������ҵ�ͼ���û�����
	private String name;//����
	private Socket socket;//�ÿͻ��˵�socket����
	private JTextArea text_receiver;
	private JTextField text_sender;
	private JButton button_send, button_leave;
	private PrintWriter cout;//��ӡ�����
	
	public ChatTCPSocketJFrame(String name,Socket socket) throws Exception {//�����û��ǳƺ�socket���󴴽��ͻ���
		// TODO Auto-generated constructor stub
		this.setTitle("������  "+name+"  "+InetAddress.getLocalHost());
		this.setBounds(320, 240, 400, 240);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.text_receiver= new JTextArea();
		this.text_receiver.setEditable(false);//���öԻ��򲻿ɱ༭
		this.add(new JScrollPane(this.text_receiver));
		
		JPanel panel= new JPanel();
		this.add(panel,BorderLayout.SOUTH);
		this.text_sender= new JTextField(16);
		panel.add(this.text_sender);
		button_send= new JButton("����");
		panel.add(button_send);
		button_send.addActionListener(this);
		button_leave= new JButton("����");
		panel.add(button_leave);
		button_leave.addActionListener(this);
		this.setVisible(true);
		
		this.name= name;//���ͷ�����
		this.socket= socket;
		this.cout= new PrintWriter(socket.getOutputStream(),true);//���socket��������������Զ�ˢ�»�����
		this.cout.println(name);//�����Լ��������Է�
		
		
		//���շ�����
		BufferedReader cin= new BufferedReader(new InputStreamReader(socket.getInputStream()));//��ȡsocket������������װ�ɻ�����
		
		String aline= cin.readLine();//��ȡ�Է�����
		text_receiver.append("����"+aline+"�ɹ�\r\n");
		aline= cin.readLine();//������ȡ�Է�����������
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
	
	public ChatTCPSocketJFrame(String name, String host, int port) throws Exception//�ͻ����������˿ڷ���TCP��������
	{//ֱ�ӵ���������췽����Ȼ������������湹�췽��
		this(name,new Socket(host,port));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== button_send)
		{
			this.cout.println(name+":"+text_sender.getText());//ͨ�������͸��Է�
			text_receiver.append("��:"+text_sender.getText()+"\n");//ͬ���������ҷ�����ʾ
			text_sender.setText("");//������ÿ�
		}
		if(e.getSource()== button_leave)
		{
			text_receiver.append("������\n");
			this.cout.println(name+"����\n"+"bye");
			button_leave.setEnabled(false);
			button_send.setEnabled(false);
		}
	}
	public static void main(String[] args) throws Exception {
		new ChatTCPSocketJFrame("Bob", "127.0.0.1", 2001);//Ŀ��ip��Ӧ�Ķ˿ں�
	}
}