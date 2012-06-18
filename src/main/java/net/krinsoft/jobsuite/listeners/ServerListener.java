package net.krinsoft.jobsuite.listeners;

import com.fernferret.allpay.AllPay;
import net.krinsoft.jobsuite.Job;
import net.krinsoft.jobsuite.JobCore;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author krinsdeath
 */
public class ServerListener implements Listener {
    private JobCore plugin;

    public ServerListener(JobCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void pluginLoad(PluginEnableEvent event) {
        if (Arrays.asList(AllPay.getValidEconPlugins()).contains(event.getPlugin().getDescription().getName())) {
            plugin.validateAllPay();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void playerJoin(PlayerJoinEvent event) {
        List<Job> jobs = plugin.getJobManager().getClaimableJobs(event.getPlayer());
        if (jobs.size() > 0) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "You have claimable rewards: " + ChatColor.GOLD + "/job claim");
        }
    }

}
