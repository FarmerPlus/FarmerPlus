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

package me.kafein.farmer.common.plugin;

import me.kafein.farmer.api.manager.FarmerManager;
import me.kafein.farmer.common.command.CommandManager;
import me.kafein.farmer.common.compatibility.RegionCompatibility;
import me.kafein.farmer.common.config.ConfigManager;
import me.kafein.farmer.common.manager.FarmerManagerImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFarmerPlugin implements FarmerPlugin {

    private FarmerManager farmerManager;
    private CommandManager commandManager;
    private RegionCompatibility regionCompatibility;

    @Override
    public void load() {
        ConfigManager.initialize(getDataFolder());

    }

    @Override
    public void enable() {
        farmerManager = new FarmerManagerImpl(this);
        commandManager = createCommandManager();

        final RegionCompatibility createdRegionCompatibility = createRegionCompatibility();
        if (createdRegionCompatibility != null) {
            createdRegionCompatibility.initialize(this);
            this.regionCompatibility = createdRegionCompatibility;
        }

        registerListeners();
    }

    @Override
    public void disable() {
        farmerManager.saveAll();

    }

    protected abstract void registerListeners();

    protected abstract String getDataFolder();

    @NotNull
    protected abstract CommandManager createCommandManager();

    @Nullable
    protected abstract RegionCompatibility createRegionCompatibility();

    @Override
    public @Nullable RegionCompatibility getRegionCompatibility() {
        return regionCompatibility;
    }

    @Override
    public @NotNull FarmerManager getFarmerManager() {
        return farmerManager;
    }

    @Override
    public @NotNull CommandManager getCommandManager() {
        return commandManager;
    }
}
