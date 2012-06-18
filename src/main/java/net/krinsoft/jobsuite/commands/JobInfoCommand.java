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
        addCommandExample("/job info 3 0");
        addCommandExample("/job info this 1");
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
        if (args.size() == 0 || args.get(0).equalsIgnoreCase("this")) {
            job = manager.getQueuedJob(sender.getName());
        } else {
            try {
                if (args.get(0).equalsIgnoreCase("this")) {
                    job = manager.getQueuedJob(sender.getName());
                } else {
                    job = manager.getJob(Integer.parseInt(args.get(0)));
                }
                if (args.size() > 1) {
                    id = Integer.parseInt(args.get(1));
                }
            } catch (NumberFormatException e) {
                job = null;
            }
        }
        if (job != null) {
            sender.sendMessage(ChatColor.DARK_GREEN + "=== " +
                    ChatColor.DARK_AQUA + job.getName() + "(" + ChatColor.GOLD + (job.getId() != -1 ? job.getId() : "unposted") + ChatColor.DARK_AQUA + ")" +
                    (id == -1 ? "": ChatColor.DARK_AQUA + " - item(" + ChatColor.GOLD + id + ChatColor.DARK_AQUA + ")") +
                    ChatColor.DARK_GREEN + " ==="
            );
            if (id == -1) {
                sender.sendMessage(ChatColor.GREEN + "Owner: " + ChatColor.AQUA + job.getOwner());
                long dur = job.getDuration() - System.currentTimeMillis();
                String time = String.format("%d days, %d hrs, %d mins, %d seconds",
                        (dur / (1000 * 60 * 60 * 24)) % 7,
                        (dur / (1000 * 60 * 60)) % 24,
                        (dur / (1000 * 60)) % 60,
                        (dur / 1000) % 60
                        );
                sender.sendMessage(ChatColor.GREEN + "Expires in: " + ChatColor.AQUA + time);
                if (job.getLock() != null) {
                    if (job.getOwner().equals(sender.getName())) {
                        sender.sendMessage(ChatColor.GREEN + "Locked by: " + ChatColor.AQUA + job.getLock());
                    } else if (job.getLock().equals(sender.getName())) {
                        sender.sendMessage(ChatColor.AQUA + "You've accepted this job.");
                    }
                }
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
                sender.sendMessage(ChatColor.GREEN + "Item: " + ChatColor.AQUA + stack.getType().name());
                sender.sendMessage(ChatColor.GREEN + "Amount: " + ChatColor.AQUA + stack.getAmount());
                sender.sendMessage(ChatColor.GREEN + "Enchantments:");
                for (Map.Entry<Enchantment, Integer> entry : stack.getEnchantments().entrySet()) {
                    sender.sendMessage(entry.getKey().getName() + " (Level " + entry.getValue() + ")");
                }            }
        } else {
            error(sender, "Couldn't find a matching job.");
        }
    }
}
