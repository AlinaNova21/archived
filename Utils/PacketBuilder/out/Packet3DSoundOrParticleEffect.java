package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=3D
 * @author Adam
 */
public class Packet3DSoundOrParticleEffect extends Packet{
	public int effectID;
	public int x;
	public byte y;
	public int z;
	public int data;
	public byte pID=(byte)0x3D;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		effectID=str.readInt();
		x=str.readInt();
		y=str.readByte();
		z=str.readInt();
		data=str.readInt();

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