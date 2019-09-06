package dev.anullihate.est;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import org.itxtech.synapseapi.SynapsePlayer;

public class Main extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof SynapsePlayer) {
            SynapsePlayer p = (SynapsePlayer) sender;
            if (command.getName().equalsIgnoreCase("transfer")) {
                if (args.length > 0) {
                    if (p.getSynapseEntry().getServerDescription().equals(args[0])) {
                        p.sendMessage("\u00A7cYou are already on this server");
                    } else {
                        if (!p.transferByDescription(args[0])) {
                            p.sendMessage("\u00A7cUnknown server");
                        }
                    }
                } else {
                    p.sendMessage("Usage: /transfer <target>");
                }
            } else if (command.getName().equalsIgnoreCase("hub")) {
                if (p.getSynapseEntry().getServerDescription().equals("hub")) {
                    p.sendMessage("\u00A7cYou are already on a lobby server");
                } else {
                    p.transferByDescription("hub");
                }
            }
        }

        return true;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String message = event.getQuitMessage().toString();
        if (message.equals("timeout") || message.equals("generic reason") || message.equals("client disconnect") || message.equals("unknown")) {
            event.setQuitMessage("");
        }
    }
}
