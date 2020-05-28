package com.vexsoftware.votifier.model;

import static com.vexsoftware.votifier.NuVotifierBukkit.instance;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * {@code VotifierEvent} is a custom Bukkit event class that is sent
 * synchronously to CraftBukkit's main thread allowing other plugins to listener
 * for votes.
 *
 * @author frelling
 */
public class VotifierEvent extends Event {
    /**
     * Event listener handler list.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Encapsulated vote record.
     */
    private Vote vote;
    private Boolean keepAlive;

    /**
     * Constructs a vote event that encapsulated the given vote record.
     *
     * @param vote vote record
     */
    public VotifierEvent(final Vote vote) {
        this.vote = vote;
    }
    public VotifierEvent() {
    }
	
    /**
     * Return the encapsulated vote record.
     *
     * @return vote record
     */
    public Vote getVote() {
        return vote;
    }
	
	public Boolean getKeepAlive() {
		return instance.getConfig().getBoolean("database.use");
	}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
