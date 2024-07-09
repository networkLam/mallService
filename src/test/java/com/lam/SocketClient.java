package com.lam;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1",1024);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            System.out.println("请输入内容");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        Scanner scanner = new Scanner(System.in);
                        String input = scanner.nextLine();
                        printWriter.println(input);
                        printWriter.flush();
                    }
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
