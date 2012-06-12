package net.krinsoft.jobsuite;

import com.pneumaticraft.commandhandler.CommandHandler;
import net.krinsoft.jobsuite.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author krinsdeath
 */
public class JobCore extends JavaPlugin {
    private JobManager manager;
    private CommandHandler commands;

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        if (!new File(getDataFolder(), "config.yml").exists()) {
            getConfig().setDefaults(YamlConfiguration.loadConfiguration(this.getClass().getResourceAsStream("/config.yml")));
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        initializeManager();
        initializeCommands();
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                manager.persist();
            }
        }, 3600L, 3600L);
        getLogger().info("JobSuite initialized! (" + (System.currentTimeMillis() - time) + "ms)");
    }

    @Override
    public void onDisable() {
        long time = System.currentTimeMillis();
        getServer().getScheduler().cancelTasks(this);
        manager.close();
        getLogger().info("JobSuite disabled successfully. (" + (System.currentTimeMillis() - time) + "ms)");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> allArgs = new ArrayList<String>(Arrays.asList(args));
        allArgs.add(0, label);
        return commands.locateAndRunCommand(sender, allArgs);
    }

    private void initializeManager() {
        manager = new JobManager(this);
        manager.load();
    }

    private void initializeCommands() {
        PermissionHandler handler = new PermissionHandler();
        commands = new CommandHandler(this, handler);
        // base help command
        commands.registerCommand(new JobBaseCommand(this));

        // JOB CREATION
        commands.registerCommand(new JobMakeCommand(this));
        commands.registerCommand(new JobDescriptionCommand(this));
        commands.registerCommand(new JobRewardCommand(this));
        commands.registerCommand(new JobAddItemCommand(this));
        commands.registerCommand(new JobRemoveItemCommand(this));
        commands.registerCommand(new JobAddEnchantmentCommand(this));
        commands.registerCommand(new JobRemoveEnchantmentCommand(this));
        commands.registerCommand(new JobListItemCommand(this));
        commands.registerCommand(new JobPostCommand(this));

        // ADMINISTRATIVE
        //commands.registerCommand(new JobAcceptCommand(this));
        //commands.registerCommand(new JobCancelCommand(this));
        commands.registerCommand(new JobListCommand(this));
        commands.registerCommand(new JobInfoCommand(this));

        // BASIC FUNCTIONS
        //commands.registerCommand(new JobReloadCommand(this));
        //commands.registerCommand(new JobFlushCommand(this));
    }

    public JobManager getJobManager() {
        return manager;
    }

}
