package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobRemoveItemCommand extends JobCommand {

    public JobRemoveItemCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Remove Item");
        setCommandUsage("/job remitem [item id]");
        setArgRange(1, 1);
        addKey("jobsuite remitem");
        addKey("job remitem");
        addKey("js remitem");
        addKey("jobsuite ri");
        addKey("job ri");
        addKey("js ri");
        setPermission("jobsuite.remitem", "Removes an item from the job objectives.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        Job job = manager.getQueuedJob(sender.getName());
        if (job != null) {
            try {
                if (job.removeItem(Integer.parseInt(args.get(0)))) {
                    message(sender, "Successfully removed the item at index " + args.get(0));
                } else {
                    message(sender, "Couldn't find an item at that index.");
                }
            } catch (NumberFormatException e) {
                error(sender, "Error parsing argument: expected number");
            }
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
