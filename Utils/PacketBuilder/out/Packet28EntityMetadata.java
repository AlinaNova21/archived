package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=28
 * @author Adam
 */
public class Packet28EntityMetadata extends Packet{
	public int entityID;
	public Metadata entityMetadata;
	public byte pID=(byte)0x28;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		entityMetadata=str.readMetadata();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(entityID);
		str.writeMetadata(entityMetadata);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}