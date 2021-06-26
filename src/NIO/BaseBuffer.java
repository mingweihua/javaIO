package NIO;

import java.nio.IntBuffer;

public class BaseBuffer {

    public static void main(String[] args) {
        //举例说明Buffer的使用
        //创建一个buffer，其中5表示可以存储5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //通过put方法往Buffer中存储数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }


        //要从Buffer中获取数据，必须读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
