package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGathering {

    public static void main(String[] args) throws IOException {


        //测试使用Scattering，Gathering
        //这里使用seerverSocketChannel，网络

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端链接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        int length = 8; //假定从客户端接受8个字节
        //循环读取
        while (true){
            int byteRead = 0;
            while (byteRead<length){
                long read = socketChannel.read(byteBuffers);
                //累计读取的字节数
                byteRead += read;
                System.out.println(byteRead);
                //使用流打印，看看buffer的position和limit
                Arrays.asList(byteBuffers).stream().map(buffer -> ""+buffer.position()
                        + " ," + buffer.limit()).forEach(System.out::println);

                //将所有buffer进行flip
                Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

                //将数据读取到客户端
                long write = 0;
                while (write<length) {
                    long l = socketChannel.write(byteBuffers);
                    write += l;
                }

                //将所有的buffer进行clear
                Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());


            }
        }


    }
}
