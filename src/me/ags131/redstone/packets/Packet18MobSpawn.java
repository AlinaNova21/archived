package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=18
 * @author Adam
 */
public class Packet18MobSpawn extends Packet{
	public int entityID;
	public byte type;
	public int x;
	public int y;
	public int z;
	public byte yaw;
	public byte pitch;
	public metadata metadata;
	public byte pID=(byte)0x18;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		type=str.readByte();
		x=str.readInt();
		y=str.readInt();
		z=str.readInt();
		yaw=str.readByte();
		pitch=str.readByte();
		metadata=str.readMetadata();

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