package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ID=82
 * @author Adam
 */
public class Packet82UpdateSign extends Packet{
	public int x;
	public short y;
	public int z;
	public String text1;
	public String text2;
	public String text3;
	public String text4;
	public byte pID=(byte)0x82;

    
    @Override
    public void read(DataInputStream str) throws IOException {
		x=str.readInt();
		y=str.readShort();
		z=str.readInt();
		short len=str.readShort();
		text1="";
		for(int I=0;I<len;I++){
			text1+=str.readChar();
		};
		short len=str.readShort();
		text2="";
		for(int I=0;I<len;I++){
			text2+=str.readChar();
		};
		short len=str.readShort();
		text3="";
		for(int I=0;I<len;I++){
			text3+=str.readChar();
		};
		short len=str.readShort();
		text4="";
		for(int I=0;I<len;I++){
			text4+=str.readChar();
		};

    }

    @Override
    public void write(DataOutputStream str) throws IOException {
		str.writeByte(pID);
		str.writeInt(x);
		str.writeShort(y);
		str.writeInt(z);
		str.writeShort(text1.length());
		str.writeChars(text1);
		str.writeShort(text2.length());
		str.writeChars(text2);
		str.writeShort(text3.length());
		str.writeChars(text3);
		str.writeShort(text4.length());
		str.writeChars(text4);

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}