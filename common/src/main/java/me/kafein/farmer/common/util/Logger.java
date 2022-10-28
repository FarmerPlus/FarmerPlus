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

package me.kafein.farmer.common.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.logging.Level;

public enum Logger {
    WARNING(Level.WARNING),
    ERROR(Level.SEVERE),
    INFO(Level.INFO);

    private final Level level;

    Logger(Level level) {
        this.level = level;
    }

    private static final DateTimeFormatter DATE_FORMAT_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_FORMAT_2 = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String DATE_STRING = DATE_FORMAT_1.format(LocalDateTime.now());

    private static String dataPath;
    private static BiConsumer<Logger, String> executeConsumer;

    public static void initialize(String dataPath, BiConsumer<Logger, String> consumer) {
        Logger.dataPath = dataPath;
        Logger.executeConsumer = consumer;
    }

    public void log(String message) {
        CompletableFuture.runAsync(() -> {
            File file = new File(dataPath + "/logs/[LOG] " + DATE_STRING + ".txt");
            try {
                String log = "[" + DATE_FORMAT_2.format(LocalDateTime.now()) + " " + this.level.getName() + "] " + message;
                FileUtils.writeStringToFile(file, log + "\n", StandardCharsets.UTF_8, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (executeConsumer != null) executeConsumer.accept(this, message);
        });
    }

    public Level getLevel() {
        return level;
    }
}
