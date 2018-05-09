package NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientTest {


        public static void main(String[] args) throws Exception {
            System.out.println("===========ClientInit===========");
            SocketChannel clientSocketChannel=SocketChannel.open();
            clientSocketChannel.connect(new InetSocketAddress("118.24.23.211",8686));
            clientSocketChannel.configureBlocking(false);
            Selector selector=Selector.open();
            //这里不能添加SelectionKey.OP_ACCEPT(不知道为什么)
            int ops=SelectionKey.OP_CONNECT|SelectionKey.OP_READ|SelectionKey.OP_WRITE;
            clientSocketChannel.register(selector,ops);
            while (!clientSocketChannel.finishConnect()){

            }
            System.out.println("===========服务器连接成功===========");

            //模拟给服务器发送消息
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20000);
                        ByteBuffer send=ByteBuffer.allocate(1024);
                        send.put("this's client.".getBytes());
                        send.flip();
                        clientSocketChannel.write(send);
                        clientSocketChannel.register(selector,SelectionKey.OP_READ);
                    }catch (Exception e){
                        System.out.println("E:"+e.getMessage());
                    }
                }
            }).start();

            while (true){
                //此方法执行处于阻塞模式的选择操作
                int num=selector.select();
                if (num==0)continue;
                System.out.println(">>>>>>>>>>>>>>>>"+num);
                //返回选择的键
                Set<SelectionKey> keys=selector.selectedKeys();
                Iterator<SelectionKey> iterator=keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key=iterator.next();
                    if (key.isConnectable()){
                        System.out.println("=====client isConnectable=====");
                        clientSocketChannel.finishConnect();
                        clientSocketChannel.register(selector,SelectionKey.OP_WRITE);
                    }else if(key.isAcceptable()){
                        System.out.println("=====client isAcceptable=====");

                    }else if(key.isReadable()){
                        System.out.println("=====client isReadable=====");
                        ByteBuffer read = ByteBuffer.allocate(256);
                        clientSocketChannel.read(read);
                        String output = new String(read.array()).trim();
                        System.out.println("Message read from Server: " + output);

                    }else if(key.isWritable()){
                        System.out.println("=====client isWritable=====");
                        ByteBuffer sendBuffer=ByteBuffer.allocate(1024);
                        sendBuffer.put("this is client.".getBytes());
                        sendBuffer.flip();
                        clientSocketChannel.write(sendBuffer);
                        System.out.println("=====writ finish=====");
                    }
                }

            }
        }

}
