/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ags131.spoutfly;

import org.spoutcraft.spoutcraftapi.addon.java.JavaAddon;

/**
 *
 * @author Adam
 */
public class SpoutFly extends JavaAddon {
    public boolean active=false;
    public spoutFlyWidget stats;
    
    @Override
    public void onEnable() {
        stats=new spoutFlyWidget(this);
        this.getClient().getActivePlayer().getMainScreen().attachWidget(this,stats);
        System.out.println("[SpoutFly] Enabled");
    }

    @Override
    public void onDisable() {
        System.out.println("[SpoutFly] Disabled");
    }
    
}
