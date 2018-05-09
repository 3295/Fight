package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO demo
 * 2018年4月23日 15:36:15
 * YtJinC
 */
public class ServerTest{

    public static void main(String[] args) {
        try {

            System.out.println("===========serverInit===========");
            //打开通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //监听的端口
            serverSocketChannel.socket().bind(new InetSocketAddress(8686));
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);

            //创建一个选择器
            Selector selector = Selector.open();
            //把通道注册到选择器里面,等待连接
            //这里只能先accept，然后再read，否则会报如下错误
            // java.lang.IllegalArgumentException
            //不知道为什么,也许看源码能明白？
            int interestSet = SelectionKey.OP_ACCEPT |SelectionKey.OP_READ | SelectionKey.OP_WRITE;
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            SocketChannel client=null;



            while (true) {
                //阻塞到至少有一个通道在你注册的事件上就绪了
                //selectNow()不会阻塞，不管什么通道就绪都立刻返回0
                int readyChannels = selector.select();
                if (readyChannels == 0) continue;
                //获取已就绪的通道
                Set selectedKeys = selector.selectedKeys();

                Iterator iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey theKey = (SelectionKey) iterator.next();
                    if (theKey.isAcceptable()) {
                        System.out.println("...accept:客户端请求连接...");
                        //获取当前通道
                        ServerSocketChannel server=(ServerSocketChannel) theKey.channel();
                        // 接受连接。
                        client = server.accept();
                        client.configureBlocking(false);
                        // 注册到selector，等待请求
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("...accept:接受客户端连接请求:"+client.getRemoteAddress());
                        ByteBuffer send=ByteBuffer.allocate(1024);
                        send.put("this's server.".getBytes());
                        send.flip();
                        client.write(send);
                        System.out.println("...accept:发数据给客户端...");
                    }  else if (theKey.isReadable()) {
                        System.out.println("...read:读取数据...");
                        client = (SocketChannel) theKey.channel();
                        ByteBuffer read = ByteBuffer.allocate(256);
                        client.read(read);
                        String output = new String(read.array()).trim();
                        System.out.println("Message read from client: " + output);
                        ByteBuffer send=ByteBuffer.allocate(1024);
                        send.put("this's server.".getBytes());
                        send.flip();
                        client.write(send);
                    } else if (theKey.isWritable()) {
                        System.out.println("...write...");
                        client =(SocketChannel) theKey.channel();
                        ByteBuffer send=ByteBuffer.allocate(1024);
                        send.put("this's server.".getBytes());
                        send.flip();
                        client.write(send);
                    }else if (theKey.isConnectable()) {
                        System.out.println("...connect...");

                    }
                    //处理之后要移除，选择器不会自己移除
                    iterator.remove();
                }


            }
        }catch (Exception e){
            System.out.println("error::"+e.getMessage());
        }
    }

}
