package proto.games.matchmaker.domain.matchmaker.rank;

import lombok.Getter;
import proto.games.matchmaker.domain.player.Rank;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
public class RankTeam extends ArrayList<Rank> {
    private final int limit;

    public RankTeam(Rank baseRank) {
        limit = baseRank.getWeight() * 5;
    }

    public int getCurrentWeight() {
        return this.stream()
                .parallel()
                .collect(Collectors.summingInt(Rank::getWeight));
    }

    public int getRemainingSpace() {
        return limit - getCurrentWeight();
    }
}
