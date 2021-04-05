package proto.games.matchmaker.domain.matchmaker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import proto.games.matchmaker.domain.player.Player;
import proto.games.matchmaker.domain.player.Rank;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Team {
    private final List<Player> players;
    private final Rank tierRank;

    public int getCurrentWeight() {
        return this.players.stream()
                .parallel()
                .map(Player::getRank)
                .collect(Collectors.summingInt(Rank::getWeight));
    }

    public int getWeaknessLevel() {
        return getCurrentWeight() - tierRank.getWeight() * 5;
    }
}
