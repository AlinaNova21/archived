package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=34
 * @author Adam
 */
public class Packet34MultiBlockChange extends Packet{
	public int x;
	public int z;
	public short size;
	public short[] coord;
	public byte[] types;
	public byte[] metadata;
	public byte pID=(byte)0x34;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		x=str.readInt();
		z=str.readInt();
		size=str.readShort();
		coord=str.readShort[]();
		types=str.readByte[]();
		metadata=str.readByte[]();

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