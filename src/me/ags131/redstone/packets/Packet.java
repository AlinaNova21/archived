/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ags131.redstone.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Adam
 */
public abstract class Packet {

    public abstract void read(DataInputStream str) throws IOException;

    public abstract void write(DataOutputStream str) throws IOException;

    public abstract void run();

    public void send() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
