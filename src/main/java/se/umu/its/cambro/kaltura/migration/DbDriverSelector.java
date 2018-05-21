package se.umu.its.cambro.kaltura.migration;

class DbDriverSelector {

    static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    static final String MARIADB_DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";

    static String getDriverClass(String jdbcUrl) {
        String dbType = jdbcUrl.split(":")[1];

        switch (dbType) {
            case "mariadb":
                return MARIADB_DRIVER_CLASS_NAME;
            case "mysql":
                return MYSQL_DRIVER_CLASS_NAME;
            default:
                throw new RuntimeException("No known driver for: " + dbType);
        }
    }
}
