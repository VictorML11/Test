package me.torciv.test.adapter;

import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import org.bukkit.Location;

public interface TNTFactory {

    EntityTNTPrimed create(Location location, double x, double y, double z, Object o);

}
