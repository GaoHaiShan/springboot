package com.ssm.service.impl;

import com.ssm.dao.Dao;
import com.ssm.model.Event;
import com.ssm.service.IEventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EventServiceImpl implements IEventService {
    @Resource
    private Dao dao;
    @Override
    public List<Event> getModelByEvent(){

        return dao.getModelByEvent();
    }

   @Override
   public Boolean addModelByEvent(Event event) {
    return dao.addModelByEevent(event);
    }

   @Override
    public int getModelByEventId() {
        return dao.getModelByEventId();
    }

    @Override
    public Boolean deleteModelByEvent(String event) {
        return dao.deleteModelByEvent(event);
    }

    @Override
    public int getModelByEventCount(String event) {
        return dao.getModelByEventCount(event);
    }
}
