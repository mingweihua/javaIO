package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口6666
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //得到一个selector对象
        Selector selector = Selector.open();
        //把服务端serverSocketChannel注册到selector，关心时间为op_Accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待，等待客户端链接
        while (true) {

            if(selector.select(2000) == 0){
                System.out.println("等待2s，没有客户端连接");
                continue;
            }

            //如果返回大于0，获取到相关的selectionKey集合
            //selector.selectedKeys()返回的事关注时间的集合，可以反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //根据期关注的事件，执行相应的逻辑业务
                if (selectionKey.isAcceptable()){
                    //本身accept()这个方法事阻塞的，但是由于基于事件驱动，执行到这证明已经有连接时间发生
                    //所以accept()并不会阻塞
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);

                    //将该channel注册到selector，但是其关注的时间是写
                    //同时关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                if (selectionKey.isReadable()){
                    //通过selectionKey反向获取通道
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("from客户端："+new String(buffer.array()));
                }

                //手动从集合中移去当前selectionKey，防止重复操作；
                iterator.remove();

            }

        }

    }
}
