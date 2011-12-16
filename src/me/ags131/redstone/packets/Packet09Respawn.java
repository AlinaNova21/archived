package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=09
 * @author Adam
 */
public class Packet09Respawn extends Packet{
	public byte dimension;
	public byte difficulty;
	public byte creative;
	public short worldHeight;
	public long mapSeed;
	public byte pID=(byte)0x09;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		dimension=str.readByte();
		difficulty=str.readByte();
		creative=str.readByte();
		worldHeight=str.readShort();
		mapSeed=str.readLong();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeByte(dimension);
		str.writeByte(difficulty);
		str.writeByte(creative);
		str.writeShort(worldHeight);
		str.writeLong(mapSeed);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}