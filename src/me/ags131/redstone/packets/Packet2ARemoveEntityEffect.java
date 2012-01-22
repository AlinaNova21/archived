package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=2A
 * @author Adam
 */
public class Packet2ARemoveEntityEffect extends Packet{
	public int entityID;
	public byte effectID;
	public byte pID=(byte)0x2A;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		effectID=str.readByte();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(entityID);
		str.writeByte(effectID);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}