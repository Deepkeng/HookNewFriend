package com.example.xposedmudulehook;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by D on 2016/8/27.
 */
public class XposedClientSocket implements Runnable {

    private String message = "" ;
    //private final String HOST = "127.0.0.1";
    private final String HOST = "192.168.1.255";
    //private final int PORT = 11137;
    private final int PORT = 10086;
    Socket client = null;

    public XposedClientSocket(String message) {
        this.message = message;     //hook方法获取到的实时聊天信息
    }

    @Override
    public void run() {
        //init();
    }

    /**
     * 初始化socket，连接到主程序socket，当catch到异常时，再进行初始化socket操作，直到socket连接上主程序socket
     */
    public void init() {
       XposedBridge.log(" init () ");
        try {
            client = new Socket();
            InetSocketAddress address = new InetSocketAddress(this.HOST, this.PORT);
            client.setKeepAlive(true);
            client.setSoTimeout(5 * 1000);
            client.setTcpNoDelay(true);
            client.setOOBInline(true);
            client.connect(address);
            write(client);
        } catch (Exception e) {
            e.printStackTrace();
            init();
        }


    }


    /**
     * 判断socket是否连接中，是则将hook到的消息发送给主程序socket，发送完则关闭socket，io流
     * <p/>
     * * @param client socket 套接字
     */
    public void write(Socket client) {
        XposedBridge.log(" wirte method ");

        if (!client.isClosed() && client.isConnected()) {

            try {
                XposedBridge.log(" obtain socket OutputStream ");
//                OutputStream os = client.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                while (!client.isClosed() && client.isConnected()) {
//                while (true) {        //ok
                    XposedBridge.log("message info : " + message);

                    if (!message.equals("")) {
                        XposedBridge.log(" send data start ");
                        String value = message;
//                        打印从Queue中获取到的消息
                        XposedBridge.log(value);
//                        os.write(value.getBytes("UTF-8"));
//                        os.flush();
                        bw.write(value);
                        bw.flush();
                        XposedBridge.log(" send data end ");
                    } else {
                        // send heart beat
                        Thread.sleep(500);
                        XposedBridge.log("send heartbeat ");
                        String heartBeat = "heartBeat";
//                        os.write(heartBeat.getBytes("UTF-8"));
//                        os.flush();
                        bw.write(heartBeat);
                        bw.flush();
                    }
                    bw.newLine();
                    bw.close();
                    client.close();

                    XposedBridge.log(" out flush ");
                }
            } catch (IOException e) {
                e.printStackTrace();
                XposedBridge.log("write IOException " + e.getMessage());
//                if (client.isClosed()) {
//                    init();
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                XposedBridge.log("write InterruptedExceptin " + e.getMessage());
            }
        }
    }

}
