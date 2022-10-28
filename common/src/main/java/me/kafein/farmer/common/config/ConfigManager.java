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

package me.kafein.farmer.common.config;

import com.google.common.reflect.TypeToken;
import me.kafein.farmer.common.config.key.ConfigKey;
import me.kafein.farmer.common.config.key.ConfigKeys;
import me.kafein.farmer.common.config.language.Language;
import me.kafein.farmer.common.config.node.NodeLoader;
import me.kafein.farmer.common.util.Logger;
import ninja.leaping.configurate.ConfigurationNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings({"unchecked"})
public class ConfigManager {

    private static final Map<String, Config> configs = new HashMap<>();

    /**
     * Loads the default configs.
     * If the configs already exist, they will be reloaded.
     */
    public static void initialize(String path) {
        configs.clear();

        ClassLoader classLoader = ConfigManager.class.getClassLoader();
        ConfigManager.createConfig("settings",
                path + "/settings.yml",
                classLoader.getResourceAsStream("settings.yml")).ifPresent(config -> {
            put(ConfigType.SETTINGS, config);
            ConfigManager.putNodesToClass(config, ConfigType.SETTINGS.getClazz(), true);
        });

        String selectedLanguage = ConfigKeys.Settings.LANGUAGE.getValue();
        for (Language language : Language.values()) {
            String languageName = language.name().toLowerCase(Locale.ROOT);
            ConfigManager.createConfig("language",
                    path + "/language/language_" + languageName + ".yml",
                    classLoader.getResourceAsStream("language/language_" + languageName + ".yml")).ifPresent(config -> {
                if (languageName.equalsIgnoreCase(selectedLanguage)) {
                    put(ConfigType.LANGUAGE, config);
                    ConfigManager.putNodesToClass(config, ConfigType.LANGUAGE.getClazz(), true);
                }
            });
        }

        Logger.INFO.log("Configs loaded. Found " + configs.size() + " configs.");
    }

    /**
     * Put config's nodes into a class's fields
     *
     * @param config config to put nodes into fields
     * @param clazz  class to put nodes into fields
     * @param prefix prefix to put nodes into fields
     * @param <V>    type of the ConfigKey's value
     */
    public static <V> void putNodesToClass(Config config, Class<?> clazz, boolean prefix) {
        try {
            for (Field field : clazz.getDeclaredFields()) {
                ConfigKey<V> configKey = (ConfigKey<V>) field.get(null);

                ConfigurationNode node = config.getNode();
                if (prefix) node = node.getNode(config.getName().toLowerCase(Locale.ROOT));
                for (String path : configKey.getPath()) node = node.getNode(path);

                Class<?> valueClass = configKey.getValue().getClass();
                V value = (V) (Collection.class.isAssignableFrom(valueClass)
                        ? node.getValue()
                        : node.getValue(TypeToken.of(valueClass)));
                if (value != null) configKey.setValue(value);
                else {
                    Logger.WARNING.log(config.getName() + " config doesn't have a value for " + Arrays.asList(configKey.getPath()));
                }
            }
        } catch (Exception e) {
            Logger.WARNING.log("Failed to initialize to class " + clazz.getName());
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a config from a path and input stream
     *
     * @param name        the name of the config
     * @param path        the path of the config
     * @param inputStream the input stream of the config
     * @return the config
     */
    public static Optional<Config> createConfig(String name, String path, InputStream inputStream) {
        try {
            return Optional.of(new Config(name, path, NodeLoader.load(path, inputStream)));
        } catch (IOException e) {
            Logger.WARNING.log("Failed to load config " + path);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a config from a path and input stream
     *
     * @param name the name of the config
     * @param path the path of the config
     * @return the config
     */
    public static Optional<Config> createConfig(String name, String path) {
        try {
            return Optional.of(new Config(name, path, NodeLoader.load(path)));
        } catch (IOException e) {
            Logger.WARNING.log("Failed to load config " + path);
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a config from a config type
     *
     * @param name the name of the config
     * @return the config
     */
    public static Optional<Config> find(String name) {
        return Optional.ofNullable(configs.get(name.toLowerCase(Locale.ROOT)));
    }

    /**
     * Get a config from a config type
     *
     * @param configType the config type
     * @return the config
     */
    public static Optional<Config> find(ConfigType configType) {
        return find(configType.name());
    }

    /**
     * Add a config to the map
     *
     * @param name   the name of the config
     * @param config the config
     */
    public static void put(String name, Config config) {
        configs.put(name.toLowerCase(Locale.ROOT), config);
    }

    /**
     * Add a config to the map
     *
     * @param configType the config type
     * @param config     the config
     */
    public static void put(ConfigType configType, Config config) {
        configs.put(configType.name().toLowerCase(Locale.ROOT), config);
    }

    /**
     * Remove a config from the map
     *
     * @param name the name of the config
     */
    public static void remove(String name) {
        configs.remove(name.toLowerCase(Locale.ROOT));
    }

}
