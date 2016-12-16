package net.luniker.lunikertools;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

/**
 * Created by 2sk on 12-Dec-16.
 */
public class Turner implements Listener {
    private static Player lastFired;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
            return;
        }

        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if(e.getPlayer().equals(lastFired)) {
            lastFired = null;
            return;
        }
        lastFired = e.getPlayer();
        Block block = e.getClickedBlock();

        if(Arrays.asList(
                Material.STEP, Material.DOUBLE_STEP, Material.WOOD_STEP, Material.STONE_SLAB2,
                Material.DOUBLE_STONE_SLAB2, Material.PURPUR_SLAB
        ).contains(block.getType())) {
            flip(block, 8);
            return;
        }

        if(Arrays.asList(
                Material.WOOD_STAIRS, Material.COBBLESTONE_STAIRS, Material.BRICK_STAIRS, Material.SMOOTH_STAIRS,
                Material.NETHER_BRICK_STAIRS, Material.SANDSTONE_STAIRS, Material.SPRUCE_WOOD_STAIRS,
                Material.JUNGLE_WOOD_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.QUARTZ_STAIRS, Material.ACACIA_STAIRS,
                Material.DARK_OAK_STAIRS, Material.RED_SANDSTONE_STAIRS, Material.PURPUR_STAIRS
        ).contains(block.getType())) {
            shift(block, 8);
            return;
        }

        if(Arrays.asList(
                Material.SMOOTH_BRICK ,Material.QUARTZ_BLOCK
        ).contains(block.getType())) {
            shift(block, 4);
            return;
        }

        if(block.getType().equals(Material.SIGN_POST)) {
            shift(block, 15);
            return;
        }

        if(Arrays.asList(
                Material.LOG, Material.LOG_2
        ).contains(block.getType())) {
            Byte data = block.getData();

            if(data > 11) {
                data = (byte) (data % 4);
            } else {
                data = (byte) (data + 4);
            }

            block.setData(data);
        }

        switch (block.getType()) {
            case HARD_CLAY: block.setType(Material.BRICK);
                            break;
            case BRICK:     block.setType(Material.HARD_CLAY);
                            break;
            case GRAVEL:    block.setType(Material.GRASS);
                            break;
            case GRASS:     block.setType(Material.DIRT);
                            block.setData((byte) 2);
                            break;
            case DIRT:      if(block.getData() == 2) {
                                block.setType(Material.GRAVEL);
                            }
                            break;
        }

        if(block.getType().equals(Material.WOOL)) {
            Byte data = block.getData();

            if(data == 0) {
                data = 14;
            } else {
                data = 0;
            }

            block.setData(data);
        }
    }

    @Deprecated
    private void flip(Block block, int flipValue) {
        Byte data = block.getData();

        if(data < flipValue) {
            data = (byte) (data + flipValue);
        } else {
            data = (byte) (data - flipValue);
        }

        block.setData(data);
    }

    @Deprecated
    private void shift(Block block, int shiftValue) {
        Byte data = block.getData();
        if(data < shiftValue) {
            data++;
        } else {
            data = 0;
        }

        block.setData(data);
    }
}
