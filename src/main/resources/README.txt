1. Set properties in kaltura_migration.properties.

2. Run as: java -jar kaltura_migration.jar

A number of logs are created:
migrated.log - A log of all rows that has been migrated
migrated_error.log - A log of all rows that has been migrated, but still contains errors (such as id not found)
migration.log - A log containing messages from the application
migration.html - A HTML-file with visible information about the migration. Contains diff.
not_found_ids.log - A log that contains all the id's that wasn't found
unmigrated.log - A log that contains rows that matched certain criterias, but then wasn't migrated.
unmigrated_error.log - A log that contains rows that matched certain criterias, but then wasn't migrated due to error (such as id not found)