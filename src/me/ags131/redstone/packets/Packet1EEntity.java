package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=1E
 * @author Adam
 */
public class Packet1EEntity extends Packet{
	public int entityID;
	public byte pID=(byte)0x1E;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();

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