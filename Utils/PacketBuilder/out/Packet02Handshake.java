package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=02
 * @author Adam
 */
public class Packet02Handshake extends Packet{
	public String hash;
	public byte pID=(byte)0x02;
	public String username;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		short len=str.readShort();
		hash="";
		for(int I=0;I<len;I++){
			hash+=str.readChar();
		};

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeShort(username.length());
		str.writeChars(username);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}