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
public class JobRewardCommand extends JobCommand {

    public JobRewardCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Reward");
        setCommandUsage("/job reward [amount]");
        setArgRange(1, 1);
        addKey("jobsuite reward");
        addKey("job reward");
        addKey("js reward");
        setPermission("jobsuite.reward", "Sets the reward for completion of the job.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        Job job = manager.getQueuedJob(sender.getName());
        if (job != null) {
            try {
                double reward = Double.parseDouble(args.get(0));
                if (sender instanceof Player && !plugin.getBank().hasEnough((Player) sender, reward, -1)) {
                    error(sender, "You don't have enough money for that.");
                    return;
                }
                job.setReward(reward);
                message(sender, "Reward added.");
                message(sender, "Now, add required items: " + ChatColor.AQUA + "/job additem [name] [amount]");
            } catch (NumberFormatException e) {
                error(sender, "Supplied argument must be a " + ChatColor.DARK_RED + "number");
            }
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
