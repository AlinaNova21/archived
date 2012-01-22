package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=20
 * @author Adam
 */
public class Packet20EntityLook extends Packet{
	public int entityID;
	public byte yaw;
	public byte pitch;
	public byte pID=(byte)0x20;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
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