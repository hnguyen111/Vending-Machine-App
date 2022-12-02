package assignment_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import assignment_2.model.Model;
import assignment_2.model.TimeTracker;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTrackerTest {

    private Model model;
    private TimeTracker timeTracker;

    @BeforeEach
    public void init() throws IOException{
        this.model = new Model();
        this.timeTracker = new TimeTracker();
    }

    /* Checks that last activity is initialized to current time 
    (up to 2 second difference is allowed) */
    @Test
    public void initCurrentTime(){
        LocalDateTime currentTime = LocalDateTime.now();
        assertTrue(ChronoUnit.SECONDS.between(currentTime, this.timeTracker.getLastActivity()) < 2);
    }

    /* Check that checkInactivity passes if less than threshold */
    @Test
    public void trueCheckInactivity(){
        assertTrue(this.timeTracker.checkInactivity());
    }

    /* Check that threshold is initialized correctly */
    @Test
    public void testThreshold(){
        double expected = 120;
        this.timeTracker.setThreshold(120);
        double actual = this.timeTracker.getThreshold();
        assertEquals(expected, actual);
    }

    /* Check that if program sleeps longer than threshold, inactivity function will fail */
    @Test
    public void falseCheckInactivity() throws InterruptedException{
        this.timeTracker.setThreshold(1);
        Thread.sleep(2000);
        assertFalse(this.timeTracker.checkInactivity());
    }

    /* Check that you can reset last activity */
    @Test 
    public void resetActivity(){
        LocalDateTime expected = this.timeTracker.resetLastActivity();
        LocalDateTime actual = this.timeTracker.getLastActivity();
        assertEquals(expected, actual);
    }

}
