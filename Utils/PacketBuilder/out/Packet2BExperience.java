package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=2B
 * @author Adam
 */
public class Packet2BExperience extends Packet{
	public float expBar;
	public short level;
	public short totalExp;
	public byte pID=(byte)0x2B;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		expBar=str.readFloat();
		level=str.readShort();
		totalExp=str.readShort();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeFloat(expBar);
		str.writeShort(level);
		str.writeShort(totalExp);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}