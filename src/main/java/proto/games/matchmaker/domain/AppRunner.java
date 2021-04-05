package proto.games.matchmaker.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import proto.games.matchmaker.domain.matchmaker.MatchResult;
import proto.games.matchmaker.domain.matchmaker.Matchmaker;
import proto.games.matchmaker.domain.player.Player;
import proto.games.matchmaker.domain.player.PlayerService;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppRunner implements CommandLineRunner {
    private final PlayerService playerService;
    private final Matchmaker matchmaker;

    @Override
    public void run(String... args) throws Exception {
        Collection<Player> players = playerService.listAllPlayers();
        MatchResult result = matchmaker.createTeams(players);
        log.info("Match Result {}", result);
    }
}
