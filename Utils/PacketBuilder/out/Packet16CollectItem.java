package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=16
 * @author Adam
 */
public class Packet16CollectItem extends Packet{
	public int collectedEntityID;
	public int collectorEntityID;
	public byte pID=(byte)0x16;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		collectedEntityID=str.readInt();
		collectorEntityID=str.readInt();

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