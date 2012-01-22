package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=46
 * @author Adam
 */
public class Packet46NewOrInvalidState extends Packet{
	public byte reason;
	public byte gamemode;
	public byte pID=(byte)0x46;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		reason=str.readByte();
		gamemode=str.readByte();

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