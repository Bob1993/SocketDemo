package socket;

import java.net.ServerSocket;
import java.net.Socket;

/*
 * �ṩServerSocket���󣨿����ṩTCP���ӷ���
 */
public class ChatTCPServer {
	public ChatTCPServer(int port, String name) throws Exception{//Լ���˿ڣ�����
		// TODO Auto-generated constructor stub
		ServerSocket server= new ServerSocket(port);//����socket�׽���
		Socket client= server.accept();//�ȴ����տͻ��˵���������
		new ChatTCPSocketJFrame(name,client);//��������˵Ŀͻ���
		server.close();//ͨ�Ž���������ر�
	}
	public static void main(String[] args) throws Exception {
		new ChatTCPServer(2001,"������");
	}
	
}

