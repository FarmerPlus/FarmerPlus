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
import me.kafein.farmer.common.command.completion.Completion;
import me.kafein.farmer.common.command.completion.CompletionProvider;
import me.kafein.farmer.common.commands.FarmerCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CommandManager {

    private final List<ParentCommand> commands = new ArrayList<>();

    private final CommandConverter converter = new CommandConverter();

    @NotNull
    private final CompletionProvider completionProvider;

    protected CommandManager(@NotNull CompletionProvider completionProvider) {
        this.completionProvider = completionProvider;
        completionProvider.initialize();
    }

    public void initialize() {
        registerCommand(new FarmerCommand());
    }

    public Optional<ParentCommand> findCommand(@NotNull String alias) {
        return commands.stream()
                .filter(command -> command.containsAlias(alias))
                .findFirst();
    }

    public Optional<Completion> findCompletion(@NotNull String completion) {
        return completionProvider.find(completion);
    }

    public void registerCommand(BaseCommand baseCommand) {
        try {
            final ParentCommand command = converter.convert(baseCommand);

            for (Method method : baseCommand.getClass().getDeclaredMethods()) {
                method.setAccessible(true);

                final ChildCommand childCommand = converter.convert(baseCommand, method);
                if (childCommand != null) {
                    command.putChild(childCommand);
                }
            }

            registerCommand(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerCommand(@NotNull ParentCommand command) {
        initializeRegisteredCommand(command);

        commands.add(command);
    }

    public abstract void initializeRegisteredCommand(ParentCommand command);

    @NotNull
    public CompletionProvider getCompletionProvider() {
        return completionProvider;
    }
}
