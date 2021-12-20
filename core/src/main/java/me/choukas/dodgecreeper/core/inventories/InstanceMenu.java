package me.choukas.dodgecreeper.core.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.choukas.dodgecreeper.api.server.ServerManager;
import me.choukas.dodgecreeper.core.Messages;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

public class InstanceMenu {

    private static final String MENU_ID = "instance-menu";

    private final InventoryManager inventoryManager;
    private final Provider provider;

    @Inject
    public InstanceMenu(InventoryManager inventoryManager, Provider provider) {
        this.inventoryManager = inventoryManager;
        this.provider = provider;
    }

    public SmartInventory asSmartInventory() {
        // Note : Add the player as argument to change the menu's title according to its language
        return SmartInventory.builder()
                .manager(this.inventoryManager)
                .id(MENU_ID)
                .title(
                        BukkitComponentSerializer.legacy().serialize(
                                Component.translatable(Messages.INSTANCE_MENU_TITLE)
                        )
                )
                .provider(this.provider)
                .build();
    }

    public static class Provider implements InventoryProvider {

        private final ServerManager serverManager;

        @Inject
        public Provider(ServerManager serverManager) {
            this.serverManager = serverManager;
        }

        @Override
        public void init(Player player, InventoryContents contents) {

        }

        @Override
        public void update(Player player, InventoryContents contents) {
            this.serverManager.getServers().whenComplete((result, error) ->
                    result.forEach(server ->
                            contents.add(
                                    ClickableItem.of(
                                            new ItemStack(Material.ACACIA_DOOR), // TODO Add name
                                            (event) ->
                                                    this.serverManager.connect(player, server)
                                    )
                            )
                    ));
        }
    }
}
