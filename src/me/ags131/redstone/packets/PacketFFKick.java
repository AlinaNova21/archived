package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=FF
 * @author Adam
 */
public class PacketFFKick extends Packet{
	public String reason;
	public byte pID=(byte)0xFF;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		short len=str.readShort();
		reason="";
		for(int I=0;I<len;I++){
			reason+=str.readChar();
		};

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeShort(reason.length());
		str.writeChars(reason);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}