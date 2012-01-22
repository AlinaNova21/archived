package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=21
 * @author Adam
 */
public class Packet21EntityLookandRelativeMove extends Packet{
	public int entityID;
	public byte dx;
	public byte dy;
	public byte dz;
	public byte yaw;
	public byte pitch;
	public byte pID=(byte)0x21;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		dx=str.readByte();
		dy=str.readByte();
		dz=str.readByte();
		yaw=str.readByte();
		pitch=str.readByte();

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