package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=0D
 * @author Adam
 */
public class Packet0DPlayerPositionAndLook extends Packet{
	public double x;
	public double stance;
	public double y;
	public double z;
	public float yaw;
	public float pitch;
	public boolean onGround;
	public byte pID=(byte)0x0D;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		x=str.readDouble();
		stance=str.readDouble();
		y=str.readDouble();
		z=str.readDouble();
		yaw=str.readFloat();
		pitch=str.readFloat();
		onGround=str.readBoolean();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeDouble(x);
		str.writeDouble(y);
		str.writeDouble(stance);
		str.writeDouble(z);
		str.writeFloat(yaw);
		str.writeFloat(pitch);
		str.writeBoolean(onGround);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}