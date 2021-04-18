package proto.games.matchmaker.support.readers

import java.util.stream.Stream

interface Reader {
    fun readall(): Stream<String?>?
}