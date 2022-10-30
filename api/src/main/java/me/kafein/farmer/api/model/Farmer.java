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

package me.kafein.farmer.api.model;

import me.kafein.farmer.api.component.LocationComponent;
import me.kafein.farmer.api.component.MaterialComponent;
import me.kafein.farmer.api.model.member.Member;
import me.kafein.farmer.api.shape.Cuboid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface Farmer {

    @NotNull
    UUID getUUID();

    @Nullable
    String getName();

    void setName(@NotNull String name);

    @Nullable
    Cuboid getCuboid();

    void setCuboid(@NotNull Cuboid cuboid);

    boolean hasCuboid();

    @Nullable
    LocationComponent getRegionLocation();

    void setRegionLocation(@NotNull LocationComponent regionLocation);

    boolean hasRegionLocation();

    @Nullable
    LocationComponent getFarmerLocation();

    void setFarmerLocation(@NotNull LocationComponent farmerLocation);

    boolean hasFarmerLocation();

    OptionalLong findMaterialAmount(@NotNull MaterialComponent materialComponent);

    boolean hasMaterial(@NotNull MaterialComponent materialComponent);

    void putMaterial(@NotNull MaterialComponent materialComponent);

    void increaseMaterialAmount(@NotNull MaterialComponent materialComponent, long amount);

    void decreaseMaterialAmount(@NotNull MaterialComponent materialComponent, long amount);

    void setMaterialAmount(@NotNull MaterialComponent materialComponent, long amount);

    void removeMaterial(@NotNull MaterialComponent materialComponent);

    Optional<Member> findMemberByUUID(@NotNull UUID uuid);

    Optional<Member> findMemberByName(@NotNull String name);

    @NotNull
    Member createMember(@NotNull UUID uuid, @NotNull String name);

    void putMember(@NotNull Member member);

    void removeMember(@NotNull Member member);

}
