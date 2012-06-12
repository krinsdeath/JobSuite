package net.krinsoft.jobsuite;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

/**
 * @author krinsdeath
 */
public class JobItem {
    private final int jobId;
    private final int index;
    private final ItemStack item;

    public JobItem(Job job, int index, ItemStack item) {
        this.jobId = job.getId();
        this.index = index;
        this.item = item;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void addEnchant(Enchantment ench, int level) {
        if (ench.canEnchantItem(this.item)) {
            this.item.addEnchantment(ench, level);
        }
    }

    public int removeEnchant(Enchantment ench) {
        for (Enchantment key : new HashSet<Enchantment>(item.getEnchantments().keySet())) {
            if (key.equals(ench)) {
                return item.getEnchantments().remove(ench);
            }
        }
        return 0;
    }

    public int getId() {
        return this.index;
    }

    @Override
    public String toString() {
        return "JobItem{type=" + item.getType().toString() + ",amount=" + item.getAmount() + "}@" + hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 13 * 5;
        hash = hash + jobId + item.hashCode();
        return hash - 5;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o.getClass() != this.getClass()) { return false; }
        JobItem that = (JobItem) o;
        return that.hashCode() == this.hashCode();
    }

}
