package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=13
 * @author Adam
 */
public class Packet13EntityAction extends Packet{
	public byte pID=(byte)0x13;
	public int entityID;
	public byte actionID;

    
    @Override
    public void read(DataInputStream str) throws IOException {

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(entityID);
		str.writeByte(actionID);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}