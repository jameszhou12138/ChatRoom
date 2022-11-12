package ChatRoom;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends JFrame {
	//端口常量
	private final int PORT = 2428;
	//服务文本
	JTextArea serverText = new JTextArea();
	//启动按钮
	JButton startButton = new JButton("启动");
	//终止按钮
	JButton endButton = new JButton("终止");
	//聊天者数量按钮
	JButton countButton = new JButton("聊天者数量");
	//聊天者列表按钮
	JButton chattersButton = new JButton("所有聊天者");
	//踢出聊天者按钮
	JButton kickOutButton = new JButton("踢出聊天者");
	//服务滚动面板
	JScrollPane serverPanel = new JScrollPane(serverText);
	//控制面板
	JPanel controlPanel = new JPanel();
	//socket
	ServerSocket serverSocket = null;
	Socket socket = null;
	//socket线程列表
	List<SocketThread> socketThreads = new ArrayList<>();
	//聊天者列表
	List<Chatter> chatters = new ArrayList<>();
	//是否启动
	boolean isStart = true;

	public Server(int width, int height) {
		this.setVisible(true);
		//JFrame关闭后退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置标题
		this.setTitle("全民聊天室（服务端） -- 开发者：周子涵");
		//设置窗口大小
		this.setSize(width, height);

		//清空整体的布局管理器
		this.setLayout(null);
		//服务器文本框
		serverPanel.setBounds(0, 0, 380, 600);
		this.add(serverPanel);
		//控制面板
		controlPanel.setBounds(0, 600, 380, 150);
		this.add(controlPanel);

		//服务文本不能手动编辑
		serverText.setEditable(false);

		//清空控制面板布局管理器
		controlPanel.setLayout(null);
		//控制面板添加启动按钮
		startButton.setBounds(100, 10, 80, 30);
		startButton.setEnabled(false);
		controlPanel.add(startButton);
		//控制面板添加终止按钮
		endButton.setBounds(200, 10, 80, 30);
		controlPanel.add(endButton);
		//控制面板添加聊天者数量按钮
		countButton.setBounds(20, 60, 100, 30);
		controlPanel.add(countButton);
		//控制面板添加聊天者列表按钮
		chattersButton.setBounds(140, 60, 100, 30);
		controlPanel.add(chattersButton);
		//控制面板添加踢出聊天者按钮
		kickOutButton.setBounds(260, 60, 100, 30);
		controlPanel.add(kickOutButton);

		serverText.append("服务器已启动\n\n");

		//启动按钮监听事件
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (serverSocket == null) {
						serverSocket = new ServerSocket(PORT);
					}
					isStart = true;
					serverText.append("\n服务器已启动\n\n");
					startButton.setEnabled(false);
					endButton.setEnabled(true);
					countButton.setEnabled(true);
					chattersButton.setEnabled(true);
					kickOutButton.setEnabled(true);
				} catch (IOException e) {
					System.out.println("socket错误");
				}
			}
		});
		//终止按钮监听事件
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (socket != null) {
						socket.close();
						isStart = false;
					}
					serverText.append("\n服务器已断开连接\n\n");
					for (SocketThread socketThread : socketThreads) {
						socketThread.send("聊天室已关闭");
					}
					System.exit(0);
				} catch (IOException e) {
					System.out.println("socket错误");
				}
			}
		});
		//聊天者数量按钮监听事件
		countButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				serverText.append("\n[服务器操作]");
				if (chatters.size() == 0) {
					serverText.append("暂无聊天者！\n\n");
					return;
				}
				serverText.append("共有 " + chatters.size() + " 位聊天者\n\n");
			}
		});
		//聊天者列表按钮监听事件
		chattersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				serverText.append("\n[服务器操作]");
				if (chatters.size() == 0) {
					serverText.append("暂无聊天者！\n\n");
					return;
				}
				serverText.append("所有聊天者：");
				int cnt = 0;
				for (Chatter chatter : chatters) {
					cnt++;
					serverText.append("\n\t" + cnt + ". " + chatter.getName() + "；");
				}
				serverText.append("\n\n");
			}
		});
		//踢出聊天者按钮监听事件
		kickOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (chatters.size() == 0) {
					JOptionPane.showMessageDialog(serverText, "暂无聊天者！！！", "出错啦", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String[] options = new String[chatters.size()];
				for (int i = 0; i < chatters.size(); i++) {
					options[i] = chatters.get(i).getName();
				}
				String name = (String) JOptionPane.showInputDialog(serverText, "请选择要踢出的聊天者：",
						"踢出聊天者", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				Chatter tmpChatter = chatters.get(0);
				for (Chatter chatter : chatters) {
					if (chatter.getName().equals(name)) {
						tmpChatter = chatter;
						break;
					}
				}
				SocketThread tmpSocketThread = socketThreads.get(0);
				for (SocketThread socketThread : socketThreads) {
					if (socketThread.s == tmpChatter.getSocket()) {
						socketThread.send("您已被踢出聊天室\n");
						tmpSocketThread = socketThread;
					} else {
						socketThread.send(tmpChatter.getName() + "已被踢出聊天室\n");
					}
				}
				socketThreads.remove(tmpSocketThread);
				chatters.remove(tmpChatter);
			}
		});
		start();
	}

	//启动服务器
	public void start() {
		try {
			try {
				serverSocket = new ServerSocket(PORT);
				isStart = true;
			} catch (IOException e2) {
				System.out.println("服务器启动失败");
			}
			while (isStart) {
				socket = serverSocket.accept();
				socketThreads.add(new SocketThread(socket));
			}
		} catch (SocketException se) {
			serverText.append("\n服务器中断\n");
		} catch (IOException e1) {
			System.out.println("socket错误");
		}
	}

	//Socket线程类
	class SocketThread implements Runnable {
		private Socket s = null;

		public SocketThread(Socket s) {
			this.s = s;
			(new Thread(this)).start();
		}

		@Override
		//从客户端接收信息
		public void run() {
			try {
				while (isStart) {
					if (s == null) {
						continue;
					}
					DataInputStream dis = new DataInputStream(s.getInputStream());
					String info = dis.readUTF();
					if (info.contains("：")) {
						for (SocketThread socketThread : socketThreads) {
							socketThread.send(info);
						}
					} else if (info.contains("退出聊天室")) {
						int index = 0;
						String name = info.substring(0, info.length() - 6);
						while (!chatters.get(index).getName().equals(name)) {
							index++;
						}
						chatters.remove(index);
						for (SocketThread socketThread : socketThreads) {
							socketThread.send(info);
						}
					} else {
						boolean flag = true;
						for (Chatter chatter : chatters) {
							if (chatter.getName().equals(info)) {
								flag = false;
								send("已有相同的昵称，请更改");
								break;
							}
						}
						if (flag) {
							Chatter chatter = new Chatter(info, s);
							chatters.add(chatter);
							for (SocketThread socketThread : socketThreads) {
								socketThread.send("欢迎" + info + "进入聊天室！\n");
							}
						}
					}
				}
			} catch (IOException e) {
				System.out.println("socket错误");
			}
		}

		//发送信息给客户端
		public void send(String str) {
			try {
				DataOutputStream dos = new DataOutputStream(this.s.getOutputStream());
				dos.writeUTF(str);
			} catch (IOException e) {
				System.out.println("输入输出流错误\n");
			}
		}
	}

	//聊天者类
	static class Chatter {
		private String name;
		private Socket socket;

		Chatter(String name, Socket socket) {
			this.name = name;
			this.socket = socket;
		}

		@Override
		public String toString() {
			return "{name='" + name + '\'' +
					", socket='" + socket + '\'' +
					"}\n";
		}

		public String getName() {
			return name;
		}

		public Socket getSocket() {
			return socket;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}
	}

	public static void main(String[] arg) {
		new Server(400, 750);
	}
}