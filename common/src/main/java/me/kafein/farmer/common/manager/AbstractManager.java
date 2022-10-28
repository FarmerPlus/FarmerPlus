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

import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class AbstractManager<K, V> {

    protected final Map<K, V> map = new HashMap<>();

    public Collection<V> findAll() {
        return map.values();
    }

    public Optional<V> find(@NotNull K key) {
        return Optional.ofNullable(map.get(key));
    }

    public V get(@NotNull K key) {
        return find(key)
                .orElseThrow(() -> new IllegalArgumentException("Value not found"));
    }

    public void put(@NotNull K key, @NotNull V value) {
        map.put(key, value);
    }

    public void remove(@NotNull K key) {
        map.remove(key);
    }
}