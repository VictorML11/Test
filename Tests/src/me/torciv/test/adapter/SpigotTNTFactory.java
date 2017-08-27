package me.torciv.test.adapter;


import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;

public class SpigotTNTFactory implements TNTFactory {

    @Override
    public EntityTNTPrimed create(Location location, double x, double y, double z, Object o) {
        return new EntityTNTPrimed((World) location.getWorld(), x, y, z, null);
    }
}
