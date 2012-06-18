package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobCancelCommand extends JobCommand {

    public JobCancelCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Cancel");
        setCommandUsage("/job cancel [job id]");
        setArgRange(1, 1);
        addKey("jobsuite cancel");
        addKey("job cancel");
        addKey("js cancel");
        setPermission("jobsuite.cancel", "Cancels the specified job.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        try {
            int id = Integer.parseInt(args.get(0));
            Job job = manager.getJob(id);
            if (job != null) {
                if (manager.cancelJob(sender, job)) {
                    message(sender, "The job was successfully canceled.");
                } else {
                    error(sender, "The job couldn't be canceled.");
                }
            } else {
                error(sender, "Couldn't find a matching job.");
            }
        } catch (NumberFormatException e) {
            error(sender, "Error parsing argument: expected number");
        }
    }
}
