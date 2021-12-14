package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.awt.event.KeyEvent;


public class NIOServer {

    long []seeds;

	private Selector selector;

	private InetSocketAddress listenAddress;
	private final static int PORT = 9093;

	public NIOServer(String address, int port) throws IOException {
        seeds=new long[3];
		listenAddress = new InetSocketAddress(address, PORT);
        for (int i=0;i<3;i++){
            seeds[i]=new Random().nextInt(55555);
        }
        //this.seed=worldScreen.getSeed();  //获取种子
	}

	/**
	 * Start the server
	 * 
	 * @throws IOException
	 */
    public void startServer() throws IOException {
		this.selector = Selector.open();
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);

		// bind server socket channel to port
		serverChannel.socket().bind(listenAddress);
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

		System.out.println("Server started on port >> " + PORT);

		while (true) {
			// wait for events
			int readyCount = selector.select();
			if (readyCount == 0) {
				continue;
			}

			// process selected keys...
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();

				// Remove key from set so we don't process it twice
				iterator.remove();

				if (!key.isValid()) {
					continue;
				}

				if (key.isAcceptable()) { // Accept client connections
					this.accept(key);
				} else if (key.isReadable()) { // Read from client
					this.read(key);
				} else if (key.isWritable()) {
                    System.out.println(111);
					this.write(key);
				}
			}
		}
	}

	// accept client connection
	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		System.out.println("Connected to: " + remoteAddr);

		/*
		 * Register channel with selector for further IO (record it for read/write
		 * operations, here we have used read operation)
		 */
		channel.register(this.selector, SelectionKey.OP_READ);  //注册完毕后 即是可读取的
	}

	// read from the socket channel  
    //PLUS：将读入的信息传递给所有client
	private void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(128);
		int numRead = -1;
		numRead = channel.read(buffer);   //从channel中将数据读入buffer

		if (numRead == -1) {  //如果没读取到
			Socket socket = channel.socket();
			SocketAddress remoteAddr = socket.getRemoteSocketAddress();
			System.out.println("Connection closed by client: " + remoteAddr);
			channel.close();
			key.cancel();
			return;
		}

		byte[] data = new byte[numRead];
		System.arraycopy(buffer.array(), 0, data, 0, numRead);   //将buffer中的数据放到存入本地data变量
        String info=new String(data);
		System.out.println("Got: " + info);
        HandleInfo(info);
        buffer.clear();
	}
    void write(SelectionKey key) throws IOException{
        SocketChannel channel=(SocketChannel) key.channel();
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        String s="test";
        buffer.put(s.getBytes());  //先将要写的数据放入buffer中
        buffer.flip();
        channel.write(buffer);   //再将buffer中的数据写入channel中
        System.out.println("Write to Client: "+s);
        buffer.clear();

    }

    void write(SelectionKey key,byte[]data) throws IOException{
        if (key.channel() instanceof SocketChannel){
            SocketChannel channel=(SocketChannel) key.channel();
            ByteBuffer buffer=ByteBuffer.allocate(1024);
            //String s="test";
            buffer.put(data);  //先将要写的数据放入buffer中
            buffer.flip();
            channel.write(buffer);   //再将buffer中的数据写入channel中
            System.out.println("Write to Client: "+new String(data));
            buffer.clear();
        }
    }

    private void HandleInfo(String info) throws IOException{
        String[]temp=info.split("/");
        System.out.println("HandleInfo:"+info);
        if (temp[0].equals("Ask")){    //建立连接时，发送种子
            int level=Integer.parseInt(temp[3]);
            String s="Seed/"+seeds[level]+"/";
            byte[]data=s.getBytes();
            //System.out.println(1);
            write_to_all(data);
        }
        else{                         //连接已建立，发送操作
            //System.out.println(2);
            byte[]data=info.getBytes();
            write_to_all(data);
        }
    }

    public void write_to_all(byte[]data) throws IOException{
        
        Set<SelectionKey> allKeys=selector.keys();
        Iterator iterator = allKeys.iterator();
        //System.out.println(allKeys.size());
        SelectionKey key = (SelectionKey) iterator.next();
        write(key,data);
        
        //int count=0;
        while (iterator.hasNext()) {
            key = (SelectionKey) iterator.next();
            //System.out.println("write"+count);
            //count++;
            write(key,data);
        }
        
    }
}
