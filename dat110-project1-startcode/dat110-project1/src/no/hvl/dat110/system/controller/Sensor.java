package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;
import no.hvl.dat110.system.sensor.SensorImpl;

public class Sensor extends RPCStub {
	
	SensorImpl sensor = new SensorImpl();

	private byte RPCID = 1;
	
	public int read() {
		
		// TODO
		// implement marshalling, call and unmarshalling for read RPC method
		int temp = sensor.read();
		
		byte[] request = RPCUtils.marshallInteger(RPCID,temp);
		
		byte[] reply = rpcclient.call(request);
		
		temp = RPCUtils.unmarshallInteger(reply);
		
		return temp;
	}
	
}
