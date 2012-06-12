package net.krinsoft.jobsuite;

import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author krinsdeath
 */
public class Job {
    private int id = -1;
    private final String owner;
    private final String name;
    private String description;

    private double reward;

    private Map<Integer, JobItem> objectives = new HashMap<Integer, JobItem>();

    /**
     * Constructs a new Job with the specified arguments
     * @param owner The name of the person who is posting the job
     * @param name The name of the job
     */
    public Job(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public double getReward() {
        return this.reward;
    }

    public void setReward(double amt) {
        this.reward = amt;
    }

    public List<JobItem> getItems() {
        return new ArrayList<JobItem>(objectives.values());
    }

    /**
     * Fetches one of this job's JobItems by its index
     * @param index The index of the item we're fetching
     * @return The item, if it exists; otherwise false
     */
    public JobItem getItem(int index) {
        return objectives.get(index);
    }

    /**
     * Inserts the specified item into the objectives list
     * @param item The item to insert
     * @return The item's list index in this job
     */
    public int addItem(ItemStack item) {
        int next = objectives.size();
        objectives.put(next, new JobItem(this, next, item));
        return next;
    }

    /**
     * Removes the specified item from the objectives list if it exists
     * @param index The index of the item we're removing
     * @return true if the index was found and removed, otherwise false
     */
    public boolean removeItem(int index) {
        if (objectives.get(index) != null) {
            objectives.remove(index);
            return true;
        }
        return false;
    }

}
