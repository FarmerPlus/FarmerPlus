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

package me.kafein.farmer.common.commands;

import me.kafein.farmer.common.command.BaseCommand;
import me.kafein.farmer.common.command.annotation.*;
import me.kafein.farmer.common.command.model.Sender;
import org.jetbrains.annotations.NotNull;

@CommandAlias("farmerplus|farmerplugin|farmer|çiftçi|ciftci")
@CommandPermission("farmerplus.admin")
@CommandUsage("/farmer")
public class FarmerCommand implements BaseCommand {

    @Override
    public void execute(@NotNull Sender sender, @NotNull String[] args) {
        sender.sendMessage("test");
    }

    @Subcommand("reload")
    @CommandPermission("farmerplus.admin")
    @CommandUsage("/farmer reload")
    public void reloadCommand(@NotNull Sender sender, @NotNull String[] args) {
        sender.sendMessage("§aFarmerPlus Yeniden Başlatılıyor...");
    }

    @Subcommand("give")
    @CommandPermission("farmerplus.admin")
    @CommandUsage("/farmer give <player> <level>")
    @CommandCompletion("1-@players|2-@levels")
    public void giveCommand(@NotNull Sender sender, @NotNull String[] args) {

    }

    @Subcommand("giveAll")
    @CommandPermission("farmerplus.admin")
    @CommandUsage("/farmer giveAll <level>")
    @CommandCompletion("1-@levels")
    public void giveAllCommand(@NotNull Sender sender, @NotNull String[] args) {

    }

}
