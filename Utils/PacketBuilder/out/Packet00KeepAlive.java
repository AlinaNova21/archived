package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=00
 * @author Adam
 */
public class Packet00KeepAlive extends Packet{
	public int ID;
	public byte pID=(byte)0x00;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		ID=str.readInt();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(ID);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}