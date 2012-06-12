package net.krinsoft.jobsuite.commands;

import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class JobAddItemCommand extends JobCommand {

    public JobAddItemCommand(JobCore instance) {
        super(instance);
        setName("JobSuite: Add Item");
        setCommandUsage("/job additem [type] [amount]");
        setArgRange(2, 2);
        addKey("jobsuite additem");
        addKey("job additem");
        addKey("js additem");
        addKey("jobsuite ai");
        addKey("job ai");
        addKey("js ai");
        setPermission("jobsuite.additem", "Adds a required job item.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        Job job = manager.getQueuedJob(sender.getName());
        if (job != null) {
            Material type;
            try {
                type = Material.getMaterial(Integer.parseInt(args.get(0)));
            } catch (NumberFormatException e) {
                type = Material.matchMaterial(args.get(0));
            }
            if (type == null) {
                error(sender, "Unknown item type.");
                return;
            }
            int amount;
            try {
                amount = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                error(sender, "Error parsing argument: expected number");
                return;
            }
            ItemStack item = new ItemStack(type, amount);
            int id = job.addItem(item);
            message(sender, "Item added at index '" + id + "'");
            message(sender, "Add an enchantment: /job addenchant " + id + " [enchantment] [level]");
            message(sender, "Remove this item: /job remitem " + id);
            message(sender, "Add more items: /job additem [type] [amount]");
            message(sender, "Post the job: /job post");
        } else {
            error(sender, "You aren't currently making a job.");
        }
    }
}
