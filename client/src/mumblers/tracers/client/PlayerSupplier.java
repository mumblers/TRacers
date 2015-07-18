package mumblers.tracers.client;

import mumblers.tracers.common.Player;

import java.util.Collection;

/**
 * todo
 *
 * @author davidot
 */
public interface PlayerSupplier {

    Player getTrackingPlayer();

    Collection<Player> getPlayers();

}
