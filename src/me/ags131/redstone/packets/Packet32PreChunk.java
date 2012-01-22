package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=32
 * @author Adam
 */
public class Packet32PreChunk extends Packet{
	public int x;
	public int z;
	public bool mode;
	public byte pID=(byte)0x32;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		x=str.readInt();
		z=str.readInt();
		mode=str.readBool();

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