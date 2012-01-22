package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=03
 * @author Adam
 */
public class Packet03Chat extends Packet{
	public String message;
	public byte pID=(byte)0x03;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		short len=str.readShort();
		message="";
		for(int I=0;I<len;I++){
			message+=str.readChar();
		};

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeShort(message.length());
		str.writeChars(message);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}