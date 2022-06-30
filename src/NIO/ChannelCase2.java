package NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelCase2 {


    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("e:\\test.txt");
        FileChannel channel1 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("e:\\test_copy.txt");
        FileChannel channel2 = fileOutputStream.getChannel();
        
        //创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (true){

            //必须对这个buffer执行clear，也就是对标志重置，否则循环将不会退出
            buffer.clear();

            int read = channel1.read(buffer);
            if(read == -1){
                break;
            }

            //将buffer数据写入到channel2,也就是读取buffer数据
            buffer.flip();
            channel2.write(buffer);

        }

        fileInputStream.close();
        fileOutputStream.close();

    }
}
