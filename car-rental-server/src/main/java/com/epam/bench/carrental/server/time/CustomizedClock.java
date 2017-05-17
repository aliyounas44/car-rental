package com.epam.bench.carrental.server.time;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class CustomizedClock {

    private Clock clock = Clock.systemDefaultZone();
    
    public LocalDateTime now() {
	return LocalDateTime.now(clock);
    }
    
    public void setClock(Clock clock) {
	this.clock = clock;
    }
    
    public void reset() {
	this.clock = Clock.systemDefaultZone();
    }
}
