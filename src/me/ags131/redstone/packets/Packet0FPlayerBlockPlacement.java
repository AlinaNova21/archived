package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=0F
 * @author Adam
 */
public class Packet0FPlayerBlockPlacement extends Packet{
	public byte pID=(byte)0x0F;
	public int x;
	public byte y;
	public int z;
	public byte direction;
	public slot heldItem;

    
    @Override
    public void read(DataInputStream str) throws IOException {

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(x);
		str.writeByte(y);
		str.writeInt(z);
		str.writeByte(direction);
		str.writeSlot(heldItem);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}