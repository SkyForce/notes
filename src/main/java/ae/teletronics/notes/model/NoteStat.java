package ae.teletronics.notes.model;

import java.util.Map;

public interface NoteStat {
    String getId();
    Map<String, Integer> getWordsCount();
}
