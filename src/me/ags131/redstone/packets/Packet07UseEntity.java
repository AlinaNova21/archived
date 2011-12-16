package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=07
 * @author Adam
 */
public class Packet07UseEntity extends Packet{
	public byte pID=(byte)0x07;
	public int user;
	public int target;
	public boolean leftClick;

    
    @Override
    public void read(DataInputStream str) throws IOException {

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(user);
		str.writeInt(target);
		str.writeBoolean(leftClick);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}