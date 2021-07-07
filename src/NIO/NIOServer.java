package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NIOServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个selector对象
        Selector selector = Selector.open();

        //绑定端口6666
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把服务端serverSocketChannel注册到selector，关心时间为op_Accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待，等待客户端链接
        while (true) {

            if(selector.select(1000) == 0){

            }
        }

    }
}
