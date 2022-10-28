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

package me.kafein.farmer.common.command.abstraction;

import me.kafein.farmer.common.command.BaseCommand;
import me.kafein.farmer.common.command.completion.RegisteredCompletion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ParentCommand extends AbstractCommand {

    private final List<ChildCommand> child = new ArrayList<>();

    public ParentCommand(@NotNull BaseCommand baseCommand,
                         @NotNull Method executor,
                         @NotNull String[] aliases,
                         @Nullable RegisteredCompletion[] completions,
                         @Nullable String description,
                         @Nullable String permission,
                         @Nullable String usage) {
        super(baseCommand, executor, aliases, completions, description, permission, usage);
    }

    @Override
    public boolean isParent() {
        return true;
    }

    @NotNull
    public List<ChildCommand> findAllChild() {
        return child;
    }

    @NotNull
    public List<String> findAllChildAliases() {
        final List<String> aliases = new ArrayList<>();

        for (ChildCommand childCommand : child) {
            aliases.addAll(Arrays.asList(childCommand.getAliases()));
        }

        return aliases;
    }

    public Optional<ChildCommand> findChild(String alias) {
        return child.stream()
                .filter(c -> c.containsAlias(alias))
                .findFirst();
    }

    public void putChild(ChildCommand childCommand) {
        child.add(childCommand);
    }
}