package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=47
 * @author Adam
 */
public class Packet47Thunderbolt extends Packet{
	public int entityID;
	public bool unknown;
	public int x;
	public int y;
	public int z;
	public byte pID=(byte)0x47;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		unknown=str.readBool();
		x=str.readInt();
		y=str.readInt();
		z=str.readInt();

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