package com.lam;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1024);
            System.out.println("服务器已启动，等待连接...");
            Socket socket = server.accept();
            String ip = socket.getInetAddress().getHostAddress();
            System.out.println("有客户端连接IP：" + ip + ",端口" + socket.getPort());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            InputStream inputStream = socket.getInputStream(); //获取输入流（从已连接的管道上 （stream 是字节流
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream); //创建输入流读取器  （将字节流转成字符流
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//将字节流变成缓冲流从而提升效率
                            String readData = bufferedReader.readLine(); //读取一行数据
                            System.out.println("收到的消息->" + readData);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
