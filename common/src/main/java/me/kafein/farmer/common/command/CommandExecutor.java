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
import me.kafein.farmer.common.command.completion.RegisteredCompletion;
import me.kafein.farmer.common.command.model.Sender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface CommandExecutor {

    default void execute(@NotNull ParentCommand command, @NotNull Sender sender, @NotNull String label, @NotNull String[] args) {
        if (!command.containsAlias(label)) return;

        if (args.length == 0 || !command.findChild(args[0]).isPresent()) {
            command.execute(sender, args);
        } else {
            final ChildCommand childCommand = command.findChild(args[0]).get();
            childCommand.execute(sender, args);
        }
    }

    @Nullable
    default List<String> tabComplete(@NotNull ParentCommand command, @NotNull CommandManager manager, @NotNull Sender sender, @NotNull String[] args) {
        if (args.length == 1) return command.findAllChildAliases();

        final Optional<ChildCommand> optionalChildCommand = command.findChild(args[0]);
        if (!optionalChildCommand.isPresent()) return null;
        final ChildCommand childCommand = optionalChildCommand.get();

        final Optional<RegisteredCompletion> optionalRegisteredCompletion = childCommand.findCompletion(args);
        if (!optionalRegisteredCompletion.isPresent()) return null;
        final RegisteredCompletion registeredCompletion = optionalRegisteredCompletion.get();

        Optional<Completion> completion = manager.findCompletion(registeredCompletion.getName());
        return completion.map(value -> value.getCompletions(sender)).orElse(null);
    }

}
