package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobPostCommand extends JobCommand {

    public JobPostCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Post");
        setCommandUsage("/job post");
        setArgRange(0, 0);
        addKey("jobsuite post");
        addKey("job post");
        addKey("js post");
        setPermission("jobsuite.post", "Posts the current job.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        Job job = manager.getQueuedJob(sender.getName());
        if (job != null) {
            if (job.getDescription() != null && job.getName() != null && job.getOwner() != null && job.getReward() > 0 && job.getItems().size() > 0) {
                if (sender instanceof Player && !plugin.getBank().hasEnough((Player) sender, job.getReward(), -1)) {
                    error(sender, "You don't have enough money to post this job.");
                    return;
                }
                if (manager.addJob(sender.getName())) {
                    if (sender instanceof Player) {
                        plugin.getBank().take((Player) sender, job.getReward(), -1);
                    }
                    message(sender, "Job posted successfully with index '" + job.getId() + "'.");
                    message(sender, "View the job: " + ChatColor.DARK_AQUA + "/job info " + job.getId());
                    message(sender, "List jobs: " + ChatColor.DARK_AQUA + "/job list");
                } else {
                    error(sender, "The job couldn't be posted.");
                }
            }
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
