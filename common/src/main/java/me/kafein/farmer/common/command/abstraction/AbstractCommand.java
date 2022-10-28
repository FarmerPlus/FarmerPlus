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
import me.kafein.farmer.common.command.model.Sender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Optional;

public abstract class AbstractCommand {

    @NotNull
    private final BaseCommand baseCommand;

    @NotNull
    private final Method executor;

    @NotNull
    private final String[] aliases;

    @Nullable
    private final RegisteredCompletion[] completions;

    @Nullable
    private final String description;

    @Nullable
    private final String permission;

    @Nullable
    private final String usage;

    protected AbstractCommand(@NotNull BaseCommand baseCommand,
                              @NotNull Method executor,
                              @NotNull String[] aliases,
                              @Nullable RegisteredCompletion[] completions,
                              @Nullable String description,
                              @Nullable String permission,
                              @Nullable String usage) {
        this.baseCommand = baseCommand;
        this.executor = executor;
        this.aliases = aliases;
        this.completions = completions;
        this.description = description;
        this.permission = permission;
        this.usage = usage;
    }

    public abstract boolean isParent();

    public void execute(@NotNull Sender sender, @NotNull String[] args) {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage("You don't have permission to execute this command.");
            return;
        }

        try {
            executor.invoke(baseCommand, sender, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public String[] getAliases() {
        return aliases;
    }

    public boolean containsAlias(String alias) {
        for (String s : aliases) {
            if (s.equalsIgnoreCase(alias)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public RegisteredCompletion[] getCompletions() {
        return completions;
    }

    public Optional<RegisteredCompletion> findCompletion(String[] args) {
        if (completions == null) {
            return Optional.empty();
        }

        for (RegisteredCompletion completion : completions) {
            if (completion.getIndex() == args.length - 1) {
                return Optional.of(completion);
            }
        }

        return Optional.empty();
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getPermission() {
        return permission;
    }

    @Nullable
    public String getUsage() {
        return usage;
    }
}
