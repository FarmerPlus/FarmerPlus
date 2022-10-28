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

package me.kafein.farmer.common.config.key;

import com.google.common.collect.ImmutableList;
import me.kafein.farmer.common.config.ConfigType;

import java.util.List;

public final class ConfigKeys {
    private ConfigKeys() {
    }

    public static final class Settings {
        private Settings() {
        }

        public static final ConfigKey<String> LANGUAGE = new ConfigKey<>("en", ConfigType.SETTINGS, "language");

        public static final ConfigKey<String> ADMIN_PERM = new ConfigKey<>("token.admin", ConfigType.SETTINGS, "admin-perm");

    }

    public static final class Language {
        private Language() {
        }

        public static final ConfigKey<String> PREFIX = new ConfigKey<>("&6DreamTokens &8➜", ConfigType.LANGUAGE, "prefix");

        public static final ConfigKey<String> RELOADED = new ConfigKey<>("&aSuccessfully reloaded.", ConfigType.LANGUAGE, "reloaded");

        public static final ConfigKey<String> NO_PERMISSION = new ConfigKey<>("&cYou can't have permission to use this command!", ConfigType.LANGUAGE, "no-perm");
        public static final ConfigKey<String> NO_PLAYER = new ConfigKey<>("&cYou must be a player to use this command!", ConfigType.LANGUAGE, "no-player");
        public static final ConfigKey<String> PLAYER_NOT_FOUND = new ConfigKey<>("&cPlayer not found!", ConfigType.LANGUAGE, "player-not-found");

        public static final ConfigKey<List<String>> HELP_MESSAGE = new ConfigKey<>(ImmutableList.of(), ConfigType.LANGUAGE, "help-message");
        public static final ConfigKey<List<String>> HELP_ADMIN_MESSAGE = new ConfigKey<>(ImmutableList.of(), ConfigType.LANGUAGE, "help-admin-message");

        public static final ConfigKey<List<String>> AUTO_SAVE_MESSAGE = new ConfigKey<>(ImmutableList.of(), ConfigType.LANGUAGE, "auto-save-message");

        public static final ConfigKey<String> SECONDS_FORMAT = new ConfigKey<>("s", ConfigType.LANGUAGE, "time-format", "seconds");
        public static final ConfigKey<String> MINUTES_FORMAT = new ConfigKey<>("m", ConfigType.LANGUAGE, "time-format", "minutes");
        public static final ConfigKey<String> HOURS_FORMAT = new ConfigKey<>("h", ConfigType.LANGUAGE, "time-format", "hours");
        public static final ConfigKey<String> DAYS_FORMAT = new ConfigKey<>("d", ConfigType.LANGUAGE, "time-format", "days");

    }
}