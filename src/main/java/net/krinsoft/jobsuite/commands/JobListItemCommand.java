package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import net.krinsoft.jobsuite.JobItem;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobListItemCommand extends JobCommand {

    public JobListItemCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: List Item");
        setCommandUsage("/job listitem");
        setArgRange(0, 0);
        addKey("jobsuite listitems");
        addKey("job listitems");
        addKey("js listitems");
        addKey("jobsuite li");
        addKey("job li");
        addKey("js li");
        setPermission("jobsuite.listitems", "List your current job's items.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        Job job = manager.getQueuedJob(sender.getName());
        if (job != null) {
            message(sender, "Items for " + job.getName() + ":");
            for (JobItem item : job.getItems()) {
                message(sender, ChatColor.DARK_AQUA + "Index " + item.getId() + ChatColor.WHITE + " - " + item.getItem().getType().name() + " (" + item.getItem().getTypeId() + ")");
            }
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
