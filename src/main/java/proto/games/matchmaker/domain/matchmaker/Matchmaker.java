package proto.games.matchmaker.domain.matchmaker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import proto.games.matchmaker.configuration.properties.DefaultProperties;
import proto.games.matchmaker.domain.matchmaker.position.PositionMatchmaker;
import proto.games.matchmaker.domain.matchmaker.rank.RankMatchmaker;
import proto.games.matchmaker.domain.matchmaker.rank.RankTeam;
import proto.games.matchmaker.domain.matchmaker.position.PositionTeam;
import proto.games.matchmaker.domain.player.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class Matchmaker {

    private final DefaultProperties defaultProperties;

    public MatchResult createTeams(Collection<Player> players) {
        PlayerBulk playerBulk = new PlayerBulk(players);
        List<PositionTeam> teams = organizeTeams(playerBulk);

        return generateResult(teams, playerBulk);
    }

    private MatchResult generateResult(List<PositionTeam> positionTeams, PlayerBulk playerBulk){
        List<Team> teams = positionTeams.stream()
                .map(t -> new Team(t.getPlayers(), playerBulk.getMedianRank()))
                .collect(Collectors.toList());
        return new MatchResult(teams, playerBulk.getPlayers());
    }

    private List<PositionTeam> organizeTeams(PlayerBulk playerBulk){
        List<RankTeam> rankTeams = clusterByRank(playerBulk);
        return integratePlayersByPositions(playerBulk, rankTeams);
    }

    private List<RankTeam> clusterByRank(PlayerBulk playerBulk) {
        RankMatchmaker rankMatchmaker = new RankMatchmaker(defaultProperties);
        return rankMatchmaker.match(playerBulk.clone());
    }

    private List<PositionTeam> integratePlayersByPositions(PlayerBulk playerBulk, List<RankTeam> rankTeams) {
        PositionMatchmaker positionMatchmaker = new PositionMatchmaker();
        return positionMatchmaker.match(playerBulk, rankTeams);
    }
}
