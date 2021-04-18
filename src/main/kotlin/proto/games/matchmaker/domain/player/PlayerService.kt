package proto.games.matchmaker.domain.player

import com.opencsv.CSVReader
import org.springframework.stereotype.Service
import proto.games.matchmaker.support.Converters
import java.io.FileReader

@Service
class PlayerService {
    fun listAllPlayers(): Set<Player> {
        val tuples = readFile()
        return tuples
            .map { tuple -> createPlayer(tuple) }
            .toSet()
    }

    private fun createPlayer(tuple: Array<String>): Player {
        return Player(
            tuple[0],
            tuple[1],
            tuple[2],
            Converters.eloToRank(tuple[3]),
            Converters.rotaToPosition(tuple[4].split(",").toTypedArray()[0]),
            Converters.rotaToPosition(tuple[4].split(",").toTypedArray()[1])
        )
    }

    private fun readFile(): List<Array<String>> {
        return try {
            readCsv()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Throws(Exception::class)
    private fun readCsv(): List<Array<String>> {
        val resource = javaClass.classLoader.getResource("data/players.csv")
        val records: List<Array<String>>;

        CSVReader(FileReader(resource.file)).use {
            records = it.iterator().asSequence().toList()
        }

        return records

    }
}