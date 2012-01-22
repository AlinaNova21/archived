package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=6C
 * @author Adam
 */
public class Packet6CEnchantItem extends Packet{
	public byte pID=(byte)0x6C;
	public byte windowID;
	public byte enchantment;

    
    @Override
    public void read(DataInputStream str) throws IOException {

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeByte(windowID);
		str.writeByte(enchantment);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}