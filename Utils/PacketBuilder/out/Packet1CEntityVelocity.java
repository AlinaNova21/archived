package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=1C
 * @author Adam
 */
public class Packet1CEntityVelocity extends Packet{
	public int entityID;
	public short x;
	public short y;
	public short z;
	public byte pID=(byte)0x1C;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		x=str.readShort();
		y=str.readShort();
		z=str.readShort();

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