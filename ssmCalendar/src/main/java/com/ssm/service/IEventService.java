package com.ssm.service;

import com.ssm.model.Event;

import javax.transaction.Transactional;
import java.util.List;

public interface IEventService {
     List<Event> getModelByEvent();
     Boolean addModelByEvent(Event event);
     int getModelByEventId();
     Boolean deleteModelByEvent(String event);
     int getModelByEventCount(String event);
}
