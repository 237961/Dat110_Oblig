package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;
	
	// hashmap to register RPC methods which are required to implement RPCImpl
	
	private HashMap<Integer,RPCImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Integer,RPCImpl>();
		
		// the stop RPC methods is built into the server
		services.put((int)RPCCommon.RPIDSTOP,new RPCServerStopImpl());
	}
	
	public void run() {
		
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
		
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		
		boolean stop = false;
		
		while (!stop) {

			try {
				
				System.out.println("Failure 2.10");
				  
				Message message = connection.receive();
				
				System.out.println("Failure 2.11");
				  
				byte[] serverreceieved = message.getData();
				  
				System.out.println("message");
				
				if(serverreceieved.length != 0) {
					
					int rpcid = serverreceieved[0];
					  
					byte[] serverReply = services.get(rpcid).invoke(serverreceieved);
					  
					Message reply = new Message(serverReply);
					  
					connection.send(reply);
					
					
					if (rpcid == RPCCommon.RPIDSTOP) {
						  stop = true;
					}
				}
				  
			 } catch (Exception e) {
				e.printStackTrace();
			 }

		   
		   // TODO
		   // - receive message containing RPC request
		   // - find the identifier for the RPC methods to invoke
		   // - lookup the method to be invoked
		   // - invoke the method
		   // - send back message containing RPC reply
		}
	
	}
	
	public void register(int rpcid, RPCImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {
		connection.close();
		msgserver.stop();
		
	}
}
