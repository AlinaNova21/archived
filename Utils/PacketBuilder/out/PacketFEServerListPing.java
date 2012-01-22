package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=FE
 * @author Adam
 */
public class PacketFEServerListPing extends Packet{
	public byte pID=(byte)0xFE;

    
    @Override
    public void read(DataInputStream str) throws IOException {

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