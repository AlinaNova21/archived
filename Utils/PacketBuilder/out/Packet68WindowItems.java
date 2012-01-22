package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=68
 * @author Adam
 */
public class Packet68WindowItems extends Packet{
	public byte windowID;
	public short count;
	public slot[] data;
	public byte pID=(byte)0x68;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		windowID=str.readByte();
		count=str.readShort();
		data=str.readSlot[]();

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