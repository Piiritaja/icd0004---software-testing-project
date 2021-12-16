package ee.taltech.weathermap.model.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    public void shouldReturnCoordinatesInCorrectFormat(){
        Coordinates dto = Coordinates.builder().lat(1.11f).lon(179).build();

        String actual = dto.getFormatted();

        assertEquals("1.11,179.00", actual);
    }

}