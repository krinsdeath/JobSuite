package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobDescriptionCommand extends JobCommand {

    public JobDescriptionCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Description");
        setArgRange(1, 20);
        addKey("jobsuite description");
        addKey("job description");
        addKey("js description");
        addKey("jobsuite desc");
        addKey("job desc");
        addKey("js desc");
        setPermission("jobsuite.description", "Sets the description for this job.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        Job job = manager.getQueuedJob(sender.getName());
        if (job != null) {
            StringBuilder desc = new StringBuilder();
            for (String word : args) {
                desc.append(word).append(" ");
            }
            job.setDescription(desc.toString().trim());
            message(sender, "Description set successfully.");
            message(sender, "Now, set a reward: " + ChatColor.AQUA + "/job reward [amount]");
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
