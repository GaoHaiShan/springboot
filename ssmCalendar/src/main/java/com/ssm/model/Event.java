package com.ssm.model;


import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Service
public class Event {
    private int id;
    @NotNull(message = "开始时间不能为空")
    private String startdate;
    @NotNull(message = "结束时间不能为空")
    private String enddate;
    @NotNull(message = "事件不能为空")
    @Size(min=2,max=200,message = "事件大于2个字符")
    private String event;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getEvent() {
        return event;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setEvent(String event) {
        if(event!="")this.event = event;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    @Override
    public String toString() {
        return event;
    }
}
