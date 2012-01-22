package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=15
 * @author Adam
 */
public class Packet15PickupSpawn extends Packet{
	public int entityID;
	public short item;
	public byte count;
	public short damage;
	public int x;
	public int y;
	public int z;
	public byte pitch;
	public byte yaw;
	public byte roll;
	public byte pID=(byte)0x15;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		item=str.readShort();
		count=str.readByte();
		damage=str.readShort();
		x=str.readInt();
		y=str.readInt();
		z=str.readInt();
		pitch=str.readByte();
		yaw=str.readByte();
		roll=str.readByte();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(entityID);
		str.writeShort(item);
		str.writeByte(count);
		str.writeShort(damage);
		str.writeInt(x);
		str.writeInt(y);
		str.writeInt(z);
		str.writeByte(pitch);
		str.writeByte(yaw);
		str.writeByte(roll);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}