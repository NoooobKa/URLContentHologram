package ru.nbk.hologram.nms.v1_16_R3;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityLiving;
import ru.nbk.hologram.api.util.HologramLine;

public class EntityLine extends EntityArmorStand implements HologramLine{

	
	
	public EntityLine() {
		super(EntityTypes.ARMOR_STAND, ((CraftWorld)Bukkit.getWorlds().get(0)).getHandle());
		
		setNoGravity(true);
		setCustomNameVisible(true);
		setInvisible(true);
		
	}

	@Override
	public void setName(String name) {
		if (name.length() > 128) {
			name = name.substring(0, 128);
		}
		
		setCustomName(new ChatComponentText(name));
		refresh();
	}

	@Override
	public String getName() {
		return getCustomName().getText();
	}
	
	@Override
	public Location getLocation() {
		return new Location(getWorld().getWorld(), locX(), locY(), locZ(), yaw, pitch);
	}

	@Override
	public void teleport(Location location) {
		world = ((CraftWorld)location.getWorld()).getHandle();
		setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		
		PacketPlayOutEntityTeleport teleport = new PacketPlayOutEntityTeleport(this);
		getWorld().getWorld().getPlayers().forEach(p -> sendPacketForPlayer(p, teleport));
	}

	
	@Override
	public void show() {
		setCustomNameVisible(false);
		refresh();
	}

	@Override
	public void hide() {
		setCustomNameVisible(true);
		refresh();
	}

	@Override
	public void refresh() {
		PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(this.getId(), this.getDataWatcher(),true);
		sendPacketForAll(meta);
	}

	@Override
	public void spawn(Player player) {
		PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving(this);
		sendPacketForPlayer(player, spawn);
		
		PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(this.getId(), this.getDataWatcher(),true);
		sendPacketForAll(meta);
	}

	@Override
	public void remove(Player player) {
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(getId());
		sendPacketForPlayer(player, destroy);
	}
	 
	private void sendPacketForPlayer(Player player, Packet<?> packet) {
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}

	private void sendPacketForAll(Packet<?> packet) {
		Bukkit.getOnlinePlayers().forEach(p -> sendPacketForPlayer(p, packet));
	}
	
}
