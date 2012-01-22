package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=29
 * @author Adam
 */
public class Packet29EntityEffect extends Packet{
	public int entityID;
	public byte effectID;
	public byte amplifier;
	public short duration;
	public byte pID=(byte)0x29;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		effectID=str.readByte();
		amplifier=str.readByte();
		duration=str.readShort();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(entityID);
		str.writeByte(effectID);
		str.writeByte(amplifier);
		str.writeShort(duration);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}