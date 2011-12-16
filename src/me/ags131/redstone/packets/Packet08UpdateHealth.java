package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=08
 * @author Adam
 */
public class Packet08UpdateHealth extends Packet{
	public short health;
	public short food;
	public float foodSaturation;
	public byte pID=(byte)0x08;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		health=str.readShort();
		food=str.readShort();
		foodSaturation=str.readFloat();

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