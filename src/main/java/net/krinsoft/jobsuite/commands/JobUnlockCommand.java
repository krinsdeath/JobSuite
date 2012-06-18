package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobUnlockCommand extends JobCommand {

    public JobUnlockCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Unlock");
        setCommandUsage("/job unlock [job id]");
        setArgRange(1, 1);
        addKey("jobsuite unlock");
        addKey("job unlock");
        addKey("js unlock");
        setPermission("jobsuite.unlock", "Removes the lock from the specified job.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        try {
            Job job = manager.getJob(Integer.parseInt(args.get(0)));
            if (job != null) {
                if (job.getLock() != null) {
                    if (job.getLock().equals(sender.getName()) || job.getOwner().equals(sender.getName()) || sender.hasPermission("jobsuite.admin.unlock")) {
                        job.unlock();
                        message(sender, "Job unlocked.");
                    } else {
                        error(sender, "You can't unlock that job.");
                    }
                } else {
                    message(sender, "Job isn't locked.");
                }
            } else {
                error(sender, "Couldn't find a matching job.");
            }
        } catch (NumberFormatException e) {
            error(sender, "Error parsing argument: expected number");
        }
    }
}
