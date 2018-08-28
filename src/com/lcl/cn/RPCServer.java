package com.lcl.cn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于发布一个远程服务
 */
public class RPCServer {

    //创建一个线程池
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * 服务的发布
     * @param service 对应的服务名称
     * @param port     对应的端口号
     */
    public void publish(final Object service,int port){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);//启动一个服务监听
            while(true){//循环监听
                Socket socket = serverSocket.accept();//监听服务
                //通过线程去处理请求
                executor.execute(new ProcessorHandle(socket,service));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
