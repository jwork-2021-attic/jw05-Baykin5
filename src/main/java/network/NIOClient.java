package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import screen.WorldScreen;
import java.awt.event.KeyEvent;

public class NIOClient {
    WorldScreen worldScreen;
    int level;
    long seed;
    int id;

    ByteBuffer buffer;
    InetSocketAddress hostAddress;
    SocketChannel clientChannel;

    public NIOClient(WorldScreen worldScreen, int id) throws IOException {
        this.worldScreen=worldScreen;
        this.id=id;
        buffer = ByteBuffer.allocate(128);
        hostAddress = new InetSocketAddress("localhost", 9093);
        clientChannel = SocketChannel.open(hostAddress);
        level=0;
        seed = -1;
    }

    void write(String s) throws IOException {
        byte[]data=s.getBytes();
        buffer.put(data);
        buffer.flip();
        clientChannel.write(buffer); // 将buffer中的数据写入channel中
        System.out.println("Write to Server: " + s);
        buffer.clear();
        //System.out.println(buffer.toString());
    }

    public void read() throws IOException { // 将server发送的消息读入并进行处理
        int numRead = -1;
        numRead = clientChannel.read(buffer);

        if (numRead == 0 || numRead == -1) {
            //System.out.println("Client Read Nothing");
            return;
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        String info = new String(data);
        System.out.println("Got from Server: " + info);
        HandleInfo(info);
        buffer.clear();
        //System.out.println(buffer.toString());
    }

    public void Action(int code) throws IOException {
        String data = "Action/" + id +"/"+ code+"/";
        write(data);
    }

    public void AskForSeed() throws IOException, InterruptedException{
        if (level!=worldScreen.getLevel()){
            level=worldScreen.getLevel();
        }
        write("Ask/for/seed/"+worldScreen.getLevel()+"/");  //根据当前关卡等级发送等级请求
    }

    void HandleInfo(String info) { // 对接收到的信息进行处理
        String[] temp=info.split("/");
        if (temp[0].equals("Seed")){   //收到新的种子 检查关卡等级
            if (level!=worldScreen.getLevel()){
                level=worldScreen.getLevel();
            }
            seed=Long.parseLong(temp[1]);
            worldScreen.setSeed(seed);
        }
        else if (temp[0].equals("Action"))
        {
            int playerid=Integer.parseInt(temp[1]);
            int keyCode=Integer.parseInt(temp[2]);
            if (keyCode==10){
                worldScreen.StartGame();
            }
            else{
                worldScreen.playerAction(playerid,keyCode);
            }
        }
    }

    public void startClient() throws IOException, InterruptedException {
        System.out.println("Client... started");
        while (seed == -1) {
            TimeUnit.MILLISECONDS.sleep(500);
            write("Ask/for/seed/0/");
            read();
        }
    }



    public long getSeed() {
        return seed;
    }
}
