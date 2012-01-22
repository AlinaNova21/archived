package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=1A
 * @author Adam
 */
public class Packet1AExpOrb extends Packet{
	public int entityID;
	public int x;
	public int y;
	public int z;
	public short count;
	public byte pID=(byte)0x1A;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		x=str.readInt();
		y=str.readInt();
		z=str.readInt();
		count=str.readShort();

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