package se.umu.its.cambro.kaltura.migration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DbDriverSelectorTest {

    @Test
    public void shouldSelectMysql() {
        assertThat(DbDriverSelector.getDriverClass("jdbc:mysql:sadfsdafdsafdsaf")).isEqualToIgnoringCase(DbDriverSelector.MYSQL_DRIVER_CLASS_NAME);
    }

    @Test
    public void shouldSelectMariadb() {
        assertThat(DbDriverSelector.getDriverClass("jdbc:mariadb:sadfsdafdsafdsaf")).isEqualToIgnoringCase(DbDriverSelector.MARIADB_DRIVER_CLASS_NAME);
    }

    @Test
    public void shouldThrowException() {
        assertThatThrownBy(() -> DbDriverSelector.getDriverClass("jdbc:error:sadfsdafdsafdsaf")).isInstanceOf(RuntimeException.class);
    }

}