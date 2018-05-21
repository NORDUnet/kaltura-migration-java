# Kaltura-migration

Java-program for migrating from one Kaltura instance to another one.

### Code

The Java-program is built with Spring.

It fetches all columns that are defined as longtext, text or varchar with a size bigger than 100 (See MigrationDAO).

NOTE!: It will exclude columns that are primary keys or unique.

It then fetches the rows that contains text like (See MigrationDAO):

* entry_id
* entryid
* entryId
* play.umu.se
* kaltura

NOTE!: It will exclude columns that doesn't have any primary keys or uniques.
It will also exclude columns ending with "_REF", since in Sakai that may be a column that is a foreign key.

It then performs migration of the rows that match the given patterns. 
The migration is performed by a number of migrators (See playlist/migrators and entries/migrators).

Adding a migrator is quite simple, decide if it should extend PatternEntryMigrator (if Entry),
but make sure that you add it to EntryMigrators.

#### Code description

In the package "entry" is code related to entry migration.
The package "entry/migrators" contains the specific migrators and base classes.
PatternEntryMigrator is the base class for regexp replacing.

In the package "playlist" is code related to playlist migration.
The package "playlist/migrators" contains the specific playlist migrators and base classes.
PatternPlaylistMigrator is the base class for regexp replacing.

AppConfig - Spring configuration file

DbDriverSelector - Helper class for figuring out which DB-driver to use, parses jdbc-url.

DiffToHtmlExecutor - Helper class for performing diff on the row to be updated. Used for creating HTML-report.

KalturaMigrator - The main executor. Performs the steps described earlier.

MigrationConfiguration - Spring configuration file that reads in properties from properties-file.

MigrationDAO - Class that contains methods for database access.

Migrator - The main Spring class that contains main-method.

ResultPrinter - A class that contains methods for printing result to files.

RowToUpdate - A representation of a row in the database that possibly will be updated.

SpringJDBCConfiguration - Spring configuration file that sets up the access to the database.

TableInformation - A representation of a Table and Column combination containing information about primary keys.


### Build

```mvn clean package```

Will generate a kaltura-migration-app.zip that contains the application and properties-file.

To run you will also have to use kaltura_tools to generate csv-files of entries and playlists mapping.