package proto.games.matchmaker.domain.player;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Player implements Comparable<Player>{
    @EqualsAndHashCode.Exclude
    private String name;
    private String email;
    @EqualsAndHashCode.Exclude
    private Rank rank;
    private String nickName;
    @EqualsAndHashCode.Exclude
    private Position primaryPosition;
    @EqualsAndHashCode.Exclude
    private Position secondaryPosition;
    @EqualsAndHashCode.Exclude
    private Position tertiaryPosition;

    @Override
    public int compareTo(Player other) {
        return Integer.compare(other.getRank().getWeight(), rank.getWeight());
    }
}
