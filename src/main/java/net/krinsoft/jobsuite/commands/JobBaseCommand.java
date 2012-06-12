package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.JobCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobBaseCommand extends JobCommand {

    public JobBaseCommand(JobCore instance) {
        super(instance);
        setName("JobSuite - Help");
        setCommandUsage("/job");
        addCommandExample(ChatColor.GREEN + "/job " + ChatColor.RED + "jobs    " + ChatColor.WHITE + "-- Show job-related commands");
        addCommandExample(ChatColor.GREEN + "/job " + ChatColor.RED + "basic   " + ChatColor.WHITE + "-- Show basic commands");
        addCommandExample(ChatColor.GREEN + "/job " + ChatColor.RED + "admin   " + ChatColor.WHITE + "-- Show admin commands");
        setArgRange(1, 1);
        addKey("jobsuite");
        addKey("job");
        addKey("js");
        setPermission("jobsuite.help", "Allows the user to view JobSuite's commands.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if (args.get(0).equalsIgnoreCase("jobs")) {
            sender.sendMessage(ChatColor.DARK_GREEN + "=== " + ChatColor.AQUA + "Job-related Commands" + ChatColor.DARK_GREEN + " ===");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "make    " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "desc    " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "reward  " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "additem " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "remitem " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "post    " + ChatColor.RED + "?");
        } else if (args.get(0).equalsIgnoreCase("basic")) {
            sender.sendMessage(ChatColor.DARK_GREEN + "=== " + ChatColor.AQUA + "Basic Commands" + ChatColor.DARK_GREEN + " ===");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "accept  " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "cancel  " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "info    " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "list    " + ChatColor.RED + "?");
        } else if (args.get(0).equalsIgnoreCase("admin") && hasPermission(sender, "jobsuite.help.admin")) {
            sender.sendMessage(ChatColor.DARK_GREEN + "=== " + ChatColor.AQUA + "Admin Commands" + ChatColor.DARK_GREEN + " ===");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "reload  " + ChatColor.RED + "?");
            sender.sendMessage(ChatColor.GREEN + "/job " + ChatColor.GOLD + "flush   " + ChatColor.RED + "?");
        } else {
            showHelp(sender);
        }
    }

}
