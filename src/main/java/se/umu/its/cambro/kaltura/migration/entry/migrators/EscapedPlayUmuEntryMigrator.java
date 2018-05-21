package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EscapedPlayUmuEntryMigrator extends PlayUmuEntryMigrator {

    @Override
    Pattern getPattern() {
        return Pattern.compile("play.umu.se\\\\/(media\\\\/t\\\\/|t\\\\/media\\\\/|t\\\\/|id\\\\/|edit\\\\/){1}(?<entryid>\\S{10})");
    }
}
