package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=0B
 * @author Adam
 */
public class Packet0BPlayerPosition extends Packet{
	public byte pID=(byte)0x0B;
	public double x;
	public double y;
	public double stance;
	public double z;
	public boolean onGround;

    
    @Override
    public void read(DataInputStream str) throws IOException {

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeDouble(x);
		str.writeDouble(y);
		str.writeDouble(stance);
		str.writeDouble(z);
		str.writeBoolean(onGround);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}