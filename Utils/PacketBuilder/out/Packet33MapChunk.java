package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=33
 * @author Adam
 */
public class Packet33MapChunk extends Packet{
	public int x;
	public short y;
	public int z;
	public byte sizeX;
	public byte sizeY;
	public byte sizeZ;
	public int size;
	public byte[] data;
	public byte pID=(byte)0x33;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		x=str.readInt();
		y=str.readShort();
		z=str.readInt();
		sizeX=str.readByte();
		sizeY=str.readByte();
		sizeZ=str.readByte();
		size=str.readInt();
		data=str.readByte[]();

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