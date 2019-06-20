<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>演示：FullCalendar应用——拖动与实时保存</title>
    <meta name="keywords" content="日程安排,FullCalendar,日历,JSON,jquery实例">
    <meta name="description" content="在线演示FullCalendar拖动与保存日程事件的示例。">
    <link rel="stylesheet" type="text/css" href="/js3/css/main.css">
    <link rel="stylesheet" type="text/css" href="/js3/css/fullcalendar.css">
    <script src="/js3/jquerymin.js"></script>
	<script type="text/javascript" src="/js3/fancybox/11.js"></script>
	<link rel="stylesheet" type="text/css" href="/js3/fancybox/11.css" media="screen" />
 	<link rel="stylesheet" href="/js3/style.css" />
    <style type="text/css">
    #calendar{width:960px; margin:20px auto 10px auto}
    .fancy h3{height:30px; line-height:30px; border-bottom:1px solid #d3d3d3; font-size:14px}
    .fancy form{padding:10px}
    .fancy p{height:28px; line-height:28px; padding:4px; color:#999}
    .input{height:20px; line-height:20px; padding:2px; border:1px solid #d3d3d3; width:100px}
    .btn{-webkit-border-radius: 3px;-moz-border-radius:3px;padding:5px 12px; cursor:pointer}
    .btn_ok{background: #360;border: 1px solid #390;color:#fff}
    .btn_cancel{background:#f0f0f0;border: 1px solid #d3d3d3; color:#666 }
    .btn_del{background:#f90;border: 1px solid #f80; color:#fff }
    .sub_btn{height:32px; line-height:32px; padding-top:6px; border-top:1px solid #f0f0f0; text-align:right; position:relative}
    .sub_btn .del{position:absolute; left:2px}

</style>
 <script src="/js3/js/11.js"></script>
<script type="text/javascript">
$(function(){
	$('#calendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		editable: true,
		dragOpacity: {
			agenda: .5,
			'':.6
		},

        //进行数据获取
        slotEventOverlap:true,
        selectHelper: true,
        events:[
                <c:forEach items="${events}" var="event">
        	    {
        	            id  : '<c:out value="${event.id}"/>',
			         title  : '<c:out value="${event.event}"/>',
                     start  : '<c:out value="${event.startdate}"/>',
                     end    : '<c:out value="${event.enddate}"/>',
			    },
                </c:forEach>
        	],
        eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) {
            $.post("do.php?action=drag",{id:event.id,daydiff:dayDelta,minudiff:minuteDelta,allday:allDay},function(msg){
                if(msg!=1){
                    alert(msg);
                    revertFunc();
                }
            });
        },
        eventResize: function(event,dayDelta,minuteDelta,revertFunc) {
            $.post("do.php?action=resize",{id:event.id,daydiff:dayDelta,minudiff:minuteDelta},function(msg){
                if(msg!=1){
                    alert(msg);
                    revertFunc();
                }
            });
        },

        selectable: true,

        select: function( startDate, endDate, allDay, jsEvent, view ){
            var start1 =$.fullCalendar.formatDate(startDate,'yyyy-MM-dd');
            var end1 =$.fullCalendar.formatDate(endDate,'yyyy-MM-dd');
            $.fancybox({
                'href':'#adddivtest',
            });
            $("#startdate").val(start1);
            $("#enddate").val(end1);

        },
        eventClick: function(calEvent, jsEvent, view) {
            alert('Event: ' + calEvent.title);
            alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
            alert('View: ' + view.name);

            // change the border color just for fun


        },

        dayClick: function(date, allDay, jsEvent, view) {
            var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd');
            $.fancybox({
                'href':'#adddivtest',
            });
            $("#startdate").val(selDate);
            $("#enddate").val(selDate);
            $(this).css('border-color', 'red');
        },
    });
});
</script>
    <script>
        function submit1() {
            var url = "/test1/delete";
            var a = document.getElementById("event").value;
            window.location.href=url+"/?event="+a;
        }</script>
</head>

<body>
<form action="/test1/delete/" style="margin-top: 50px;margin-left: 200px;" method="get">
    <input type="text" class="input" name="event" id="event3" style="width:320px" placeholder="输入删除日程信息..."/>
    <input type="submit" class="btn btn_ok" value="删除"/>
</form>
	<div style="display:none;">
    <div id="adddivtest">
    <h3>编辑事件</h3>
    <form id="add_form" action="/test1/add/" method="get">
    <p>日程内容：<input type="text" class="input" name="event" id="event" style="width:320px"  value=""></p>
        <p>开始时间：<input type="date" class="input datepicker" name="startdate" id="startdate" value="" ></p>
       <p> 结束时间：<input type="date" class="input datepicker" name="enddate" id="enddate" value="" ></p>
     <p>
    <div class="sub_btn">
    	<span class="del"><input type="button" class="btn btn_del" id="del_event" value="删除" onclick="submit1()">
    </span>
    <input type="submit" class="btn btn_ok" value="确定">
    <input type="button" class="btn btn_cancel" value="取消" onClick="$.fancybox.close()"></div>
    </form>
    </span>
    </p>
    </form>
</div>
	</div>

<div id="main" style="width:1060px">
   <h2 class="top_title"><a href="http://www.helloweba.com/view-blog-235.html">FullCalendar应用——拖动与实时保存</a></h2>
   <div id='calendar'></div>
   <div class="ad_76090"><script src="/js3/js/ad_js/bd_76090.js" type="text/javascript"></script></div><br/>
</div>
<div id="footer">
    <p>Powered by helloweba.com  允许转载、修改和使用本站的DEMO，但请注明出处：<a href="http://www.helloweba.com">www.helloweba.com</a></p>
</div>
<p id="stat"><script type="text/javascript" src="/js3/js/tongji.js"></script></p>
</body>
</html>
