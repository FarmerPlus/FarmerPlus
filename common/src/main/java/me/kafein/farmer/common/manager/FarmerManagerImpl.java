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

package me.kafein.farmer.common.manager;

import me.kafein.farmer.api.component.LocationComponent;
import me.kafein.farmer.api.manager.FarmerManager;
import me.kafein.farmer.api.model.Farmer;
import me.kafein.farmer.common.model.FarmerImpl;
import me.kafein.farmer.common.plugin.AbstractFarmerPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class FarmerManagerImpl extends AbstractManager<UUID, Farmer> implements FarmerManager {

    private final AbstractFarmerPlugin plugin;

    public FarmerManagerImpl(@NotNull AbstractFarmerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load(@NotNull UUID uuid) {

    }

    @Override
    public void save(@NotNull UUID uuid) {

    }

    @Override
    public void saveAll() {

    }

    @Override
    public @NotNull Farmer createFarmer(@NotNull UUID uuid) {
        return new FarmerImpl(uuid);
    }

    @Override
    public @NotNull Farmer createAndPutFarmer(@NotNull UUID uuid) {
        final Farmer farmer = createFarmer(uuid);

        return put(uuid, farmer);
    }

    @Override
    public Optional<Farmer> findByLocation(@NotNull LocationComponent locationComponent) {
        Optional<Farmer> optionalFarmer = findAll().stream()
                .filter(Farmer::hasCuboid)
                .filter(farmer -> farmer.getFarmerLocation().getWorldName().equals(locationComponent.getWorldName()))
                .filter(farmer -> farmer.getCuboid().isInCuboid(locationComponent))
                .findFirst();

        return optionalFarmer.isPresent()
                ? optionalFarmer
                : plugin.getRegionCompatibility().matchFarmer(plugin, locationComponent);
    }

    @Override
    public Optional<Farmer> findByFarmerLocation(@NotNull LocationComponent locationComponent) {
        return findAll().stream()
                .filter(Farmer::hasRegionLocation)
                .filter(farmer -> farmer.getFarmerLocation().equals(locationComponent))
                .findFirst();
    }

    @Override
    public Optional<Farmer> findByRegionLocation(@NotNull LocationComponent locationComponent) {
        return findAll().stream()
                .filter(Farmer::hasRegionLocation)
                .filter(farmer -> farmer.getRegionLocation().equals(locationComponent))
                .findFirst();
    }

}
