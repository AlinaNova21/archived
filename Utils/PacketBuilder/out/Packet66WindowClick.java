package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=66
 * @author Adam
 */
public class Packet66WindowClick extends Packet{
	public byte windowID;
	public short slot;
	public byte rightClick;
	public short actionNum;
	public bool shift;
	public slot clickedItem;
	public byte pID=(byte)0x66;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		windowID=str.readByte();
		slot=str.readShort();
		rightClick=str.readByte();
		actionNum=str.readShort();
		shift=str.readBool();
		clickedItem=str.readSlot();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeByte(windowID);
		str.writeShort(slot);
		str.writeByte(rightClick);
		str.writeShort(actionNum);
		str.writeBool(shift);
		str.writeSlot(clickedItem);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}