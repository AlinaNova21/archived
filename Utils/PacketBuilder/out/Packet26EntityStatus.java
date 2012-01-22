package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=26
 * @author Adam
 */
public class Packet26EntityStatus extends Packet{
	public int entityID;
	public byte entityStatus;
	public byte pID=(byte)0x26;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		entityStatus=str.readByte();

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