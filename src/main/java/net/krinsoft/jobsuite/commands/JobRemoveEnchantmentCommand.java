package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import net.krinsoft.jobsuite.JobItem;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobRemoveEnchantmentCommand extends JobCommand {

    public JobRemoveEnchantmentCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Remove Enchantment");
        setCommandUsage("/job remenchant [item id] [enchantment]");
        setArgRange(2, 2);
        addKey("jobsuite remenchant");
        addKey("job remenchant");
        addKey("js remenchant");
        addKey("jobsuite re");
        addKey("job re");
        addKey("js re");
        setPermission("jobsuite.remenchant", "Removes an enchantment from a job item.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        Job job = manager.getQueuedJob(sender.getName());
        if (job != null) {
            JobItem jItem;
            Enchantment ench;
            try {
                jItem = job.getItem(Integer.parseInt(args.get(0)));
                if (jItem == null) {
                    error(sender, "Couldn't find an item at that index.");
                    return;
                }
            } catch (NumberFormatException e) {
                error(sender, "Error parsing argument: expected number");
                return;
            }
            try {
                ench = Enchantment.getById(Integer.parseInt(args.get(1)));
            } catch (NumberFormatException e) {
                ench = Enchantment.getByName(args.get(1));
            }
            if (ench == null) {
                error(sender, "No such enchantment.");
                return;
            } else if (ench.canEnchantItem(jItem.getItem())) {
                error(sender, "That item can't have that enchantment.");
                return;
            }
            int id = jItem.removeEnchant(ench);
            if (id != 0) {
                message(sender, "Successfully removed enchantment from item at index '" + jItem.getId() + "'.");
                message(sender, "Add an enchantment: " + ChatColor.DARK_AQUA + "/job addenchant " + jItem.getId() + " [enchantment] [level]");
                message(sender, "Add an item: " + ChatColor.DARK_AQUA + "/job additem [type] [amount]");
                message(sender, "List current items: " + ChatColor.DARK_AQUA + "/job listitems");
            } else {
                message(sender, "Couldn't find that enchantment on that item.");
            }
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
