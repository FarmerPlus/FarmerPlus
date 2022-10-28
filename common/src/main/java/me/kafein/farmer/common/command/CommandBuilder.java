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

package me.kafein.farmer.common.command;

import me.kafein.farmer.common.command.abstraction.AbstractCommand;
import me.kafein.farmer.common.command.abstraction.ChildCommand;
import me.kafein.farmer.common.command.abstraction.ParentCommand;
import me.kafein.farmer.common.command.annotation.*;
import me.kafein.farmer.common.command.completion.RegisteredCompletion;
import me.kafein.farmer.common.command.misc.CommandAnnotationProcessor;
import me.kafein.farmer.common.command.misc.CommandPatternProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CommandBuilder {

    private Method executor;
    private String[] aliases;
    private RegisteredCompletion[] completions;
    private String description;
    private String permission;
    private String usage;
    private boolean isParent;

    @NotNull
    private final BaseCommand baseCommand;

    public CommandBuilder(@NotNull BaseCommand baseCommand) {
        this.baseCommand = baseCommand;
    }

    public void process(@NotNull Annotation annotation) {
        try {
            final String value = CommandAnnotationProcessor.process(annotation);
            if (value == null) {
                return;
            }

            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(CommandCompletion.class)) {
                completions(CommandPatternProcessor.processCompletion(value));
            } else if (annotationType.equals(CommandAlias.class) || annotationType.equals(Subcommand.class)) {
                aliases(CommandPatternProcessor.processAlias(value));
            } else if (annotationType.equals(CommandDescription.class)) {
                description(value);
            } else if (annotationType.equals(CommandPermission.class)) {
                permission(value);
            } else if (annotationType.equals(CommandUsage.class)) {
                usage(value);
            }
        } catch (Exception ignored) {
        }
    }

    public CommandBuilder type(boolean isParent) {
        this.isParent = isParent;
        return this;
    }

    public CommandBuilder executor(@NotNull Method executor) {
        this.executor = executor;
        return this;
    }

    public CommandBuilder aliases(@NotNull String[] aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandBuilder completions(@Nullable RegisteredCompletion[] completions) {
        this.completions = completions;
        return this;
    }

    public CommandBuilder description(@Nullable String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder permission(@Nullable String permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder usage(@Nullable String usage) {
        this.usage = usage;
        return this;
    }

    public AbstractCommand build() {
        return isParent
                ? new ParentCommand(baseCommand, executor, aliases, completions, description, permission, usage)
                : new ChildCommand(baseCommand, executor, aliases, completions, description, permission, usage);
    }

}
