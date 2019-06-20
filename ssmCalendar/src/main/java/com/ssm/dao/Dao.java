package com.ssm.dao;

import com.ssm.model.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Dao {
    List<Event> getModelByEvent();
    boolean addModelByEevent(Event event);
    int getModelByEventId();
    boolean deleteModelByEvent(String event);
    int getModelByEventCount(String event);
}
