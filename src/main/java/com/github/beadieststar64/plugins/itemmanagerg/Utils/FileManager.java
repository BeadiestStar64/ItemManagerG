package com.github.beadieststar64.plugins.itemmanagerg.Utils;

import com.github.beadieststar64.plugins.itemmanagerg.ItemManagerG;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class FileManager {

    private final JavaPlugin plugin;
    private String version;

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize(String fileName) {
        File folder = plugin.getDataFolder();
        if (!new File(folder, fileName).exists()) {
            createFile(folder, fileName);
        }

        try (InputStream stream = Files.newInputStream(new File(folder, fileName).toPath());
             InputStreamReader input = new InputStreamReader(stream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(input)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) == '#') continue;
                else if (line.charAt(0) == '<') {
                    line = line.replaceAll("<-- ", "").replaceAll(" -->", "");
                    String[] configs = line.split(": ");
                    if (configs.length <= 1) {
                        // Error
                        plugin.getLogger().log(Level.WARNING, "There is an error in the configuration item in RequestFiles.txt.");
                        plugin.getLogger().severe("Version confirmation is not available! Stop plugin...");
                        plugin.getServer().getPluginManager().disablePlugin(plugin);
                        return;
                    }
                    switch (configs[0]) {
                        case "version" -> version = configs[1]; // For automatic update
                        case "skip" -> ItemManagerG.addSoftDepends(configs[1]);
                        default -> plugin.getLogger().warning(String.format("%s: %s",
                                "Incorrect setting. This setting is not available.",
                                configs[0] + configs[1]
                        ));
                    }
                } else {
                    String[] split = line.split(", ");
                    if (split.length < 1 || split.length > 2) {
                        plugin.getLogger().warning("Error");
                        return;
                    }
                    if (split.length == 1) {
                        createFile(plugin.getDataFolder(), split[0]);
                    } else {
                        createFile(new File(plugin.getDataFolder(), split[0]), split[1]);
                    }
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe(e.getMessage());
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public void createFile(File folder, String fileName) {
        String filePath = folder + File.separator + fileName;
        File file = new File(fileName);
        if(!folder.exists()) {
            if(folder.mkdir()) {
                plugin.getLogger().info("Folder created: " + folder);
            }else{
                plugin.getLogger().warning(ChatColor.RED + "Folder creation failed: " + folder);
            }
        }
        if(!file.exists()) {
            String resource = File.separator + fileName;
            copyResource(resource, filePath);
        }
    }

    private void copyResource(String resourcePath, String targetPath) {
        try {
            File temp = copyTemp(resourcePath);
            copyFile(temp, new File(targetPath));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private File copyTemp(String resourcePath) throws IOException {
        try(InputStream input = plugin.getClass().getResourceAsStream(resourcePath)) {
            File temp = File.createTempFile("temp", null);
            temp.deleteOnExit();

            try(FileOutputStream output = new FileOutputStream(temp)) {
                byte[] buffer = new byte[1024];
                int byteRead;
                while((byteRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, byteRead);
                }
            }

            input.close();
            return temp;
        }
    }

    private void copyFile(File sourceFile, File targetFile) {
        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
