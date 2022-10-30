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

package me.kafein.farmer.bukkit;

import com.google.common.collect.ImmutableList;
import me.kafein.farmer.bukkit.command.BukkitCommandManager;
import me.kafein.farmer.bukkit.command.BukkitCompletionProvider;
import me.kafein.farmer.bukkit.compatibility.BukkitRegionCompatibilityFactory;
import me.kafein.farmer.bukkit.listener.DropListener;
import me.kafein.farmer.bukkit.listener.registrar.ListenerRegistrar;
import me.kafein.farmer.bukkit.loader.BukkitPluginLoader;
import me.kafein.farmer.common.command.CommandManager;
import me.kafein.farmer.common.command.completion.CompletionProvider;
import me.kafein.farmer.common.plugin.AbstractFarmerPlugin;
import me.kafein.farmer.common.compatibility.RegionCompatibility;
import me.kafein.farmer.common.compatibility.RegionCompatibilityFactory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BukkitFarmerPlugin extends AbstractFarmerPlugin {

    private static final String DATA_FOLDER = "plugins/FarmerPlus/";

    private static final List<Class<?>> LISTENERS = ImmutableList.of(
            DropListener.class
    );

    @Override
    protected void registerListeners() {
        ListenerRegistrar.register(this, LISTENERS);
    }

    @Override
    protected String getDataFolder() {
        return DATA_FOLDER;
    }

    @Override
    protected @NotNull CommandManager createCommandManager() {
        final CompletionProvider completionProvider = new BukkitCompletionProvider();

        final CommandManager commandManager = new BukkitCommandManager(getRoot(), completionProvider);
        commandManager.initialize();

        return commandManager;
    }

    @Override
    protected @Nullable RegionCompatibility createRegionCompatibility() {
        final RegionCompatibilityFactory factory = new BukkitRegionCompatibilityFactory();

        return factory.create(this);
    }

    public Plugin getRoot() {
        return BukkitPluginLoader.getPlugin(BukkitPluginLoader.class);
    }

}
