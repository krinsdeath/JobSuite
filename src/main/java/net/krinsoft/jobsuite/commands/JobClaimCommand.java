package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import net.krinsoft.jobsuite.JobItem;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;
import java.util.Map;

/**
 * @author krinsdeath
 */
public class JobClaimCommand extends JobCommand {

    public JobClaimCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Claim Items");
        setCommandUsage("/job claim [job id]");
        setArgRange(0, 1);
        addKey("jobsuite claim");
        addKey("job claim");
        addKey("js claim");
        setPermission("jobsuite.claim", "Lists or claims the items from a posted job.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if (args.size() == 0) {
            List<Job> jobs = manager.getClaimableJobs(sender);
            if (jobs.size() > 0) {
                for (Job job : jobs) {
                    message(sender, ChatColor.GOLD + "[" + job.getId() + "] " + ChatColor.WHITE + job.getName() + ": Requires " + ChatColor.GOLD + job.getItems().size() + " free slots");
                }
            } else {
                message(sender, "You don't have any claimable jobs.");
            }
            return;
        }
        try {
            Job job = manager.getClaimableJob(sender, Integer.parseInt(args.get(0)));
            if (job != null) {
                if (sender instanceof Player) {
                    for (JobItem jItem : job.getItems()) {
                        Map<Integer, ItemStack> items = ((Player)sender).getInventory().addItem(jItem.getItem());
                        if (items.size() > 0) {
                            error(sender, "Some items couldn't be claimed.");
                        }
                    }
                    message(sender, "You've claimed your rewards.");
                } else {
                    message(sender, "Job items discarded.");
                }
                manager.claim(job);
            } else {
                error(sender, "Can't find a matching job.");
            }
        } catch (NumberFormatException e) {
            error(sender, "Error parsing argument: expected number");
        }
    }
}
