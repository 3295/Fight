package Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    /**
     * 启动服务
     */
    public void run() throws Exception{
        //NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器

        EventLoopGroup bossGroup=new NioEventLoopGroup();//用来接收进来的连接
        EventLoopGroup workerGroup=new NioEventLoopGroup();//用来处理已经被接收的连接

        try {
            //ServerBootstrap 是一个启动 NIO 服务的辅助启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            //这里我们指定使用 NioServerSocketChannel 类来举例说明一个新的 Channel 如何接收进来的连接
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(new ServerHandler());
                }
            });
            /**
             * 对于ChannelOption.SO_BACKLOG的解释：
             * 服务器端TCP内核维护有两个队列，我们称之为A、B队列。客户端向服务器端connect时，会发送带有SYN标志的包（第一次握手），服务器端
             * 接收到客户端发送的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，然后服务器接收到
             * 客户端发送的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。也就是说accept
             * 从B队列中取出完成了三次握手的连接。
             * A队列和B队列的长度之和就是backlog。当A、B队列的长度之和大于ChannelOption.SO_BACKLOG时，新的连接将会被TCP内核拒绝。
             * 所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。要注意的是，backlog对程序支持的
             * 连接数并无影响，backlog影响的只是还没有被accept取出的连接
             */
            bootstrap.option(ChannelOption.SO_BACKLOG, 126)
            .option(ChannelOption.SO_SNDBUF,32*1024)//设置发送数据缓冲大小
            .option(ChannelOption.SO_RCVBUF,32*1024);//设置接受数据缓冲大小
            //childOption() 是提供给由父管道 ServerChannel 接收到的连接
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            //绑定端口，接受连接
            ChannelFuture cf = bootstrap.bind(8765).sync();

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            cf.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception{
        new Server().run();
    }
}
