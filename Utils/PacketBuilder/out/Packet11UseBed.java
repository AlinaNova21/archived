package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=11
 * @author Adam
 */
public class Packet11UseBed extends Packet{
	public int entityID;
	public byte inBed;
	public int x;
	public byte y;
	public byte z;
	public byte pID=(byte)0x11;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		inBed=str.readByte();
		x=str.readInt();
		y=str.readByte();
		z=str.readByte();

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