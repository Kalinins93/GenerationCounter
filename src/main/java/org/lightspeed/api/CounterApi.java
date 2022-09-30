package org.lightspeed.api;

import org.lightspeed.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("counter")
public class CounterApi {
    @Autowired
    CounterService counterService;

    @PostMapping("{key}")
    public Long addCounter(@PathVariable String key) {
        return counterService.addCounter(key);
    }

    @PutMapping("{key}/increment")
    public Long incrementCounter(@PathVariable String key) {
        return counterService.incrementCounter(key);
    }

    @DeleteMapping("{key}")
    public void deleteCounter(@PathVariable String key) {
        counterService.deleteCounter(key);
    }

    @GetMapping("/total")
    public Long getTotalCounter() {
        return counterService.getTotalCounter();
    }

    @GetMapping("{key}")
    public Long getValueCounter(@PathVariable String key) {
        return counterService.getValueCounter(key);
    }

    @GetMapping("/list")
    public Set<String> getListCounterName() {
        return counterService.getListCounterName();
    }
}