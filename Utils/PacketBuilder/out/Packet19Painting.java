package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=19
 * @author Adam
 */
public class Packet19Painting extends Packet{
	public int entityID;
	public String title;
	public int x;
	public int y;
	public int z;
	public int direction;
	public byte pID=(byte)0x19;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		short len=str.readShort();
		title="";
		for(int I=0;I<len;I++){
			title+=str.readChar();
		};
		x=str.readInt();
		y=str.readInt();
		z=str.readInt();
		direction=str.readInt();

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