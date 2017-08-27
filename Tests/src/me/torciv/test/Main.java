package me.torciv.test;

import me.torciv.test.adapter.PaperTNTFactory;
import me.torciv.test.adapter.SpigotTNTFactory;
import me.torciv.test.adapter.TNTFactory;
import me.torciv.test.customevent.DispenseTNTEvent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;


public class Main extends JavaPlugin{

    TNTFactory factory;

    @Override
    public void onEnable() {

        BlockDispenser.REGISTRY.a(Item.getItemOf(Blocks.TNT), new DispenseBehaviorItem() {

            @Override
            protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                World world = isourceblock.getWorld();
                BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
                ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
                org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(),
                        isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
                CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);

                BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(),
                        new org.bukkit.util.Vector(
                                (double)blockposition.getX() + 0.5,
                                (double) blockposition.getY() + 0.5,
                                (double) blockposition.getZ() + 0.5));

                if (!BlockDispenser.eventFired) {
                    world.getServer().getPluginManager().callEvent(event);
                }

                if (!BlockDispenser.eventFired) {
                    world.getServer().getPluginManager().callEvent(event);
                }
                if (event.isCancelled()) {
                    ++itemstack.count;
                    return itemstack;
                }

                if (!event.getItem().equals(craftItem)) {
                    ++itemstack.count;
                    ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                    IDispenseBehavior idispensebehavior = BlockDispenser.REGISTRY.get(eventStack.getItem());
                    if (idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                        idispensebehavior.a(isourceblock, eventStack);
                        return itemstack;
                    }
                }

                Location loc = new Location(isourceblock.getWorld().getWorld(),
                        (double)blockposition.getX() + 0.5,
                        (double) blockposition.getY() + 0.5,
                        (double) blockposition.getZ() + 0.5);

                boolean spigot = false;

                Class<EntityTNTPrimed> cls = EntityTNTPrimed.class;

                try {
                    cls.getConstructor(Location.class, World.class, double.class, double.class, double.class, Object.class);
                } catch (NoSuchMethodException ex) {
                    // It's spigot
                    spigot = true;
                }
                if (spigot) {
                    factory = new SpigotTNTFactory();
                } else {
                    factory = new PaperTNTFactory();
                }

                EntityTNTPrimed entitytntprimed = factory.create(loc,event.getVelocity().getX(), event.getVelocity().getY(),
                        event.getVelocity().getZ(), null);


                /* EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(
                        loc, world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), null);
                */

                DispenseTNTEvent spawnEvent = new DispenseTNTEvent(event, entitytntprimed.getBukkitEntity());
                world.getServer().getPluginManager().callEvent(spawnEvent);

                if (!spawnEvent.isCancelled()) {
                    world.addEntity(entitytntprimed);
                    //System.out.println("Event called");
                    world.makeSound(entitytntprimed, "game.tnt.primed", 1.0f, 1.0f);
                }

                return itemstack;
            }
        });
    }
}
