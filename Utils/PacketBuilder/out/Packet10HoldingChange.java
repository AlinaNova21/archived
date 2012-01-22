package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=10
 * @author Adam
 */
public class Packet10HoldingChange extends Packet{
	public short slot;
	public byte pID=(byte)0x10;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		slot=str.readShort();

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