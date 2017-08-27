package me.torciv.test.adapter;

import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;

public class PaperTNTFactory implements TNTFactory {


    //REQUIERES LOC AND WORLD

    @Override
    public EntityTNTPrimed create(Location location, double x, double y, double z, Object o) {

        return new EntityTNTPrimed(location,(World) location.getWorld(), x, y, z, null);
    }

}
