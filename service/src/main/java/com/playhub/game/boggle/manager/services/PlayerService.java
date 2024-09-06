package com.playhub.game.boggle.manager.services;

import java.util.Set;
import java.util.UUID;

public interface PlayerService {

    Set<UUID> filterActivePlayers(Set<UUID> playerIds);

}
