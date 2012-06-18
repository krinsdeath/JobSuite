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
public class JobListItemCommand extends JobCommand {

    public JobListItemCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: List Item");
        setCommandUsage("/job listitem");
        setArgRange(0, 1);
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
            if (args.size() == 0) {
                message(sender, "Items for " + job.getName() + ":");
                for (JobItem item : job.getItems()) {
                    message(sender, ChatColor.DARK_AQUA + "Index " + item.getId() + ChatColor.WHITE + " - " + item.getItem().getType().name() + " (" + item.getItem().getTypeId() + ")");
                }
            } else {
                try {
                    JobItem item = job.getItem(Integer.parseInt(args.get(0)));
                    if (item == null) {
                        error(sender, "This job has no item at that index.");
                        return;
                    }
                    ItemStack stack = item.getItem();
                    sender.sendMessage(ChatColor.GREEN + "Item: " + ChatColor.AQUA + stack.getType().name());
                    sender.sendMessage(ChatColor.GREEN + "Amount: " + ChatColor.AQUA + stack.getAmount());
                    sender.sendMessage(ChatColor.GREEN + "Enchantments:");
                    for (Map.Entry<Enchantment, Integer> entry : stack.getEnchantments().entrySet()) {
                        sender.sendMessage(entry.getKey().getName() + " (Level " + entry.getValue() + ")");
                    }
                } catch (NumberFormatException e) {
                    error(sender, "Error parsing argument: expected number");
                }
            }
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
