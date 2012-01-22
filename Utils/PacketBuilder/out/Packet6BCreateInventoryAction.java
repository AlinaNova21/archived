package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=6B
 * @author Adam
 */
public class Packet6BCreateInventoryAction extends Packet{
	public short slot;
	public slot item;
	public byte pID=(byte)0x6B;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		slot=str.readShort();
		item=str.readSlot();

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