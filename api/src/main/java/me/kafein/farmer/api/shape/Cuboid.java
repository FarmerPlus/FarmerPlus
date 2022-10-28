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

package me.kafein.farmer.api.shape;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public class Cuboid {

    public static final Serializer SERIALIZER = new Serializer();

    private final int minX, maxX;
    private final int minZ, maxZ;

    public Cuboid(int minX, int maxX, int minZ, int maxZ) {
        Preconditions.checkArgument(minX <= maxX, "minX must be less than or equal to maxX");
        Preconditions.checkArgument(minZ <= maxZ, "minZ must be less than or equal to maxZ");

        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public static Cuboid of(int minX, int maxX, int minZ, int maxZ) {
        return new Cuboid(minX, maxX, minZ, maxZ);
    }

    public boolean isInCuboid(int x, int z) {
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public static final class Serializer implements me.kafein.farmer.api.serializer.Serializer<Cuboid, String> {

        @NotNull
        public String serialize(@NotNull Cuboid cuboid) {
            return String.format("%d;%d;%d;%d", cuboid.minX, cuboid.maxX, cuboid.minZ, cuboid.maxZ);
        }

        @NotNull
        public Cuboid deserialize(@NotNull String serialized) {
            String[] split = serialized.split(";");

            return new Cuboid(Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]),
                    Integer.parseInt(split[2]),
                    Integer.parseInt(split[3])
            );
        }

    }

}
