import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {

        //创建一个线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建一个服务端监听6666端口
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器已启动，监听6666端口");
        //当监听到客户端链接时，启动一个线程与之通讯
        while (true) {
            final Socket socket = serverSocket.accept();
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handle(socket);
                }
            });
        }
    }

    public static void handle(Socket socket){
        System.out.println("启动了线程：["+ Thread.currentThread().getName()+"]进行通讯");
        byte[] bytes = new byte[1024];
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            while (true) {
                final int read = inputStream.read(bytes);
                if(read != -1){
                    System.out.println(Thread.currentThread().getName() + "--接受到信息:" + new String(bytes,0,read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
