package NIO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelCase1 {


    public static void main(String[] args) throws IOException {

        String str = "Hello, channel";

        FileOutputStream fileOutputStream = new FileOutputStream("e:\\test.txt");

        //通过fileOutputStream获取对应的filechannel
        //FileChannel真实类型是FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();
        
        //创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //对应的buffer就是写
        buffer.put(str.getBytes());

        buffer.flip();

        //把buffer的数据写入到channel中，对应的buffer就是读
        channel.write(buffer);

        //关闭资源
        fileOutputStream.close();

    }
}
