package proto.games.matchmaker.domain.matchmaker;

import lombok.Getter;
import proto.games.matchmaker.domain.player.Player;
import proto.games.matchmaker.domain.player.Rank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayerBulk implements Cloneable {
    private final List<Player> players;

    @Getter
    private Rank medianRank;

    public PlayerBulk(Collection<Player> players) {
        this.players = new ArrayList<>(players);
        Collections.sort(this.players);
        defineMedianRank(this.players);
    }

    public List<Player> takeTopHighLevelPlayers(int length) {
        List<Player> topPlayers = new ArrayList<>(players.subList(0, length));
        remove(topPlayers);
        return topPlayers;
    }

    public List<Player> getPlayers(){
        return new ArrayList<>(players);
    }

    public void remove(List<Player> players) {
        for (Player player : players) {
            remove(player);
        }
    }

    public void remove(Player player) {
        players.remove(player);
    }

    public long count() {
        return players.size();
    }

    @Override
    public PlayerBulk clone() {
        return new PlayerBulk(new ArrayList<>(players));
    }

    private void defineMedianRank(List<Player> players) {
        if (players.size() % 2 == 0) {
            this.medianRank = players.get(players.size() / 2 + 1).getRank();
        } else {
            this.medianRank = players.get(players.size() / 2).getRank();
        }
    }
}
