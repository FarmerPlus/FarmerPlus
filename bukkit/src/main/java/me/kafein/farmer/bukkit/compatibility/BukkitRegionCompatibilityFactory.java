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

package me.kafein.farmer.bukkit.compatibility;

import me.kafein.farmer.common.compatibility.RegionCompatibility;
import me.kafein.farmer.common.compatibility.RegionCompatibilityFactory;
import me.kafein.farmer.common.compatibility.RegionCompatibilityType;
import me.kafein.farmer.common.plugin.FarmerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class BukkitRegionCompatibilityFactory implements RegionCompatibilityFactory {

    private static final String COMPATIBILITY_PACKAGE = "me.kafein.farmer.compatibility.";

    @Override
    @Nullable
    public RegionCompatibility create(@NotNull FarmerPlugin farmerPlugin) {
        RegionCompatibilityType type = matchType();
        if (type == null) {
            return null;
        }

        try {
            Class<?> clazz = Class.forName(COMPATIBILITY_PACKAGE + type.getName().toLowerCase(Locale.ROOT) + "." + type.getName() + "RegionCompatibility");

            RegionCompatibility compatibility = clazz.asSubclass(RegionCompatibility.class).newInstance();
            compatibility.initialize(farmerPlugin);

            return compatibility;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create RegionCompatibility for type " + type, e);
        }
    }

    @Override
    @Nullable
    public RegionCompatibilityType matchType() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        for (RegionCompatibilityType compatibilityType : RegionCompatibilityType.values()) {
            String pluginName = compatibilityType.getName();

            if (pluginManager.isPluginEnabled(pluginName)) {
                return compatibilityType;
            }
        }

        return null;
    }

}
