package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.JobCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobMakeCommand extends JobCommand {

    public JobMakeCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Make");
        setCommandUsage("/job make [name]");
        addCommandExample("/job make demo");
        setArgRange(1, 20);
        addKey("jobsuite make");
        addKey("job make");
        addKey("js make");
        setPermission("jobsuite.make", "Creates a job session.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        StringBuilder name = new StringBuilder();
        for (String arg : args) {
            name.append(arg).append(" ");
        }
        if (manager.addQueuedJob(sender.getName(), name.toString().trim())) {
            message(sender, "Job '" + name.toString().trim() + "' created successfully.");
            message(sender, "Now, enter a description: " + ChatColor.AQUA + "/job desc [description]");
        } else {
            error(sender, "Failed to create job.");
        }
    }
}
