package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=06
 * @author Adam
 */
public class Packet06SpawnPosition extends Packet{
	public int x;
	public int y;
	public int z;
	public byte pID=(byte)0x06;

    
    @Override
    public void read(DataInputStream str) throws IOException {
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