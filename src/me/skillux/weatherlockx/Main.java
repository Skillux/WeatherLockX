package me.skillux.weatherlockx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		
		System.out.println(ChatColor.DARK_AQUA + "\n\nWeatherLockX has been loaded!\n\n");
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		saveDefaultConfig();
		
	}
	
    public void saveDefaultConfig() {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveResource("config.yml", false);
        }
    		
	}
	
	List<String> clear = new ArrayList<>();
	List<String> rain = new ArrayList<>();
	List<String> thunder = new ArrayList<>();
	List<String> enabled = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		String world = player.getWorld().getName();
		World world1 = player.getWorld();
		
		if(player.hasPermission("weatherlockx.admin")) {
			
			 if(args.length == 0) {
				 
				 player.sendMessage(ChatColor.GRAY + "--------------------" + ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "WeatherLockX" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + "--------------------");
				 player.sendMessage(ChatColor.AQUA + "Below is a list of all WeatherLockX commands:");
				 player.sendMessage(" ");
				 player.sendMessage(ChatColor.DARK_AQUA + "/weatherlock: " + ChatColor.WHITE + "Displays a list of commands.");
				 player.sendMessage(" ");
				 player.sendMessage(ChatColor.DARK_AQUA + "/weatherlock <Clear/Rain/Thunder>: " + ChatColor.WHITE + "Changes and locks the weather to either clear, rainy, or thundering.");
				 player.sendMessage(" ");
				 player.sendMessage(ChatColor.DARK_AQUA + "/weatherlock off: " + ChatColor.WHITE + "Returns the weather cycle to normal.");
				 player.sendMessage(" ");
				 player.sendMessage(ChatColor.DARK_AQUA + "/weatherlock reload: " + ChatColor.WHITE + "Realods the config file.");
				 player.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
				 
				 
				 return true; 
			 }
			 
			 if(args[0].equalsIgnoreCase("clear")) {
				 
				 if(clear.contains(world)) {
					 
					 player.sendMessage(getConfig().getString("Already-Enabled").replaceAll("&", "§"));
					 
				 } else if (rain.contains(world)) {
					 
					 rain.remove(world);
					 
				 } else if(thunder.contains(world)) {
					 
					 thunder.remove(world);
					 
				 }
				 
				 if (enabled.contains(world)) {
					 
					 enabled.remove(world);
				 }
				 
				 world1.setGameRuleValue("doWeatherCycle", "false");
				 player.getWorld().setStorm(false);
				 player.getWorld().setThundering(false);
				 
				 if(!clear.contains(world)) {
					 
					 clear.add(world);
					 player.sendMessage(getConfig().getString("WeatherLock-Set").replaceAll("&", "§").replaceAll("%weather%", "Clear"));

				 }
				 
				 enabled.add(world);
				 
				 return true;
				 
			 } else if(args[0].equalsIgnoreCase("rain")) {
				 
				 if(rain.contains(world)) {
					 
					 player.sendMessage(getConfig().getString("Already-Enabled").replaceAll("&", "§"));
					 
				 } else if (clear.contains(world)) {
					 
					 clear.remove(world);
					 
				 } else if(thunder.contains(world)) {
					 
					 thunder.remove(world);
					 
				 }
				 
				 if (enabled.contains(world)) {
					 
					 enabled.remove(world);
				 }
				 
				 world1.setGameRuleValue("doWeatherCycle", "false");
				 player.getWorld().setStorm(true);
				 player.getWorld().setThundering(false);
				 
				 if(!rain.contains(world)) {
					 
					 player.sendMessage(getConfig().getString("WeatherLock-Set").replaceAll("&", "§").replaceAll("%weather%", "Rainy"));
					 rain.add(world);
				 }
				 
				 enabled.add(world);
				 				 
				 return true;
				 
			 } else if(args[0].equalsIgnoreCase("thunder")) {
				 
				 if(thunder.contains(world)) {
					 
					 player.sendMessage(getConfig().getString("Already-Enabled").replaceAll("&", "§"));
					 
				 } else if (clear.contains(world)) {
					 
					 clear.remove(world);
					 
				 } else if(rain.contains(world)) {
					 
					 rain.remove(world);
					 
				 }
				 
				 if (enabled.contains(world)) {
					 
					 enabled.remove(world);
				 }
				 
				 world1.setGameRuleValue("doWeatherCycle", "false");
				 player.getWorld().setStorm(true);
				 player.getWorld().setThundering(true);
				 
				 if(!thunder.contains(world)) {
					 
					 player.sendMessage(getConfig().getString("WeatherLock-Set").replaceAll("&", "§").replaceAll("%weather%", "Thundering"));
					 thunder.add(world);
				 }
				 
				 enabled.add(world);
				 
				 return true;
				 
			 } else if (args[0].equalsIgnoreCase("off")) {
				 
				 if(enabled.contains(world)) {
					 player.sendMessage(getConfig().getString("Turned-Off").replaceAll("&", "§"));
				 } else {
					 player.sendMessage(getConfig().getString("No-Selected-Mode").replaceAll("&", "§"));
				 }
				 
				 if(enabled.contains(world)) {
					 
					enabled.remove(world);
					world1.setGameRuleValue("doWeatherCycle", "true");

				 }
				 
				 if(clear.contains(world)) {
					 clear.remove(world);
				 }
				 
				 if(rain.contains(world)) {
					 rain.remove(world);
				 }
				 
				 if(thunder.contains(world)) {
					 thunder.remove(world);
				 }
				 
				 return true;
				 
			 } else if (args[0].equalsIgnoreCase("reload")) {
				 
				reloadConfig();
				player.sendMessage(getConfig().getString("Reload-Message").replaceAll("&", "§"));
				
				return true;
				
				} else {
				 
				 player.sendMessage(getConfig().getString("Invalid-Usage").replaceAll("&", "§"));
				 
				 return true;
			 }
			
			
		} else { 
			
			player.sendMessage(getConfig().getString("No-Permission").replaceAll("&", "§"));
			
		}
		
		return true;
	}
	
	public void setGameRules(World world) {
		
		Player player = Bukkit.getPlayer(getName());
		String world1 = player.getWorld().getName();
		
		if(enabled.contains(world1)) {
			
			world.setGameRuleValue("doWeatherCycle", "false");
			
		}
		
	}

}


