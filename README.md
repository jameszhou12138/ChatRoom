# ChatRoom
## 实验题目

全民聊天室          

## 实验环境

Intellij IDEA、记事本、命令提示符等   

## 实验要求

1. 掌握Java网络编程的原理和实现方法。

2. 掌握Java多线程的原理和编程方法。

3. 本实验的客户端需要至少3个线程才能正常工作：图形界面线程、接收数据的线程、发送数据的线程。

4. 本实验的服务器端至少需要2*n+3个线程才能正常工作，其中2*n个线程负责与n个客户端的通信，包括接收和发送。另外3个线程中，第一个是主线程，也就是服务器端启动后所产生的线程；第二个是管理员操作线程，负责接收管理员的指令，控制整个的程序；第三个线程是客户端巡检线程，查看哪个客户端需要踢出（客户自己退出、管理员主动踢出、异常出现时的退出）。

 

## 实验内容

1．实验背景介绍和系统具体要求

通讯软件已经替代传统的电话、短信，成为最主要的交流工具。本实验利用Java的Socket通信机制，实现一款即时通信软件，能够允许任意多的客户端的参与。

几乎所有的通信软件都需要服务器管理，否则，客户端之间几乎难以建立联系。

服务器端和客户端程序是两个完全独立的工程。它们之间虽然可能会有共同的类，但这些类要属于不同的工程。服务器端和客户端之间要通过协议通信，这是传统的C/S结构。在这种传统结构下，服务器端和客户端之间的协议是由程序员规定的！

服务器端无需图形化界面；但为了方便聊天者，客户端需要使用图形界面。

 

2．用记事本书写两个Java程序

（1）、建立个人子目录

步骤1：建立个人子目录

第一次上机时先在D盘上建立一个以自己学号+姓名为目录名的子目录，如学号为210824109的张三同学，就用“210824109张三”为子目录名。实验完成的源代码、Java字节码和实验报告三个文件都要放在这个文件夹下（称为上交文件夹）。

 

步骤2：建立两个Java源代码文件

在所建立的文件夹下新建一个文件夹“server”，在其中建立一个记事本文件Server.txt，并把它重命名为Server.java。

在所建立的文件夹下新建一个文件夹“client”，在其中建立一个记事本文件Client.txt，并把它重命名为Client.java。

在server文件夹下除了Server.java外，还应该有其他的Java文件辅助，例如

class Inspection extends Thread { } //用于巡检。

class Control extends Thread { } //用于接收管理员的指令，控制服务器端。

class Anteroom extends Thread{ } //专门用于等候新的聊客连接并建立通信通道。

class Receiver extends Thread{ } //用于接收特定客户端的信息。

class Sender extends Thread{ } //用于发送特定客户的的信息。

在client文件夹下除了Client.java外，还应该有其他的Java文件辅助，例如：

class Sender extends Thread{ } //用于向服务器发送数据。

class Receiver extends Thread{ } //用于接收服务器端发来的数据。

（2）、编写源代码

具体实现上述的类。当然，除了Server.java和Client.java两个主类之外，其他类的名称、数量、功能都可以自己确定。Server和Client之间的通信协议自己规定。

 

 

## 实验方法

根据实验内容，将该实验大致分为三个步骤：（1）编写服务端Server和客户端Client的图形用户界面；（2）编写socket相关内容，使得服务器与客户端连接；（3）编写每个按钮的功能。

 

## 实验结果

（1）用一个命令提示符cmd编译运行服务端Server，用多个（以4个为例）命令提示符cmd编译运行客户端Client。（如图1所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps1.jpg) 

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps2.jpg) 

图1 编译运行Server和Client

 

（2）运行Server服务端，得到服务端初始界面。默认服务器已启动，上面的文本域用于显示服务端文本，初始时有提示信息“服务器已启动”，下面共5个按钮。“启动”按钮用于启动服务器，默认已经按下；“终止”按钮用于终止服务器，关闭界面；“聊天者数量”按钮用于显示聊天室内的人数；“所有聊天者”按钮用于显示所有聊天者的信息；“踢出聊天者”按钮会弹出消息对话框，选中指定用户后，将该用户踢出聊天室。（如图2所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps3.jpg) 

图2 服务端初始界面

 

（3）当聊天室内无聊天者时，点击“聊天者数量”按钮和“所有聊天者”按钮，服务端文本域中都会出现提示信息“暂无聊天者！”。（如图3所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps4.jpg) 

图3 聊天室无聊天者

 

（4）运行Client客户端，得到客户端初始界面。最上方为进入退出聊天室的控制面板：用于填写IP地址的文本框，默认填写的IP为127.0.0.1；用于填写端口号的文本框，默认填写的端口为2428；用于填写昵称的文本框；用于进入聊天室的“进入”按钮，当IP地址、端口号、昵称不填写或填写有误时弹出相应错误信息的消息对话框，当昵称重复时弹出昵称重复的消息对话框，当对应IP地址和端口的服务器未启动弹出服务器未启动的消息对话框；用于退出聊天室的“退出按钮”。中间为文本域用于记录聊天内容和用户进入退出聊天室。最下方为发送聊天信息的文本域和“发送”按钮。（如4所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps5.jpg) 

图4 客户端初始界面

 

（5）当用户未填写IP地址时，弹出消息对话框“请输入IP”；当用户未填写端口号时，弹出消息对话框“请输入端口”；当用户填写的端口号不是数字时，弹出消息对话框“端口应为数字”；当用户未填写昵称时，弹出消息对话框“请输入昵称”；当对应IP地址和端口的服务器未启动时，弹出消息对话框“该服务器尚未启动”。（如图5所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps6.jpg) ![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps7.jpg)

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps8.jpg) ![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps9.jpg)

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps10.jpg) 

图5 客户端错误提示消息对话框

 

（6）当在聊天室内已存在输入的昵称的聊天者，弹出消息对话框“已有相同的昵称，请更改”。

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps11.jpg) 

图6 相同昵称提示

 

（7）当一个聊天者正常进入聊天室后，在聊天室内的所有聊天者都能接收到这个聊天者进入聊天室的提示信息；当一个用户发送信息，所有用户都能收到该信息。（如图7所示）。

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps12.jpg) 

图7 进入聊天室和正常聊天

 

（8）点击“聊天者数量”按钮，在服务端可以显示当前在聊天室聊天者的数量。（如图8所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps13.jpg) 

图8 点击“聊天者数量”按钮

 

（9）点击“所有聊天者”按钮，在服务端可以显示当前在聊天室所有聊天者的昵称。（如图9所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps14.jpg) 

图9 点击“所有聊天者”按钮

 

（10）点击“踢出聊天者”按钮，弹出选择消息对话框，选择需要踢出聊天室的聊天者，点击“确定”，则该聊天者的客户端弹出消息对话框“您已被踢出聊天室”，其余聊天者的客户端显示被踢出聊天室的聊天者已被踢出聊天室。（如图10所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps15.jpg) 

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps16.jpg) 

图10 点击“踢出聊天者”按钮

 

（11）聊天者点击“退出”按钮，则该聊天者的客户端清空，其余聊天者的客户端显示退出聊天室的聊天者已退出聊天室。（如图10所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps17.jpg) 

图11 退出聊天室

 

（12）服务端点击“终止”按钮，服务端界面消失，所有聊天者的聊天框中内容全部清空并弹出消息对话框“聊天室已关闭”。聊天者无法再发送消息与进入聊天室。（如图12所示）

![img](file:///C:\Users\ZHOUZI~1\AppData\Local\Temp\ksohtml11384\wps18.jpg) 

图12 终止服务器

## 结论分析

（1）Client客户端

① “进入”按钮响应事件：

点击“进入”按钮，触发该事件。当IP地址、端口号、昵称不填写或填写有误时弹出相应错误信息的消息对话框。

若输入均正确，用nickname存储昵称文本框内的文字。通过IP地址和端口号创建一个socket作为该客户端的socket，将用于判断是否与服务端连接的isConnect设置为true，再将nickname发送给Server服务端，并创建一个新的线程。将“进入”按钮设为不可点击；将IP地址文本框、端口号文本框、昵称文本框和设为不可编辑；将“退出”按钮、“发送”按钮设为可以点击；将发送文本域和设为可编辑。

若创建socket失败，说明客户端未启动，弹出消息对话框“该服务器尚未启动！！！”的提示信息。

```java
//“进入”按钮响应事件
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
```

 

② “退出”按钮响应事件：

点击“退出”按钮，触发该事件。

将以昵称+“已退出聊天室”的形式发送给Server服务端，并将用于判断是否与服务端连接的isConnect设置为false。将“进入”按钮设为可以点击；将IP地址文本框、端口号文本框、昵称文本框和设为可以编辑；将“退出”按钮、“发送”按钮设为不可点击；将发送文本域和设为不可编辑。

若发送失败，输出socket错误的提示信息。

```java
//“退出”按钮响应事件
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
```

 

③ “发送”按钮响应事件：

点击“发送”按钮，触发该事件。

先获取发送文本域中的文本，若文本不为空，则将以昵称+文本的形式发送给Server服务端，再将发送文本域清空。

```java
//“发送”按钮响应事件
String sendData = sendText.getText();
if (!sendData.equals("")) {
  send(nickname + "：" + sendData + "\n");
}
sendText.setText("");
```

 

④ 发送信息到服务器：

创建用于向服务器发送数据的输出流，客户端向服务器发送数据。并进行异常处理。

```java
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
```

 

⑤ 接收服务器信息类：

接收服务器信息类为一个线程类，对类中的run方法进行编写。当是连接的状态下，创建用于从接收服务器数据的输入流，用info记录下服务器向客户端发送的数据。并进行异常处理。

若info为“已有相同的昵称，请更改”、info为“您已被踢出聊天室\n”或者info为“聊天室已关闭”，将“进入”按钮设为可以点击；将IP地址文本框、端口号文本框、昵称文本框和设为可以编辑；将“退出”按钮、“发送”按钮设为不可点击；将发送文本域和设为不可编辑。再关闭socket，并弹出消息对话框，消息对话框的内容为info。

否则先获取当前的时间，并以时间+info的形式写入该客户端的聊天框中。

```java
//接收服务器信息类
class Receive implements Runnable {
  public void run() {
   try {
     while (isConnect) {
      DataInputStream data = new DataInputStream(socket.getInputStream());
      String info = data.readUTF();
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
```



（2）Server服务端

① 聊天者类：

聊天者类中有私有成员变量name来表示聊天者的昵，私有成员变量socket来表示聊天者的socket，成员方法toString便于调试输出该导弹的信息以及一些方法用于修改或获取某一成员变量。

```java
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
```

 

② Socket线程类：

Socket线程类为一个线程类，类中有私有成员变量s表示线程的socket。重载方法run用于从客户端接收信息，方法send用于发送信息给客户端。

- 方法run：

当是连接的状态下，创建用于从接收客户端socket的输入流，用info记录下客户端向服务器发送的数据。并进行异常处理。

如果info包含“：”时，说明info为聊天，则对于socket线程列表的每一个socket线程都将info发送给该线程；

如果线程包含“退出聊天室”，则对于socket线程列表的每一个socket线程都将info发送给该线程，再将当前socket线程从socket线程列表中移除，当前聊天者从聊天者列表中移除；

否则为进入聊天室，判断当前socket所对应的昵称是否在聊天者列表中存在，存在则将“已有相同的昵称，请更改”发送给该socket线程，不存在则将“欢迎”+ info + “进入聊天室！”的形式发送给socket线程列表的每一个线程。

- 方法send：

创建用于向客户端发送数据的输出流，服务器向客户端发送数据。并进行异常处理

```java
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
   }catch (IOException e) {
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
```

 

③ 启动按钮监听事件：

点击“启动”按钮，触发该事件。

若当前的服务器socket为空，则将新建一个端口为PORT的服务器socket，将判断是否开启服务器的isStart设为true，并在服务器文本域中添加“服务器已启动”的文字提示信息。将“启动”按钮设为不可点击；将“终止”按钮、“聊天者数量”按钮、“所有聊天者”按钮、“踢出聊天者”按钮设为可以点击。

```java
//启动按钮监听事件
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
```

 

④ 终止按钮监听事件：

点击“终止”按钮，触发该事件。

若当前的服务器socket不为空，关闭服务器socket，将判断是否开启服务器的isStart设为false，并将“聊天室已关闭”发送给socket线程列表的所有socket线程。关闭服务器界面。

```java
//终止按钮监听事件
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
```

 

⑤ 聊天者数量按钮监听事件：

点击“聊天者数量”按钮，触发该事件。

当聊天者列表的长度为0时，在服务器文本域中添加“[服务器操作]暂无聊天者”的文字提示信息。

当聊天者列表的长度大于0时，在服务器文本域中添加“[服务器操作]共有”+聊天者列表的长度+“位聊天者”的文字提示信息。

```java
//聊天者数量按钮监听事件
serverText.append("\n[服务器操作]");
if (chatters.size() == 0) {
  serverText.append("暂无聊天者！\n\n");
  return;
}
serverText.append("共有 " + chatters.size() + " 位聊天者\n\n");
```

 

⑥ 所有聊天者按钮监听事件：

点击“所有聊天者”按钮，触发该事件。

当聊天者列表的长度为0时，在服务器文本域中添加“[服务器操作]暂无聊天者”的文字提示信息。

当聊天者列表的长度大于0时，在服务器文本域中添加“[服务器操作]所有聊天者：”+聊天者在聊天者列表中的索引+聊天者的昵称的文字提示信息。。

```java
//启动按钮监听事件
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
```

 

⑦ 踢出聊天者按钮监听事件：

点击“踢出聊天者”按钮，触发该事件。

当聊天者列表的长度为0，弹出消息对话框“暂无聊天者！！！”。

当聊天者列表的长度大于0时，弹出消息对话框从聊天者列表中的昵称中选择需要踢出的聊天者，找到该聊天者的socket。遍历socket线程列表，若当前socket线程为要踢出的聊天者的socket，则将“您已被踢出聊天室”发送给当前socket线程；否则将以要踢出的聊天者的昵称+“已被踢出聊天室”的形式发送给当前socket线程。最后将要踢出的聊天者从列表中移除，将该聊天者对应的socket线程从线程列表中移除。

```java
//踢出聊天者按钮监听事件
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
```

 

 
