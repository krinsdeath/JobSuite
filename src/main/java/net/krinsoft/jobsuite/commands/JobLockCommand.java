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
public class JobLockCommand extends JobCommand {

    public JobLockCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Lock Job");
        setCommandUsage("/job lock [job id]");
        setArgRange(1, 1);
        addKey("jobsuite lock");
        addKey("job lock");
        addKey("js lock");
        setPermission("jobsuite.lock", "Accepts and locks the specified job.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        try {
            Job job = manager.getJob(Integer.parseInt(args.get(0)));
            if (job != null) {
                if (job.getOwner().equals(sender.getName())) {
                    error(sender, "You can't lock your own job.");
                    return;
                }
                short lock = job.lock(sender.getName());
                switch (lock) {
                    case 0:
                        error(sender, "That job has expired.");
                        manager.cancelJob(sender, job);
                        return;
                    case 5:
                        error(sender, "That job has already been accepted.");
                        return;
                    case 7:
                        error(sender, "You can't lock your own job.");
                        return;
                    case 9:
                        message(sender, "You've accepted the job: " + ChatColor.GOLD + "[" + job.getId() + "] " + job.getName());
                        break;
                    default:
                        message(sender, "Something weird happened.");
                        break;
                }
            } else {
                error(sender, "Couldn't find a matching job.");
            }
        } catch (NumberFormatException e) {
            error(sender, "Error parsing argument: expected number");
        }
    }
}
