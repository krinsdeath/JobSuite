package net.krinsoft.jobsuite;

import net.krinsoft.jobsuite.db.Database;
import org.bukkit.enchantments.Enchantment;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krinsdeath
 */
public class JobManager {
    private final JobCore plugin;
    private Database database;

    private Map<Integer, Job> jobs = new HashMap<Integer, Job>();

    private int nextJob;

    public JobManager(JobCore instance) {
        this.plugin = instance;
    }

    public void load() {
        database = new Database(plugin);
        nextJob = plugin.getConfig().getInt("jobs.total", 0);
    }

    public void close() {
        persist();
        database.close();
    }

    public void persist() {
        PreparedStatement jobStatement = database.prepare("REPLACE INTO jobsuite_base (job_id, owner, name, description, reward) VALUES (?, ?, ?, ?, ?);");
        PreparedStatement itemStatement = database.prepare("REPLACE INTO jobsuite_items (job_id, enchantment_entry, type, amount) VALUES (?, ?, ?, ?);");
        PreparedStatement enchStatement = database.prepare("REPLACE INTO jobsuite_enchantments (job_id, enchantment_entry, enchantment, power) VALUES (?, ?, ?, ?);");
        try {
            jobStatement.getConnection().setAutoCommit(false);
            for (Map.Entry<Integer, Job> entry : jobs.entrySet()) {
                Job job = entry.getValue();
                jobStatement.setInt(1, job.getId());
                jobStatement.setString(2, job.getOwner());
                jobStatement.setString(3, job.getName());
                jobStatement.setString(4, job.getDescription());
                jobStatement.setDouble(5, job.getReward());
                jobStatement.executeUpdate();
                for (JobItem item : job.getItems()) {
                    itemStatement.setInt(1, job.getId());
                    itemStatement.setInt(2, item.hashCode());
                    itemStatement.setString(3, item.getItem().getType().toString());
                    itemStatement.setInt(4, item.getItem().getAmount());
                    itemStatement.executeUpdate();
                    for (Map.Entry<Enchantment, Integer> ench : item.getItem().getEnchantments().entrySet()) {
                        enchStatement.setInt(1, job.getId());
                        enchStatement.setInt(2, item.hashCode());
                        enchStatement.setInt(3, ench.getKey().getId());
                        enchStatement.setInt(4, ench.getValue());
                        enchStatement.executeUpdate();
                    }
                }
            }
            jobStatement.getConnection().commit();
        } catch (SQLException e) {
            plugin.getLogger().warning("An SQLException occurred: " + e.getMessage());
        }
    }

    public int getNextJob() {
        return this.nextJob;
    }

    public List<Job> getJobs() {
        return new ArrayList<Job>(jobs.values());
    }

    /**
     * Gets the specified Job by its ID
     * @param id The ID of the job we're fetching
     * @return The Job and all of its details, otherwise null
     */
    public Job getJob(int id) {
        return jobs.get(id);
    }

    public boolean addJob(Job job) {
        plugin.getLogger().finer("Job '" + job.getName() + "' registered.");
        jobs.put(job.getId(), job);
        return true;
    }

    /**
     * Registers the specified job
     * @param owner The owner of the job
     * @return true if the job is valid and added, otherwise false
     */
    public boolean addJob(String owner) {
        Job job = queued.get(owner);
        if (owner == null || job == null) {
            return false;
        }
        nextJob++;
        job.setId(nextJob);
        jobs.put(job.getId(), job);
        plugin.getConfig().set("jobs.total", nextJob);
        return true;
    }



    /////////////////
    // JOB QUEUING //
    /////////////////

    private Map<String, Job> queued = new HashMap<String, Job>();

    /**
     * Gets a job that is currently being created by the player's name
     * @param player The player whose job we're fetching
     * @return The job associated with the player, or null
     */
    public Job getQueuedJob(String player) {
        return queued.get(player);
    }

    /**
     * Adds a job to the queue unless the player already has one queued
     * @param player The player responsible for the job
     * @param job The basic job object
     * @return true if the job is added, otherwise false (the player has one queued or an object was null)
     */
    public boolean addQueuedJob(String player, Job job) {
        if (queued.get(player) == null) {
            if (player == null || job == null) {
                return false;
            }
            queued.put(player, job);
            return true;
        }
        return false;
    }

    /**
     * Attempts to remove the specified player's queued job
     * @param player The player whose job we're removing
     * @return true if the job existed and was removed, otherwise false
     */
    public boolean removeQueuedJob(String player) {
        return queued.remove(player) != null;
    }

}
