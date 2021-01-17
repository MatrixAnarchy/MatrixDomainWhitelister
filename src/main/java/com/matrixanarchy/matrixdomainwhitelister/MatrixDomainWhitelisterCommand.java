package com.matrixanarchy.matrixdomainwhitelister;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MatrixDomainWhitelisterCommand implements CommandExecutor {

    private final MatrixDomainWhitelister plugin;

    public MatrixDomainWhitelisterCommand(MatrixDomainWhitelister plugin){
        this.plugin =plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage("Configuration reloaded");
            return true;
        }
        return false;
    }
}
