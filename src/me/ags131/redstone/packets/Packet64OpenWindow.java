package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=64
 * @author Adam
 */
public class Packet64OpenWindow extends Packet{
	public byte windowID;
	public byte inventoryType;
	public String windowTitle;
	public byte slots;
	public byte pID=(byte)0x64;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		windowID=str.readByte();
		inventoryType=str.readByte();
		short len=str.readShort();
		windowTitle="";
		for(int I=0;I<len;I++){
			windowTitle+=str.readChar();
		};
		slots=str.readByte();

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