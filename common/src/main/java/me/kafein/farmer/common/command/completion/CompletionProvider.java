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

package me.kafein.farmer.common.command.completion;

import com.google.common.collect.ImmutableList;
import me.kafein.farmer.common.command.model.Sender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CompletionProvider {

    private final List<Completion> completions = new ArrayList<>();

    protected CompletionProvider() {
        put(new Completion("@levels") {
            @Override
            public List<String> getCompletions(@NotNull Sender sender) {
                return ImmutableList.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            }
        });
    }

    public abstract void initialize();

    public List<Completion> findAll() {
        return completions;
    }

    public Optional<Completion> find(String name) {
        return completions.stream()
                .filter(completion -> completion.getName().equals(name))
                .findFirst();
    }

    public void put(Completion completion) {
        completions.add(completion);
    }
}
