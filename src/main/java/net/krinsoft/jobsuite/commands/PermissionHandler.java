package net.krinsoft.jobsuite.commands;

import com.pneumaticraft.commandhandler.PermissionsInterface;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

/**
 * @author krinsdeath
 */
public class PermissionHandler implements PermissionsInterface {
    @Override
    public boolean hasPermission(CommandSender sender, String node, boolean isOpRequired) {
        return sender instanceof ConsoleCommandSender || sender.hasPermission(node);
    }

    @Override
    public boolean hasAnyPermission(CommandSender sender, List<String> allPermissionStrings, boolean opRequired) {
        for (String node : allPermissionStrings) {
            if (hasPermission(sender, node, opRequired)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAllPermission(CommandSender sender, List<String> allPermissionStrings, boolean opRequired) {
        for (String node : allPermissionStrings) {
            if (!hasPermission(sender, node, opRequired)) {
                return false;
            }
        }
        return true;
    }
}
