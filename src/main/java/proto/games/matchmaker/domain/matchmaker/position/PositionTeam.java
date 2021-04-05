package proto.games.matchmaker.domain.matchmaker.position;

import lombok.Getter;
import proto.games.matchmaker.domain.matchmaker.rank.RankTeam;
import proto.games.matchmaker.domain.player.Player;
import proto.games.matchmaker.domain.player.Position;
import proto.games.matchmaker.domain.player.Rank;

import java.util.*;

@Getter
public class PositionTeam {
    private final RankTeam rankTeam;
    private final List<Player> players;
    private final Set<Position> filledPositions;

    public PositionTeam(RankTeam rankTeam) {
        this.rankTeam = rankTeam;
        this.players = new ArrayList<>();
        this.filledPositions = new HashSet<>();
    }

    public Player getHead() {
        return players.get(0);
    }

    public Rank getHeadRank() {
        return rankTeam.get(0);
    }

    public boolean include(Player player) {
        if (isIncompatible(player)) {
            return false;
        }

        if (hasNoPosition(player)) {
            return false;
        }

        return insert(player);
    }

    public boolean populate(Player player) {
        if (isIncompatible(player)) {
            return false;
        }

        return insert(player);
    }

    private boolean isIncompatible(Player player) {
        if (players.contains(player)) {
            return true;
        }

        if (!rankTeam.contains(player.getRank())) {
            return true;
        }

        if (wrongRank(player)) {
            return true;
        }

        return false;
    }

    private boolean insert(Player player) {
        filledPositions.add(player.getPrimaryPosition());
        filledPositions.add(player.getSecondaryPosition());

        var inserted = players.add(player);
        Collections.sort(players);

        return inserted;
    }

    private boolean wrongRank(Player player) {
        long totalRank = rankTeam.stream()
                .filter(r -> r == player.getRank())
                .count();

        long totalPlayersByRank = players.stream()
                .filter(p -> p.getRank() == player.getRank())
                .count();

        if (totalRank <= totalPlayersByRank) {
            return true;
        }

        return false;
    }

    private boolean hasNoPosition(Player player) {
        Position primary = player.getPrimaryPosition();
        Position secondary = player.getSecondaryPosition();

        if(!filledPositions.contains(primary)){
            return false;
        }

        if(!filledPositions.contains(secondary)){
            return false;
        }

        return true;
    }

}
