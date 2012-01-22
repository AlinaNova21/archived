package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=22
 * @author Adam
 */
public class Packet22EntityTeleport extends Packet{
	public int entityID;
	public int x;
	public int y;
	public int z;
	public byte yaw;
	public byte pitch;
	public byte pID=(byte)0x22;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		x=str.readInt();
		y=str.readInt();
		z=str.readInt();
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