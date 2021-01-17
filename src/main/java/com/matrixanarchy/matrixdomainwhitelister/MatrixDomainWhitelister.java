package com.matrixanarchy.matrixdomainwhitelister;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MatrixDomainWhitelister extends JavaPlugin implements Listener {

    private Set<String> validHostNames;
    private  boolean ignoreCase = true;
    private String warning;


    public static Set<String> getHostNames(List<String> hosts, boolean ignoreCase) {
        Set<String> result = new HashSet<>();
        if (hosts != null) {
            for (String host : hosts) {
                if (ignoreCase) {
                    host = host.toLowerCase();
                }
                result.add(host);
            }
        }
        return result;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        reloadConfig();
        PluginManager pm =getServer().getPluginManager();
        getCommand("MatrixDomainWhitelister").setExecutor(new MatrixDomainWhitelisterCommand(this));
        pm.registerEvents(this,this);

    }
    @Override
    public void reloadConfig() {
        super.reloadConfig();
        Configuration config = getConfig();
        warning = ChatColor.translateAlternateColorCodes('&', config.getString("warning"));
        validHostNames = getHostNames(config.getStringList("allowed-host-names"), ignoreCase);
    }
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        String hostname = event.getHostname();
        int port = hostname.indexOf(":");
        if (port != -1) {
            hostname = hostname.substring(0, port);
        }
        if (isBlocked(hostname)) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, warning);
        }


    }
    private boolean isBlocked(String host) {
        if (ignoreCase) {
            host = host.toLowerCase();
        }
        return !validHostNames.contains(host);
    }

}
