package com.nezha.learn.demo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: RPC服务提供者 <br>
 * @Date: 2019/1/4 3:06 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class Provider {

    public static void main(String[] args) {
        try {
            //1.Socket绑定本地端口
            ServerSocket serverSocket = new ServerSocket(8888);
            //2.监听端口
            while (true){
                Socket socket = serverSocket.accept();
                //1.接收所有的参数
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String apiClassName = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class[] paramTypes = (Class[]) inputStream.readObject();
                Object[] args4Method = (Object[]) inputStream.readObject();
                Class clazz = null;
                //2.服务注册，找到具体的实现类
                if (apiClassName.equals(IUserService.class.getName())){
                    clazz = UserServiceImpl.class;
                }
                //3.执行UserServiceImpl的方法
                Method method = clazz.getMethod(methodName,paramTypes);
                Object invoke = method.invoke(clazz.newInstance(),args4Method);

                //4.返回结果给客户端
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(invoke);
                outputStream.flush();

                //5.关闭连接
                outputStream.close();
                inputStream.close();

                socket.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
