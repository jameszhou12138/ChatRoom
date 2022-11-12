package ChatRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Client extends JFrame {
	//ip标签
	JLabel ipLabel = new JLabel("IP:");
	//ip文本框
	JTextField ipText = new JTextField(10);
	//端口标签
	JLabel portLabel = new JLabel("端口:");
	//端口文本框
	JTextField portText = new JTextField(10);
	//昵称标签
	JLabel nicknameLabel = new JLabel("昵称:");
	//昵称文本框
	JTextField nicknameText = new JTextField(10);
	//进入聊天室按钮
	JButton enterButton = new JButton("进入");
	//退出聊天室按钮
	JButton exitButton = new JButton("退出");
	//聊天文本框
	JTextArea chatText = new JTextArea(20, 20);
	//发送文本框
	JTextArea sendText = new JTextArea(6, 20);
	//发送按钮
	JButton sendButton = new JButton("发送");
	//控制面板
	JPanel controlPanel = new JPanel();
	//聊天滚动面板
	JScrollPane chatPanel = new JScrollPane(chatText);
	//发送面板
	JPanel sendPanel = new JPanel();

	//socket
	Socket socket = null;
	//昵称
	String nickname = "";
	//是否连接
	boolean isConnect = false;

	public Client(int width, int height) {
		//设置可视
		this.setVisible(true);
		//JFrame关闭后退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置标题
		this.setTitle("全民聊天室 -- 开发者：周子涵");
		//设置窗口大小
		this.setSize(width, height);
		//打开后屏幕居中
		this.setLocationRelativeTo(getOwner());

		//清空整体的布局管理器
		this.setLayout(null);
		//控制面板
		controlPanel.setBounds(0, 0, 580, 40);
		this.add(controlPanel);
		//聊天滚动面板
		chatPanel.setBounds(0, 40, 580, 250);
		this.add(chatPanel);
		//发送面板
		sendPanel.setBounds(0, 290, 580, 110);
		this.add(sendPanel);

		//清空控制面板布局管理器
		controlPanel.setLayout(null);
		//控制面板添加ip标签
		ipLabel.setBounds(10, 10, 20, 20);
		controlPanel.add(ipLabel);
		//控制面板添加ip文本
		ipText.setText("127.0.0.1");
		ipText.setBounds(30, 10, 80, 20);
		controlPanel.add(ipText);
		//控制面板添加接口标签
		portText.setText("2428");
		portLabel.setBounds(140, 10, 40, 20);
		controlPanel.add(portLabel);
		//控制面板添加接口文本
		portText.setBounds(180, 10, 80, 20);
		controlPanel.add(portText);
		//控制面板添加昵称标签
		nicknameLabel.setBounds(280, 10, 40, 20);
		controlPanel.add(nicknameLabel);
		//控制面板添加昵称文本
		nicknameText.setBounds(320, 10, 80, 20);
		controlPanel.add(nicknameText);
		//控制面板添加进入聊天室按钮
		enterButton.setBounds(430, 10, 60, 20);
		controlPanel.add(enterButton);
		//增加面板添加退出聊天室按钮
		exitButton.setBounds(510, 10, 60, 20);
		exitButton.setEnabled(false);
		controlPanel.add(exitButton);

		//聊天文本不能手动编辑
		chatText.setEditable(false);

		//清空发送面板布局管理器
		sendPanel.setLayout(null);
		//发送面板添加发送文本框
		sendText.setBounds(10, 10, 480, 60);
		sendText.setEnabled(false);
		sendPanel.add(sendText);
		//发布页面添加发送按钮
		sendButton.setBounds(500, 10, 80, 60);
		sendButton.setEnabled(false);
		sendPanel.add(sendButton);

		//进入聊天室按钮监听事件
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (ipText.getText().equals("")) {
					JOptionPane.showMessageDialog(chatText, "请输入IP");
					return;
				}
				String ip = ipText.getText();
				if (portText.getText().equals("")) {
					JOptionPane.showMessageDialog(chatText, "请输入端口");
					return;
				}
				if (!Pattern.compile("\\d+").matcher(portText.getText()).matches()) {
					JOptionPane.showMessageDialog(chatText, "端口应为数字");
					return;
				}
				int port = Integer.parseInt(portText.getText());
				if (nicknameText.getText().equals("")) {
					JOptionPane.showMessageDialog(chatText, "请输入昵称");
					return;
				}
				nickname = nicknameText.getText();
				try {
					socket = new Socket(ip, port);
					isConnect = true;
					send(nickname);
					new Thread(new Receive()).start();
					ipText.setEnabled(false);
					portText.setEnabled(false);
					nicknameText.setEnabled(false);
					enterButton.setEnabled(false);
					exitButton.setEnabled(true);
					sendText.setEnabled(true);
					sendButton.setEnabled(true);
					chatText.setText("");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(chatText, "该服务器尚未启动！！！", "出错啦", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//退出聊天室按钮监听事件
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					send(nickname + "退出聊天室\n");
					isConnect = false;
					socket.close();
					ipText.setEnabled(true);
					portText.setEnabled(true);
					nicknameText.setEnabled(true);
					enterButton.setEnabled(true);
					exitButton.setEnabled(false);
					sendText.setEnabled(false);
					sendButton.setEnabled(false);
					chatText.setText("");
				} catch (IOException e) {
					System.out.println("socket错误");
				}
			}
		});
		//发送按钮监听事件
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String sendData = sendText.getText();
				if (!sendData.equals("")) {
					send(nickname + "：" + sendData + "\n");
				}
				sendText.setText("");
			}
		});
	}

	//发送信息到服务器
	public void send(String info) {
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(info);
		} catch (SocketException se) {
			System.out.println("未连接服务器");
		} catch (IOException e) {
			System.out.println("输入输出流错误");
		}
	}

	//接收服务器信息类
	class Receive implements Runnable {
		public void run() {
			try {
				while (isConnect) {
					DataInputStream dis = new DataInputStream(socket.getInputStream());
					String info = dis.readUTF();
					if (info.equals("已有相同的昵称，请更改") || info.equals("您已被踢出聊天室\n") || info.equals("聊天室已关闭")) {
						ipText.setEnabled(true);
						portText.setEnabled(true);
						nicknameText.setEnabled(true);
						enterButton.setEnabled(true);
						exitButton.setEnabled(false);
						sendText.setEnabled(false);
						sendButton.setEnabled(false);
						chatText.setText("");
						socket.close();
						JOptionPane.showMessageDialog(chatText, info);
						continue;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					chatText.append("[" + sdf.format(System.currentTimeMillis()) + "]" + info);
				}
			} catch (SocketException se) {
				System.out.println("未连接服务器");
			} catch (IOException e) {
				System.out.println("输入输出流错误");
			}
		}
	}

	public static void main(String[] arg) {
		EventQueue.invokeLater(() -> {
			//默认初始大小为600*400
			Client client = new Client(600, 400);
		});
	}
}