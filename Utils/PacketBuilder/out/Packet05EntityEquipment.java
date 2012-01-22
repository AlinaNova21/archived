package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=05
 * @author Adam
 */
public class Packet05EntityEquipment extends Packet{
	public int entityID;
	public short slot;
	public short itemID;
	public short damage;
	public byte pID=(byte)0x05;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		slot=str.readShort();
		itemID=str.readShort();
		damage=str.readShort();

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