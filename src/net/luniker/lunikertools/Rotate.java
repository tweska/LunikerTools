package net.luniker.lunikertools;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Rotate implements Listener {
    private Material tool;

    public Rotate() {
        this(Material.STICK);
    }

    public Rotate(Material tool) {
        this.tool = tool;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Material used = player.getInventory().getItemInMainHand().getType();

        if(e.getHand() != EquipmentSlot.HAND || !used.equals(tool) || e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if(!rotateBlock(e.getClickedBlock())) {
            player.sendMessage("This block cannot be rotated!");
        }
    }

    public static boolean rotateBlock(Block block) {
        switch(block.getType()) {
            case STEP:
            case DOUBLE_STEP:
            case WOOD_STEP:
            case STONE_SLAB2:
            case PURPUR_SLAB:
            case DOUBLE_STONE_SLAB2:
                incrementBlockData(block, 8, 16);
                return true;
            case ENDER_CHEST:
            case FURNACE:
            case BURNING_FURNACE:
                incrementBlockData(block, 1, 2, 5);
                return true;
            case CHEST:
            case TRAPPED_CHEST:
                rotateChest(block);
                return true;
            case HOPPER:
                rotateHopper(block);
                return true;
            case LOG:
            case LOG_2:
                incrementBlockData(block, 4, 15);
                return true;
            case ANVIL:
                incrementBlockData(block, 11);
                return true;
            case WOOD_STAIRS:
            case SPRUCE_WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case DARK_OAK_STAIRS:
            case ACACIA_STAIRS:
            case COBBLESTONE_STAIRS:
            case BRICK_STAIRS:
            case SMOOTH_STAIRS:
            case NETHER_BRICK_STAIRS:
            case QUARTZ_STAIRS:
            case SANDSTONE_STAIRS:
            case RED_SANDSTONE_STAIRS:
            case PURPUR_STAIRS:
                incrementBlockData(block, 8);
                return true;
            case RAILS:
                incrementBlockData(block, 9);
                return true;
            case POWERED_RAIL:
            case ACTIVATOR_RAIL:
            case DETECTOR_RAIL:
                rotateRedstoneRail(block);
                return true;
            case PISTON_BASE:
            case PISTON_STICKY_BASE:
            case PUMPKIN:
            case JACK_O_LANTERN:
                incrementBlockData(block, 5);
                return true;
            case SIGN_POST:
                incrementBlockData(block, 15);
                return true;
        }
        return false;
    }

    private static void incrementBlockData(Block block, int max) {
        incrementBlockData(block, 1, 0, max);
    }

    private static void incrementBlockData(Block block, int n, int max) {
        incrementBlockData(block, n, 0, max);
    }

    private static void incrementBlockData(Block block, int n, int min, int max) {
        byte data = block.getData();

        if(data > max) {
            return;
        }

        data += n;

        if(data > max) {
            data = (byte)(data % n + min);
        }

        block.setData(data);
    }

    private static void rotateHopper(Block block) {
        byte data = block.getData();

        if(data < 5) {
            if(++data == 1) {
                data++;
            }
        } else {
            data = 0;
        }

        block.setData(data);
    }

    private static void rotateChest(Block block) {
        Material type = block.getType();

        if(type.equals(block.getRelative(BlockFace.NORTH).getType())) {

        } else if(type.equals(block.getRelative(BlockFace.EAST).getType())) {

        } else if(type.equals(block.getRelative(BlockFace.SOUTH).getType())) {

        } else if(type.equals(block.getRelative(BlockFace.WEST).getType())) {

        } else {
            incrementBlockData(block, 1, 2, 5);
        }
    }

    private static void rotateRedstoneRail(Block block) {
        byte data = block.getData();
        byte direction = (byte)(data & 7);

        if(++direction > 5) {
            direction = 0;
        }

        if(data >= 8) {
            direction += 8;
        }

        block.setData(direction);
    }
}
