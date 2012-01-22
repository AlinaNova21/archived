package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=17
 * @author Adam
 */
public class Packet17AddObjectOrVehicle extends Packet{
	public int entityID;
	public byte type;
	public int x;
	public int y;
	public int z;
	public CONDITIONAL FIX;
	public int fbThrowersEID;
	public short xSpeed;
	public short ySpeed;
	public short zSpeed;
	public byte pID=(byte)0x17;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		type=str.readByte();
		x=str.readInt();
		y=str.readInt();
		z=str.readInt();
		FIX=str.readCONDITIONAL();
		fbThrowersEID=str.readInt();
		xSpeed=str.readShort();
		ySpeed=str.readShort();
		zSpeed=str.readShort();

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