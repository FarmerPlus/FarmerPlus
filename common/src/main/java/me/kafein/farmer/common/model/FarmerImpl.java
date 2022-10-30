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

package me.kafein.farmer.common.model;

import me.kafein.farmer.api.component.LocationComponent;
import me.kafein.farmer.api.component.MaterialComponent;
import me.kafein.farmer.api.model.Farmer;
import me.kafein.farmer.api.model.member.Member;
import me.kafein.farmer.api.shape.Cuboid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FarmerImpl implements Farmer {

    private final Map<MaterialComponent, Long> materials = new HashMap<>();
    private final List<Member> members = new ArrayList<>();

    private LocationComponent farmerLocation;
    private LocationComponent regionLocation;

    private Cuboid cuboid;
    private String name;

    private final UUID uuid;

    public FarmerImpl(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public @NotNull UUID getUUID() {
        return uuid;
    }

    @Override
    @Nullable
    public String getName() {
        return name;
    }

    @Override
    @Nullable
    public Cuboid getCuboid() {
        return cuboid;
    }

    @Override
    @Nullable
    public LocationComponent getRegionLocation() {
        return regionLocation;
    }

    @Override
    @Nullable
    public LocationComponent getFarmerLocation() {
        return farmerLocation;
    }

    @Override
    public OptionalLong findMaterialAmount(@NotNull MaterialComponent materialComponent) {
        return OptionalLong.of(materials.get(materialComponent));
    }

    @Override
    public boolean hasMaterial(@NotNull MaterialComponent materialComponent) {
        return materials.containsKey(materialComponent);
    }

    @Override
    public void putMaterial(@NotNull MaterialComponent materialComponent) {
        materials.put(materialComponent, 0L);
    }

    @Override
    public void increaseMaterialAmount(@NotNull MaterialComponent materialComponent, long amount) {
        materials.put(materialComponent, materials.get(materialComponent) + amount);
    }

    @Override
    public void decreaseMaterialAmount(@NotNull MaterialComponent materialComponent, long amount) {
        materials.put(materialComponent, materials.get(materialComponent) - amount);
    }

    @Override
    public void setMaterialAmount(@NotNull MaterialComponent materialComponent, long amount) {
        materials.put(materialComponent, amount);
    }

    @Override
    public void removeMaterial(@NotNull MaterialComponent materialComponent) {
        materials.remove(materialComponent);
    }

    @Override
    public Optional<Member> findMemberByUUID(@NotNull UUID uuid) {
        return members.stream()
                .filter(member -> member.getUUID().equals(uuid))
                .findFirst();
    }

    @Override
    public Optional<Member> findMemberByName(@NotNull String name) {
        return members.stream()
                .filter(member -> member.getName().equals(name))
                .findFirst();
    }

    @Override
    public @NotNull Member createMember(@NotNull UUID uuid, @NotNull String name) {
        return new MemberImpl(uuid, name);
    }

    @Override
    public void putMember(@NotNull Member member) {
        members.add(member);
    }

    @Override
    public void removeMember(@NotNull Member member) {
        members.remove(member);
    }

    @Override
    public void setRegionLocation(@NotNull LocationComponent regionLocation) {
        this.regionLocation = regionLocation;
    }

    @Override
    public boolean hasRegionLocation() {
        return regionLocation != null;
    }

    @Override
    public void setFarmerLocation(@NotNull LocationComponent farmerLocation) {
        this.farmerLocation = farmerLocation;
    }

    @Override
    public boolean hasFarmerLocation() {
        return farmerLocation != null;
    }

    @Override
    public void setCuboid(@NotNull Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    @Override
    public boolean hasCuboid() {
        return cuboid != null;
    }

    @Override
    public void setName(@NotNull String name) {
        this.name = name;
    }
}