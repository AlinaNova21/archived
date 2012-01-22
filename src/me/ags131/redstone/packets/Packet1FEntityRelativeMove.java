package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=1F
 * @author Adam
 */
public class Packet1FEntityRelativeMove extends Packet{
	public int entityID;
	public byte dx;
	public byte dy;
	public byte dz;
	public byte pID=(byte)0x1F;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		dx=str.readByte();
		dy=str.readByte();
		dz=str.readByte();

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