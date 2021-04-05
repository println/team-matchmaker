package proto.games.matchmaker.domain.player;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import proto.games.matchmaker.support.Converters;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlayerService {
    public Set<Player> listAllPlayers() {
        List<List<String>> tuples = readFile();
        return tuples.stream()
                .map(this::createPlayer)
                .collect(Collectors.toSet());
    }

    private Player createPlayer(List<String> tuple){
        return Player.builder()
                .name(tuple.get(0))
                .email(tuple.get(1))
                .nickName(tuple.get(2))
                .rank(Converters.eloToRank(tuple.get(3)))
                .primaryPosition(Converters.rotaToPosition(tuple.get(4).split(",")[0]))
                .secondaryPosition(Converters.rotaToPosition(tuple.get(4).split(",")[1]))
                .build();
    }

    private List<List<String>> readFile() {
        try {
            return readCsv();
        } catch (Exception e) {
            log.error("Error to read csv {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private List<List<String>> readCsv() throws Exception {
        URL resource = getClass().getClassLoader().getResource("data/players.csv");

        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(resource.getFile()));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }

        return records;
    }
}
