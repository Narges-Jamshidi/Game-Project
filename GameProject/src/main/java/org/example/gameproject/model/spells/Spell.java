package org.example.gameproject.model.spells;

import org.example.gameproject.model.player.Player;

public interface Spell {
    default int getPrice() {
        return 0;
    }

    default void drop( Player player) {
    }

}
