package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import net.krinsoft.jobsuite.JobItem;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;
import java.util.Map;

/**
 * @author krinsdeath
 */
public class JobInfoCommand extends JobCommand {

    public JobInfoCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Job Info");
        setCommandUsage("/job info [job id] [item id]");
        setArgRange(0, 2);
        addKey("jobsuite info");
        addKey("job info");
        addKey("js info");
        setPermission("jobsuite.info", "Shows the specified job's information.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        int id = -1;
        Job job;
        if (args.size() == 0) {
            job = manager.getQueuedJob(sender.getName());
        } else {
            try {
                job = manager.getJob(Integer.parseInt(args.get(0)));
                if (args.size() > 1) {
                    id = Integer.parseInt(args.get(1));
                }
            } catch (NumberFormatException e) {
                job = null;
            }
        }
        if (job != null) {
            sender.sendMessage(ChatColor.DARK_GREEN + "=== " + ChatColor.DARK_AQUA + job.getName() + "(" + ChatColor.GOLD + job.getId() + ChatColor.DARK_AQUA + ")" + ChatColor.DARK_GREEN + " ===");
            if (id == -1) {
                sender.sendMessage(ChatColor.GREEN + "Owner: " + ChatColor.AQUA + job.getOwner());
                sender.sendMessage(ChatColor.GREEN + "Description: " + ChatColor.AQUA + job.getDescription());
                sender.sendMessage(ChatColor.GREEN + "Reward: " + ChatColor.AQUA + job.getReward());
                sender.sendMessage(ChatColor.GREEN + "Items Needed:");
                for (JobItem item : job.getItems()) {
                    sender.sendMessage("[" + item.getId() + "] " + item.getItem().getType().name() + ": " + item.getItem().getAmount() );
                }
            } else {
                JobItem item = job.getItem(id);
                if (item == null) {
                    error(sender, "This job has no item at that index.");
                    return;
                }
                ItemStack stack = item.getItem();
                sender.sendMessage(ChatColor.GREEN + "Item: " + stack.getType().name());
                sender.sendMessage(ChatColor.GREEN + "Amount: " + stack.getAmount());
                sender.sendMessage(ChatColor.GREEN + "Enchantments:");
                for (Map.Entry<Enchantment, Integer> entry : stack.getEnchantments().entrySet()) {
                    sender.sendMessage(entry.getKey().getName() + " (Level " + entry.getValue() + ")");
                }
            }
        } else {
            error(sender, "Couldn't find a matching job.");
        }
    }
}
