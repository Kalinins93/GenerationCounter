package org.lightspeed.service;

import org.lightspeed.exception.ConflictException;
import org.lightspeed.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CounterService {
    private static final Logger logger = LoggerFactory.getLogger(CounterService.class);
    private ConcurrentMap<String, AtomicLong> storageCounterMap = new ConcurrentHashMap<>();

    public Long addCounter(String nameCounter) {
        if (!storageCounterMap.containsKey(nameCounter)) {
            Long initValue = 0L;
            storageCounterMap.putIfAbsent(nameCounter, new AtomicLong(initValue));
            logger.info("Added counter {}", nameCounter);
            return initValue;
        }
        logger.info("Counter {} already exists", nameCounter);
        throw new ConflictException();
    }

    public Long incrementCounter(String nameCounter) {
        if (storageCounterMap.containsKey(nameCounter)) {
            logger.info("Increment counter {}", nameCounter);
            return storageCounterMap.get(nameCounter).incrementAndGet();
        }
        logger.info("Counter {} isn't exists", nameCounter);
        throw new NotFoundException();
    }

    public Long getValueCounter(String nameCounter) {
        if (storageCounterMap.containsKey(nameCounter)) {
            Long value =  storageCounterMap.get(nameCounter).get();
            logger.info("Counter value {} = {} ", nameCounter, value);
            return value;
        }
        logger.info("Counter {} isn't exists", nameCounter);
        throw new NotFoundException();
    }

    public void deleteCounter(String nameCounter) {
        if (storageCounterMap.containsKey(nameCounter)) {
            storageCounterMap.computeIfPresent(nameCounter, (key, value) -> storageCounterMap.remove(key));
            logger.info("Counter {} is removed", nameCounter);
            return;
        }
        logger.info("Counter {} isn't exists", nameCounter);
        throw new NotFoundException();
    }

    public Long getTotalCounter() {
        Long totalValue = 0L;
        for (Map.Entry<String, AtomicLong> entry : storageCounterMap.entrySet()) {
            totalValue = totalValue + entry.getValue().get();
        }
        logger.info("Total counters = {}", totalValue);
        return totalValue;
    }

    public Set<String> getListCounterName() {
        Set<String> set =storageCounterMap.keySet();
        logger.info("List counter - {}", set);
        return set;
    }

}
