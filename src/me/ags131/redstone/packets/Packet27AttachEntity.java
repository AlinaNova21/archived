package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=27
 * @author Adam
 */
public class Packet27AttachEntity extends Packet{
	public int entityID;
	public int vehicleID;
	public byte pID=(byte)0x27;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		vehicleID=str.readInt();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(entityID);
		str.writeInt(vehicleID);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}