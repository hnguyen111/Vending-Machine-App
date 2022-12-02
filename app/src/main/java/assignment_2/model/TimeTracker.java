package assignment_2.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/** Responsible for tracking time/user inactivity **/
public class TimeTracker{

    private LocalDateTime lastActivity;
    private double threshold;

    public TimeTracker(){
        this.init();
    }

    /** Initialize last activity to being current date and time **/
    private void init(){
        this.lastActivity = LocalDateTime.now();
        this.threshold = 120; // Putting the threshold to 10 seconds for now for the sake of testing
    }

    /** Resets last activity to current date and time **/
    public LocalDateTime resetLastActivity(){
        this.lastActivity = LocalDateTime.now();
        return this.lastActivity;
    }

    /** Returns true if it passes the threshold, false if inactivity was longer than threshold **/
    public boolean checkInactivity(){
        LocalDateTime currentTime = LocalDateTime.now();
        if (ChronoUnit.SECONDS.between(this.lastActivity, currentTime) > threshold){
            System.out.println("User was logged out due to inactivity");
            return false;
        }
        return true;
    }

    /** Returns the date and time of last user activity **/
    public LocalDateTime getLastActivity(){
        return this.lastActivity;
    }

    /** Returns threshold for timeout **/
    public double getThreshold(){
        return this.threshold;
    }

    /** Sets threshold for timeout **/
    public void setThreshold(double newThreshold){
        this.threshold = newThreshold;
    }
}
