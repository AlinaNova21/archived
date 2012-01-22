package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=FA
 * @author Adam
 */
public class PacketFAPluginMessage extends Packet{
	public String channel;
	public short length;
	public byte[] data;
	public byte pID=(byte)0xFA;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		short len=str.readShort();
		channel="";
		for(int I=0;I<len;I++){
			channel+=str.readChar();
		};
		length=str.readShort();
		data=str.readByte[]();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeShort(channel.length());
		str.writeChars(channel);
		str.writeShort(length);
		str.writeByte[](data);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}