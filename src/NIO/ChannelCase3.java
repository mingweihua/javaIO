package NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelCase3 {


    //针对ChannelCase2的复制文件，这里直接实验channel的拷贝

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("e:\\test.txt");
        FileChannel channel1 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("e:\\test_copy.txt");
        FileChannel channel2 = fileOutputStream.getChannel();

        channel2.transferFrom(channel1,0,channel1.size());

        fileInputStream.close();
        fileOutputStream.close();

    }
}
