package com.example.demo;

import com.example.demo.bussiness.DoService;
import com.example.demo.service.UserService;
import com.example.demo.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author 作者信息
 * @date 2020-06-09
 */

public final class EchoThread extends Thread{

    @Autowired
    private UserService userService;

    protected Socket socket;


    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }


    public void run(){
        try {
            System.out.println("New Thread");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            InputStream is = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len,"UTF-8"));
            }
            System.out.println("get message from client: " + sb);
            //TOdo 根据sql的warehouse_id 匹配元数据中的ip+port

            DoService doService = SpringUtil.getBean(DoService.class);
            List<Map<String, String>> all = doService.getAll(sb.toString());
            for (Map<String, String> map: all) {
                for (Map.Entry<String, String> entry: map.entrySet()) {
                    System.out.println("key:" + entry.getKey() + " v:"+ entry.getValue());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}