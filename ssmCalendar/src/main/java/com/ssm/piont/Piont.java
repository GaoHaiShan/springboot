package com.ssm.piont;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class Piont {

    @Pointcut("execution(* com.ssm.controller.EventController.AddEvent(..)))")
    public void AddEvent(){}
   @AfterReturning("AddEvent()")
    public void ShowSelect() {
       System.out.println("添加一条记录");
   }


   @Pointcut("execution(* com.ssm.controller.EventController.DeleteEvent(..))")
    public void DeleteEvent(){}
    @AfterReturning("DeleteEvent()")
    public void xiancheng1(){
        System.out.println("删除一条记录");
    }


    @Pointcut("execution(* com.ssm.controller.EventController.ShowDrag(..))")
    public void ShowDrag(){}
    @AfterReturning("ShowDrag()")
    public void xiancheng2(){
        System.out.println("返回查询结果");
    }
}
