package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=12
 * @author Adam
 */
public class Packet12Animation extends Packet{
	public int entityID;
	public byte animation;
	public byte pID=(byte)0x12;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		animation=str.readByte();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(entityID);
		str.writeByte(animation);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}