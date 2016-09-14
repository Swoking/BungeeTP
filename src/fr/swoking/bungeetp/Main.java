package fr.swoking.bungeetp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	private static Cuboide[] Zone;
	
	private Location loadLocation(String chemin){
		double x = getConfig().getDouble(chemin + ".x");
		double y = getConfig().getDouble(chemin + ".y");
		double z = getConfig().getDouble(chemin + ".z");
		World w = Bukkit.getWorld(getConfig().getString(chemin + ".w"));
		return new Location(w, x, y, z);
	}
	
	private void saveLocation(String chemin, Location l) {
		getConfig().set(chemin + ".x", l.getX());
		getConfig().set(chemin + ".y", l.getY());
		getConfig().set(chemin + ".z", l.getZ());
		getConfig().set(chemin + ".w", l.getWorld().getName());
		saveConfig();
	}
	
	private Boolean hasMoved(Location l1, Location l2){
        if(l1 != l2){
                return true;
        }else{
                return false;
        }
       
}

	@EventHandler
	public void onMove(PlayerMoveEvent e){
	        Player p = (Player)e.getPlayer();
	        if(Zone[1].isInZonne(p)){
	                p.sendMessage("§6Tu es dans la zone §4n°1§6 !");
	        }else if(Zone[2].isInZonne(p)){
	                p.sendMessage("§6Tu es dans la zone §4n°2§6 !");
	        }
	}
	
	@Override
	public void onEnable() {
		saveConfig();
		getServer().getPluginManager().registerEvents(this, this);
		if(getConfig().getBoolean("location")){
			Zone[1] = new Cuboide(loadLocation("uhc.l1"), loadLocation("uhc.l2"));
			Zone[2] = new Cuboide(loadLocation("uhc.l1"), loadLocation("uhc.l2"));
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player)sender;
		if(command.getName().equalsIgnoreCase("setzone")){
			if(args.length == 2){
				if(args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("2")){
					saveLocation(args[0]+".l"+args[1], p.getLocation());
					getConfig().set("location", true);
					saveConfig();
					p.sendMessage("la position " + args[1]+" pour la zone "+args[0]+" a été définis.");
				}
			}
		}
		
		return true;
	}

}
