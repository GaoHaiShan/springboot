package com.ssm.controller;

import com.ssm.model.Event;
import com.ssm.service.IEventService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/test1")
public class EventController {
    @Resource
    private IEventService eventService;

    @RequestMapping("/drag1")
    public String ShowDrag(Model model){
        List<Event> events = this.eventService.getModelByEvent();
        model.addAttribute("events",events);
        return "drag1";
    }


    @RequestMapping("/add/")
    public String AddEvent(@Valid Event event, Errors errors){
        if(errors.hasErrors()||event.getEvent()== null)
        {
            return "redirect:/test1/drag1/";
        }
        else {
            event.setId(eventService.getModelByEventId()+1);
            eventService.addModelByEvent(event);
            return "redirect:/test1/drag1/";
        }
    }

    @RequestMapping("/delete/")
    public String DeleteEvent(@RequestParam("event") String event){
            if (event != "") {
                eventService.deleteModelByEvent(event);
            }
            return "redirect:/test1/drag1/";
    }
}
