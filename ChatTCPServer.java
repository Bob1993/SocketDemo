package socket;

import java.net.ServerSocket;
import java.net.Socket;

/*
 * 提供ServerSocket对象（可以提供TCP连接服务）
 */
public class ChatTCPServer {
	public ChatTCPServer(int port, String name) throws Exception{//约定端口，网名
		// TODO Auto-generated constructor stub
		ServerSocket server= new ServerSocket(port);//创建socket套接字
		Socket client= server.accept();//等待接收客户端的连接申请
		new ChatTCPSocketJFrame(name,client);//创建服务端的客户端
		server.close();//通信结束，服务关闭
	}
	public static void main(String[] args) throws Exception {
		new ChatTCPServer(2001,"花仙子");
	}
	
}

