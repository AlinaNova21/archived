package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=36
 * @author Adam
 */
public class Packet36BlockAction extends Packet{
	public int x;
	public short y;
	public int z;
	public byte byte1;
	public byte byte2;
	public byte pID=(byte)0x36;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		x=str.readInt();
		y=str.readShort();
		z=str.readInt();
		byte1=str.readByte();
		byte2=str.readByte();

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