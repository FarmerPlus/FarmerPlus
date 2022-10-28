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

package me.kafein.farmer.compatibility.bentobox;

import com.google.common.collect.ImmutableList;
import me.kafein.farmer.api.component.LocationComponent;
import me.kafein.farmer.common.compatibility.RegionCompatibility;
import me.kafein.farmer.api.model.Farmer;
import me.kafein.farmer.common.plugin.FarmerPlugin;
import me.kafein.farmer.compatibility.bentobox.listener.IslandListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BentoBoxRegionCompatibility implements RegionCompatibility {

    private static final List<String> ISLAND_WORLDS = ImmutableList.of(
            "bskyblock_world",
            "acidisland_world",
            "oneblock_world",
            "caveblock-world",
            "boxed_world"
    );

    private final BentoBox bentoBox = BentoBox.getInstance();

    @Override
    public void initialize(@NotNull FarmerPlugin farmerPlugin) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("FarmerPlus");
        if (plugin == null) return;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new IslandListener(farmerPlugin), plugin);
    }

    @Override
    public Optional<Farmer> matchFarmer(@NotNull FarmerPlugin farmerPlugin, @NotNull LocationComponent locationComponent) {
        String worldName = locationComponent.getWorldName();
        if (!ISLAND_WORLDS.contains(worldName)) return Optional.empty();

        Location location = new Location(bentoBox.getServer().getWorld(worldName),
                locationComponent.getX(),
                locationComponent.getY(),
                locationComponent.getZ()
        );

        Optional<Island> island = bentoBox.getIslands().getIslandAt(location);
        if (!island.isPresent()) return Optional.empty();

        UUID uuid = UUID.fromString(island.get().getUniqueId());
        return farmerPlugin.getFarmerManager().find(uuid);
    }

}
