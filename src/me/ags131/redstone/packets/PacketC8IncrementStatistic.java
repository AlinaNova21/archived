package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=C8
 * @author Adam
 */
public class PacketC8IncrementStatistic extends Packet{
	public int statID;
	public byte amount;
	public byte pID=(byte)0xC8;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		statID=str.readInt();
		amount=str.readByte();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(statID);
		str.writeByte(amount);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}