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

package me.kafein.farmer.bukkit.component;

import com.cryptomorin.xseries.XMaterial;
import me.kafein.farmer.api.component.MaterialComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BukkitMaterialComponentFactory implements MaterialComponent {

    public static final Serializer SERIALIZER = new Serializer();

    private final Material material;

    public BukkitMaterialComponentFactory(@NotNull Material material) {
        this.material = material;
    }

    @NotNull
    public Material getMaterial() {
        return material;
    }

    @Override
    public String getName() {
        return material.name();
    }

    public static class Serializer implements me.kafein.farmer.api.serializer.Serializer<BukkitMaterialComponentFactory, XMaterial> {

        @NotNull
        public XMaterial serialize(@NotNull BukkitMaterialComponentFactory materialComponent) {
            return XMaterial.matchXMaterial(materialComponent.getMaterial());
        }

        @NotNull
        public BukkitMaterialComponentFactory deserialize(@NotNull MaterialComponent materialComponent) {
            return (BukkitMaterialComponentFactory) materialComponent;
        }

        @NotNull
        public BukkitMaterialComponentFactory deserialize(@NotNull XMaterial material) {
            Objects.requireNonNull(material.parseMaterial(), "Material cannot be null");

            return new BukkitMaterialComponentFactory(material.parseMaterial());
        }

        @NotNull
        public BukkitMaterialComponentFactory deserialize(@NotNull Material material) {
            return new BukkitMaterialComponentFactory(material);
        }

        @NotNull
        public BukkitMaterialComponentFactory deserialize(@NotNull ItemStack itemStack) {
            return deserialize(XMaterial.matchXMaterial(itemStack));
        }

    }

}
