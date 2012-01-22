package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=67
 * @author Adam
 */
public class Packet67SetSlot extends Packet{
	public byte windowID;
	public short slot;
	public slot data;
	public byte pID=(byte)0x67;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		windowID=str.readByte();
		slot=str.readShort();
		data=str.readSlot();

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