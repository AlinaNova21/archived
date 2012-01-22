package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=65
 * @author Adam
 */
public class Packet65CloseWindow extends Packet{
	public byte windowID;
	public byte pID=(byte)0x65;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		windowID=str.readByte();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeByte(windowID);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}