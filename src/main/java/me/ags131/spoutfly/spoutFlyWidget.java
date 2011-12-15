/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ags131.spoutfly;

import org.lwjgl.input.Keyboard;
import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.gui.GenericLabel;

/**
 *
 * @author Adam
 */
class spoutFlyWidget extends GenericLabel {

    private boolean keyDown = false;
    public SpoutFly addon;

    public spoutFlyWidget(SpoutFly addon) {
        this.addon = addon;
        this.setX(10);
        this.setY(10);
    }

    @Override
    public void render() {
        Keyboard.poll();
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            if (!keyDown) {
                addon.active = !addon.active;
            }
            keyDown = true;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            addon.getClient().setGravityMultiplier(.1F * addon.getClient().getPlayerSpeed());
        } else if (keyDown) {
            keyDown = false;
        }
        String msg = "Flying ";
        if (addon.active) {
            msg += "Enabled";
        } else {
            msg += "Disabled";
        }
        this.setText(msg);
        super.render();
    }
}
