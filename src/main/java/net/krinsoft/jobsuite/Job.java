package net.krinsoft.jobsuite;

import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author krinsdeath
 */
public class Job {
    private int id;
    private final String owner;
    private final String name;
    private String description;

    private long expiry;
    private String locked;

    private double reward;

    private Map<Integer, JobItem> objectives = new HashMap<Integer, JobItem>();

    /**
     * Constructs a new Job with the specified arguments
     * @param owner The name of the person who is posting the job
     * @param name The name of the job
     * @param id The job's unique index
     */
    public Job(String owner, String name, int id) {
        this(owner, name, id, (System.currentTimeMillis() + (1000L * 60L * 60L * 24L * 7L)));
    }

    public Job(String owner, String name, int id, long expiry) {
        this.owner = owner;
        this.name = name;
        this.id = id;
        this.expiry = expiry;
    }

    /**
     * Gets the job's unique reference ID number.
     * @return The job's reference ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the name specified during the job's creation.
     * @return The name of the job.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the name of the person who posted the job.
     * @return The name of the job's creator.
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * Gets the job's description.
     * @return The description of the job.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the job's description.
     * @param desc The new description of the job.
     */
    public void setDescription(String desc) {
        this.description = desc;
    }

    /**
     * Fetches the reward specified for the job's completion.
     * @return The job's reward.
     */
    public double getReward() {
        return this.reward;
    }

    /**
     * Sets the job's reward when it is completed.
     * @param amt The amount of money given for the job's completion.
     */
    public void setReward(double amt) {
        this.reward = amt;
    }

    /**
     * Gets the expiration date of the job.
     * @return The time, from the unix epoch, of the job's expiration
     */
    public long getDuration() {
        return this.expiry;
    }

    /**
     * Checks whether the job is expired or still available.
     * @return true if it's expired, otherwise false
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expiry;
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

    public int addItem(int id, ItemStack item) {
        objectives.put(id, new JobItem(this, id, item));
        return id;
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

    /**
     * Locks this job to the specified player.
     * @param player The player that is accepting the job.
     * @return
     * 0 - Job Expired              <br />
     * 3 - Null Lock Denied         <br />
     * 5 - Already Locked           <br />
     * 7 - Invalid Lock             <br />
     * 9 - Lock Success
     */
    public short lock(String player) {
        if (isExpired()) {
            return 0; // job timed out
        }
        if (player == null || player.equals("null")) {
            return 3; // lock can't be null
        }
        if (locked != null && !locked.equals(player)) {
            return 5; // job already locked
        }
        if (player.equals(owner)) {
            return 7; // can't lock your own job
        }
        locked = player;
        return 9; // job locked to player
    }

    public void unlock() {
        locked = null;
    }

    public String getLock() {
        return locked;
    }

}
