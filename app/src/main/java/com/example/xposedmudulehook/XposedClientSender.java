package com.example.xposedmudulehook;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by psq on 2016/9/23
 */
public class XposedClientSender implements Runnable{

    private String message = "" ;
    private final String HOST = "192.168.1.255";
    private final int PORT = 10086;

   public XposedClientSender(String  message){
        this.message = message;
    }

    @Override
    public void run() {

        // 创建发送端的Socket对象
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();

            // 创建数据并打包
            byte[] bys = message.getBytes();

            // DatagramPacket dp = new DatagramPacket(bys, bys.length,
            // InetAddress.getByName("192.168.12.92"), 12345);
            DatagramPacket dp = new DatagramPacket(bys, bys.length, InetAddress.getByName(HOST), PORT);

            // 发送数据
            ds.send(dp);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // 释放资源
        ds.close();
    }
}
