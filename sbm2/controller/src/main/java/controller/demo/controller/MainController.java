package controller.demo.controller;



import controller.demo.entity.Study;
import controller.demo.services.IService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author 高海山
 *
 * demo实现思想：
 *     通过简单的业务逻辑实现各个知识点总结，以及用户之间用户借阅，
 * 制作期间，经过版本迭代，解决了三大模块之间的通信问题，通过 springcloud
 *可以对数据库模块进行分布式开发（主要思想：通过对用户名进行hash函数运算找到
 *对应的服务器进行访问），增加redis缓存机制减少数据库压力，主要是对各个学习知
 * 识点进行存储，存储方式：按函数名+用户+类型存储当前用户的数据 按照函数名+用
 * 户名+类型+public存储当前用户对外公开的知识点。进行修改时，使用的是，修改数据库
 * 后直接对redis所在缓存进行修改。
 *
 * 相对之前版本进行的优化：
 *     解决都用户同时进行添加后id重复问题，主要使用 volatile 是线程在提交添加的知识点
 * 的时候判断当前id是否与最大id相符。
 *     当redis没有对应数据需要进行数据库查询并且插入redis缓存时，通过加锁机制，避免
 * 插入redis相同信息导致系统崩溃
 *     减少成员的使用增加线程安全，主要是将成员变量变成局部变量使用，通过前台进行所需信息
 * 存储。
 *
 */
@Controller
@RequestMapping("/main")
public class MainController {

    @Resource
    private IService service;

    /**
     * 存储最大id
     */
    private volatile int id;

    /**
     *删除知识点
     */
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("id")int id) throws IOException{
        Study study = null;
        try {
            study = service.getModelByStudy1(id);
            service.deleteModelByStudyname(study);
            return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
        }
        catch (Exception e)
        {
            System.out.println("出错了");
            return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
        }
    }

    /**
     *
     *添加知识点
     */
    @RequestMapping(value = "/tianjia")
    public String addStudy(Study study) {
        try {
            if(id!=study.getId()){
                study.setId(id);
            }
            if (service.addModelByStudy(study)) {
                id = service.getModelById();
                return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
            } else {
                return "redirect:/main/jilu1/?myUsername"+URLEncoder.encode(study.getUsername(),"UTF-8");
            }
        } catch (Exception e) {
            System.out.println("添加出错了");
            return "jilu1";
        }
    }

    /**
     *
     *修改已有知识点
     */
    @RequestMapping(value = "/updateStudy")
    public String updateStudy(Study study) {
        try {
            if (service.updateModelByStudy(study.getId(),study.getStudytxt())) {
                return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
            } else {
                return "redirect:/main/jilu1/?myUsername"+URLEncoder.encode(study.getUsername(),"UTF-8");
            }
        } catch (Exception e) {
            System.out.println("添加出错了");
            return "jilu1";
        }
    }
    /**
     *访问新的知识点添加页面
     */
    @RequestMapping("/jilu1") //添加新知识点
    public String newJiLu( @RequestParam(value = "myUsername") String myUsername,
                           Model model) {
        List<String> types = service.getModelType(myUsername);
        id = service.getModelById();
        model.addAttribute("id" ,id);
        model.addAttribute("types",types);
        model.addAttribute("username",myUsername);
        return "jilu1";
    }

    /**
     *查看已有知识
     */
    @RequestMapping("/jilu/")//访问旧知识点
    public String selectJiLu(@RequestParam("id") int id,
                             @RequestParam(value = "myUsername") String myUsername,
                             @RequestParam(value = "otherUserName",required = true,defaultValue = "") String otherUserName,
                             Model model) throws IOException {
        try {
            boolean f;
            if("".equals(otherUserName)){
                otherUserName = myUsername;
            }
            if(myUsername.equals(otherUserName)){
                model.addAttribute("myUsername",myUsername);
                f = false;
            } else{
                model.addAttribute("myUsername",myUsername);
                f = true;
            }
            Study study = service.getModelByStudyTxt(id);
            model.addAttribute("older","1");
            model.addAttribute("study",study);
            model.addAttribute("f",f);
            return "jilu";
        }catch (Exception e){
            return "redirect:/main/startView/?username="+ URLEncoder.encode(otherUserName,"UTF-8");
        }
    }

    /**
     *访问某一类型数据
     */
    @RequestMapping("/view")
    public String selectView(@RequestParam(value = "index",required = true,defaultValue = "0")String index,
                            @RequestParam(value = "type") String type,
                             @RequestParam(value = "myUsername") String myUsername,
                             @RequestParam(value = "otherUsername",required = true,defaultValue = "") String otherUserName,
                             Model model) {
       try {
           int[] i = new int[2];
           boolean f;
           if(otherUserName.equals("")){
               otherUserName = myUsername;
           }
           if(myUsername.equals(otherUserName)){
               f = false;
           } else{
               f = true;
           }
           i[0] = Integer.parseInt(index);
            List<String> types = service.getModelType(otherUserName);
            List<Study> studys = service.   getModelByStudy(otherUserName,type,i,f);
            model.addAttribute("myUsername",myUsername);
            model.addAttribute("type",type);
            model.addAttribute("types",types);
            model.addAttribute("studys",studys);
            model.addAttribute("view","view");
            model.addAttribute("index0",result(index,i[1])[0]);
            model.addAttribute("index",result(index,i[1])[1]);
           model.addAttribute("username",otherUserName);
           model.addAttribute("f",f);
           return "main";
      }catch (Exception e){
           System.out.println("出错了");
           return "main";
       }
    }

    /**
     *访问全部数据
     */
    @RequestMapping("/startView")
    public String selectView1(@RequestParam(value = "index",required = true,defaultValue = "0")String index,
                              @RequestParam(value = "myUsername")String myUsername,
                              @RequestParam(value = "otherUsername",required = true,defaultValue = "")String otherUsername
                             , Model model) {
//        try {
            boolean f;
            int[] i = new int[2];
            i[0] = Integer.parseInt(index);
             if("".equals(otherUsername)){
               otherUsername = myUsername;
            }
            if(!myUsername.equals(otherUsername)) {
                f = true;
            } else {
                f = false;
            }
            List<String> types = service.getModelType(otherUsername);
            List<Study> studies = service.getModelAllByStudy(otherUsername, i, f);
            model.addAttribute("myUsername",myUsername);
            model.addAttribute("types", types);
            model.addAttribute("studys", studies);
            model.addAttribute("view", "startView");
            model.addAttribute("index0", result(index, i[1])[0]);
            model.addAttribute("index", result(index, i[1])[1]);
            model.addAttribute("type", "all");
            model.addAttribute("username", otherUsername);
            model.addAttribute("f", f);
            return "main";

//        }catch (Exception e){System.out.println("出错了");
//            return "main";
//        }
    }

    /**
     *
     * 增加类型
     */
    @RequestMapping("/addType")
    public String addType(@RequestParam("type") String type,@RequestParam("myUsername")String myUsername) throws IOException{
       try {
           service.addType(myUsername,type);
           return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(myUsername,"UTF-8");
       }catch (Exception e){
           System.out.println("添加type出错了");
           return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(myUsername,"UTF-8");
       }
    }

    /**
     *图片上传功能
     */
    @RequestMapping(value = "/addImage",method = RequestMethod.POST)
    public String addImage(@RequestParam("image") CommonsMultipartFile image, @RequestParam("id")String id) throws IOException {
        Study study=null;
        try {
           study = service.getModelByStudy1(Integer.parseInt(id));
            service.addPhoto(image,study);
        }
        catch (Exception e){
            System.out.println("eorrs");
        }
        return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
    }

    /**
     *
     * 删除图片
     */
    @RequestMapping("/deleteImage")
    public String deleteImage(@RequestParam("id") int id) throws IOException{
        Study study=null;
        try {
            study = service.getModelByStudy1(id);
            service.deleteModelImage(study);
            return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
        }catch (Exception e){
            System.out.println("删除出错");
            return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
        }
    }

    /**
     *返回自己主页
     */
    @RequestMapping("/mymain")
    public String getMyStudy(@RequestParam("myUsername")String myUsername) throws IOException {
        return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(myUsername,"UTF-8");
    }

    /**
     *
     * 计算上一页和下一页下标
     */
    private static int[] result(String index,int size){
        int a[] = new int[2];
        if( Integer.parseInt(index)< 10){
            a[0] = 0;
        }else {
            a[0] = Integer.parseInt(index)-10;
        }
        if (Integer.parseInt(index)+10>size){
            a[1] = Integer.parseInt(index);
        }
        else {
            a[1]=Integer.parseInt(index)+10;
        }
        return a;
    }
}
