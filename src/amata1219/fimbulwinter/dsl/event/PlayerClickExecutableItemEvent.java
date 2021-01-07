package amata1219.fimbulwinter.dsl.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerClickExecutableItemEvent {

    public final Player player;
    public final EquipmentSlot handType;
    public final Block blockClicked;
    public final BlockFace blockFace;

    public PlayerClickExecutableItemEvent(Player player, EquipmentSlot handType, Block blockClicked, BlockFace blockFace) {
        this.player = player;
        this.handType = handType;
        this.blockClicked = blockClicked;
        this.blockFace = blockFace;
    }

}
