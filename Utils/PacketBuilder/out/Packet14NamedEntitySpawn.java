package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=14
 * @author Adam
 */
public class Packet14NamedEntitySpawn extends Packet{
	public int entityID;
	public String playerName;
	public int x;
	public int y;
	public int z;
	public byte rotation;
	public byte pitch;
	public short currentItem;
	public byte pID=(byte)0x14;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		short len=str.readShort();
		playerName="";
		for(int I=0;I<len;I++){
			playerName+=str.readChar();
		};
		x=str.readInt();
		y=str.readInt();
		z=str.readInt();
		rotation=str.readByte();
		pitch=str.readByte();
		currentItem=str.readShort();

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