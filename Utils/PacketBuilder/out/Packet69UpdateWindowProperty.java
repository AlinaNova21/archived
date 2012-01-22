package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=69
 * @author Adam
 */
public class Packet69UpdateWindowProperty extends Packet{
	public byte windowID;
	public short property;
	public short value;
	public byte pID=(byte)0x69;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		windowID=str.readByte();
		property=str.readShort();
		value=str.readShort();

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