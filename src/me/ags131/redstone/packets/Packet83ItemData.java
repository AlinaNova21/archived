package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=83
 * @author Adam
 */
public class Packet83ItemData extends Packet{
	public short itemType;
	public short itemID;
	public ubyte len;
	public byte[] text;
	public byte pID=(byte)0x83;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		itemType=str.readShort();
		itemID=str.readShort();
		len=str.readUbyte();
		text=str.readByte[]();

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