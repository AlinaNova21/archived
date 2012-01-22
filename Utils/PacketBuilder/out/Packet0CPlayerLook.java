package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=0C
 * @author Adam
 */
public class Packet0CPlayerLook extends Packet{
	public byte pID=(byte)0x0C;
	public float yaw;
	public float pitch;
	public boolean onGround;

    
    @Override
    public void read(DataInputStream str) throws IOException {

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeFloat(yaw);
		str.writeFloat(pitch);
		str.writeBoolean(onGround);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}