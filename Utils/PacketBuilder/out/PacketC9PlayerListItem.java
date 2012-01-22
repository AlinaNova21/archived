package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=C9
 * @author Adam
 */
public class PacketC9PlayerListItem extends Packet{
	public String name;
	public bool online;
	public short ping;
	public byte pID=(byte)0xC9;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		short len=str.readShort();
		name="";
		for(int I=0;I<len;I++){
			name+=str.readChar();
		};
		online=str.readBool();
		ping=str.readShort();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}