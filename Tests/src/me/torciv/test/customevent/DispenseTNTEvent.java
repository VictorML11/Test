package me.torciv.test.customevent;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockDispenseEvent;

public class DispenseTNTEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private BlockDispenseEvent event;
    private CraftEntity entity;


    public DispenseTNTEvent(BlockDispenseEvent event, CraftEntity entity) {
        this.event = event;
        this.entity = entity;
        this.cancelled = false;
    }

    public void setEntity(CraftEntity entity) {
        this.entity = entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public BlockDispenseEvent getEvent() {
        return event;
    }

    public CraftEntity getEntity() {
        return entity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
