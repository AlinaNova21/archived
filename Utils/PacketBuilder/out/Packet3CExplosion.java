package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=3C
 * @author Adam
 */
public class Packet3CExplosion extends Packet{
	public double x;
	public double y;
	public double z;
	public float unknown;
	public int count;
	public byte[3][] records;
	public byte pID=(byte)0x3C;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		x=str.readDouble();
		y=str.readDouble();
		z=str.readDouble();
		unknown=str.readFloat();
		count=str.readInt();
		records=str.readByte[3][]();

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