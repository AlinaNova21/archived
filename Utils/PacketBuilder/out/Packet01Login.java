package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=01
 * @author Adam
 */
public class Packet01Login extends Packet{
	public int entityID;
	public String unused;
	public long mapSeed;
	public String levelType;
	public int serverMode;
	public byte dimension;
	public byte difficulty;
	public byte worldHeight;
	public byte maxPlayers;
	public byte pID=(byte)0x01;
	public int protocol;
	public String username;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		entityID=str.readInt();
		short len=str.readShort();
		unused="";
		for(int I=0;I<len;I++){
			unused+=str.readChar();
		};
		mapSeed=str.readLong();
		short len=str.readShort();
		levelType="";
		for(int I=0;I<len;I++){
			levelType+=str.readChar();
		};
		serverMode=str.readInt();
		dimension=str.readByte();
		difficulty=str.readByte();
		worldHeight=str.readByte();
		maxPlayers=str.readByte();

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(protocol);
		str.writeShort(username.length());
		str.writeChars(username);
		str.writeLong(0);
		str.writeInt(0);
		str.writeByte(0);
		str.writeByte(0);
		str.writeByte(0);
		str.writeByte(0);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}