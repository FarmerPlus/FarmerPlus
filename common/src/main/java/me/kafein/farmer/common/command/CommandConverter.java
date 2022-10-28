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

import me.kafein.farmer.common.command.abstraction.ChildCommand;
import me.kafein.farmer.common.command.abstraction.ParentCommand;
import me.kafein.farmer.common.command.annotation.*;
import me.kafein.farmer.common.command.model.Sender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CommandConverter {

    public ParentCommand convert(@NotNull BaseCommand baseCommand) throws Exception {
        final Class<?> clazz = baseCommand.getClass();

        Annotation[] annotations = clazz.getAnnotations();
        if (annotations.length == 0) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " has no annotations!");
        }

        final CommandBuilder builder = new CommandBuilder(baseCommand)
                .executor(clazz.getDeclaredMethod("execute", Sender.class, String[].class))
                .type(true);

        for (Annotation annotation : annotations) {
            builder.process(annotation);
        }

        return (ParentCommand) builder.build();
    }

    @Nullable
    public ChildCommand convert(@NotNull BaseCommand baseCommand, @NotNull Method method) {
        Annotation[] annotations = method.getAnnotations();
        if (annotations.length == 0) {
            return null;
        }

        //is a child command?
        if (method.getAnnotation(Subcommand.class) == null) {
            return null;
        }

        final CommandBuilder builder = new CommandBuilder(baseCommand)
                .executor(method)
                .type(false);

        for (Annotation annotation : annotations) {
            builder.process(annotation);
        }

        return (ChildCommand) builder.build();
    }

}
