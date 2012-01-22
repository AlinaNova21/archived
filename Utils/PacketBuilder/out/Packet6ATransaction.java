package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=6A
 * @author Adam
 */
public class Packet6ATransaction extends Packet{
	public byte windowID;
	public short actionNum;
	public bool accepted;
	public byte pID=(byte)0x6A;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		windowID=str.readByte();
		actionNum=str.readShort();
		accepted=str.readBool();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeByte(windowID);
		str.writeShort(actionNum);
		str.writeBool(accepted);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}