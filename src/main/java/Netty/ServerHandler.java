package Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * YtJinC 2018年5月7日 15:54:12
 * 处理服务端 channel.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter{


    //每当从客户端收到新的数据时，这个方法会在收到消息时被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        //丢弃
        //(ByteBuf 是一个引用计数对象，这个对象必须显示地调用 release() 方法来释放)
        //((ByteBuf)msg).release();
        //ReferenceCountUtil.release(msg);

        //接收
        ByteBuf in = (ByteBuf) msg;
        try {
            System.out.println("Server:"+in.toString(CharsetUtil.UTF_8));
            //响应
            ctx.write(Unpooled.copiedBuffer("send by 服务端".getBytes()));
        }finally {
            //in.release();
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //ctx.write(Object) 方法不会使消息写入到通道上，
        // 他被缓冲在了内部，你需要调用 ctx.flush() 方法来把缓冲区中数据强行输出。
        // 或者你可以用更简洁的 cxt.writeAndFlush(msg) 以达到同样的目的。
        ctx.flush();
    }

    //当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();//出现异常时关闭连接

    }
}
