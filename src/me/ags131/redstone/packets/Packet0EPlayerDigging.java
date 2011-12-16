package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=0E
 * @author Adam
 */
public class Packet0EPlayerDigging extends Packet{
	public byte pID=(byte)0x0E;
	public byte status;
	public int x;
	public byte y;
	public int z;
	public byte face;

    
    @Override
    public void read(DataInputStream str) throws IOException {

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeByte(status);
		str.writeInt(x);
		str.writeByte(y);
		str.writeInt(z);
		str.writeByte(face);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}