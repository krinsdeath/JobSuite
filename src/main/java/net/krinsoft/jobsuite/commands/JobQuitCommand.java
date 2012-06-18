package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.JobCore;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobQuitCommand extends JobCommand {

    public JobQuitCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Quit");
        setCommandUsage("/job quit");
        setArgRange(0, 0);
        addKey("jobsuite quit");
        addKey("job quit");
        addKey("js quit");
        setPermission("jobsuite.quit", "Quits the creation process of a job.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if (manager.removeQueuedJob(sender.getName())) {
            message(sender, "You have quit the job creation process.");
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
