package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=35
 * @author Adam
 */
public class Packet35BlockChange extends Packet{
	public int x;
	public byte y;
	public int z;
	public byte blockType;
	public byte blockMetadata;
	public byte pID=(byte)0x35;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		x=str.readInt();
		y=str.readByte();
		z=str.readInt();
		blockType=str.readByte();
		blockMetadata=str.readByte();

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