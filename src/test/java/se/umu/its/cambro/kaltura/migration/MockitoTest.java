package se.umu.its.cambro.kaltura.migration;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public abstract class MockitoTest {


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
