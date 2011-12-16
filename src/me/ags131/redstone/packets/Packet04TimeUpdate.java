package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=04
 * @author Adam
 */
public class Packet04TimeUpdate extends Packet{
	public long time;
	public byte pID=(byte)0x04;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		time=str.readLong();

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