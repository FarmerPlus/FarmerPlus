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

package me.kafein.farmer.common.command.misc;

import com.google.common.collect.ImmutableList;
import me.kafein.farmer.common.command.annotation.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.List;

public class CommandAnnotationProcessor {

    private static final List<Class<? extends Annotation>> ANNOTATIONS = ImmutableList.of(
            CommandAlias.class,
            CommandPermission.class,
            CommandDescription.class,
            CommandCompletion.class,
            CommandUsage.class,
            Subcommand.class
    );

    private CommandAnnotationProcessor() {}

    @Nullable
    public static String process(@NotNull Annotation annotation) throws Exception {
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        if (!ANNOTATIONS.contains(annotationType)) return null;

        return (String) annotationType.getMethod("value").invoke(annotation);
    }

}
