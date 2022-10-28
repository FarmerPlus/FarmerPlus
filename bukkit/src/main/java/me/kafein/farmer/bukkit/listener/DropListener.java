/*
 * MIT License
 *
 * Copyright (c) 2022 FarmerPlus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.kafein.farmer.bukkit.listener;

import me.kafein.farmer.api.component.LocationComponent;
import me.kafein.farmer.bukkit.component.BukkitMaterialComponentFactory;
import me.kafein.farmer.bukkit.component.BukkitLocationComponentSerializer;
import me.kafein.farmer.common.plugin.FarmerPlugin;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DropListener implements Listener {

    private final FarmerPlugin plugin;

    public DropListener(@NotNull FarmerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(ItemSpawnEvent event) {
        Item item = event.getEntity();
        if (item.getPickupDelay() == 40) return;

        LocationComponent locationComponent = BukkitLocationComponentSerializer.deserialize(item.getLocation());
        plugin.getRegionCompatibility().matchFarmer(plugin, locationComponent).ifPresent(farmer -> {
            ItemStack itemStack = item.getItemStack();

            BukkitMaterialComponentFactory materialComponent = BukkitMaterialComponentFactory.SERIALIZER.deserialize(itemStack);
            if (farmer.hasMaterial(materialComponent)) {
                farmer.incrementMaterialAmount(materialComponent, (long) itemStack.getAmount());
                event.setCancelled(true);
            }
        });
    }

}
