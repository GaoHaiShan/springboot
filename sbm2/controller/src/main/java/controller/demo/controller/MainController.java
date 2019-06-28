package controller.demo.controller;

/**
 * @author 海山
 *
 * @data 2019/5/17
 *
*/


import controller.demo.entity.Study;
import controller.demo.services.IService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {

    @Resource
    private IService service;

    private volatile int id;

    @RequestMapping(value = "/delete")
    public String Delete(@RequestParam("id")int id) throws IOException{
        Study study = null;
        try {
            study = service.getModelByStudy1(id);
            service.deleteModelByStudyname(study);
        }
        catch (Exception e)
        {
            System.out.println("出错了");
        }
        finally {
            return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
        }
    }

    @RequestMapping(value = "/tianjia")
    public String AddStudy(Study study) {
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

    @RequestMapping("/jilu1") //添加新知识点
    public String NewJiLu( @RequestParam(value = "myUsername") String myUsername,
                           Model model) {
        List<String> types = service.getModelType(myUsername);
        id = service.getModelById();
        model.addAttribute("id" ,id);
        model.addAttribute("types",types);
        model.addAttribute("username",myUsername);
        return "jilu1";
    }

    @RequestMapping("/jilu/")//访问旧知识点
    public String SelectJiLu(@RequestParam("studyname") String studyname,
                             @RequestParam("type") String type1,
                             @RequestParam(value = "myUsername") String myUsername,
                             @RequestParam(value = "otherUserName",required = true,defaultValue = "") String otherUserName,
                             Model model) throws IOException {
        try {
            boolean f;
            if(otherUserName.equals("")){
                otherUserName = myUsername;
            }
            if(myUsername.equals(otherUserName)){
                f = false;
            } else{
                f = true;
            }
            Study study = service.getModelByStudyTxt(studyname,type1,otherUserName);
            id = study.getId();
            model.addAttribute("study",study);
            model.addAttribute("f",f);
            return "jilu";
        }catch (Exception e){
            return "redirect:/main/startView/?username="+ URLEncoder.encode(otherUserName,"UTF-8");
        }
    }

    @RequestMapping("/view")
    public String selectView(@RequestParam(value = "index",required = true,defaultValue = "0")String index,
                            @RequestParam(value = "type") String type,
                             @RequestParam(value = "myUsername") String myUsername,
                             @RequestParam(value = "otherUsername",required = true,defaultValue = "") String otherUserName,
                             Model model) {
       try {
           int[] i = new int[2];
           boolean f;
         System.out.println(myUsername+"："+otherUserName);
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
            List<Study> studys = service.getModelByStudy(otherUserName,type,i,f);
            model.addAttribute("myUsername",myUsername);
            model.addAttribute("type",type);
            model.addAttribute("types",types);
            model.addAttribute("studys",studys);
            model.addAttribute("view","view");
            model.addAttribute("index0",result(index,i[1])[0]);
            model.addAttribute("index",result(index,i[1])[1]);
           model.addAttribute("username",otherUserName);
           model.addAttribute("f",f);
      }catch (Exception e){System.out.println("出错了");}
        finally {
            return "main";
        }
    }

    @RequestMapping("/startView")
    public String selectView1(@RequestParam(value = "index",required = true,defaultValue = "0")String index,
                              @RequestParam(value = "myUsername")String myUsername,
                              @RequestParam(value = "otherUsername",required = true,defaultValue = "")String otherUsername
                             , Model model) {
        try {
            boolean f;
            int[] i = new int[2];
            i[0] = Integer.parseInt(index);
             if(otherUsername.equals("")){
               otherUsername = myUsername;
         }
            if (myUsername.equals(otherUsername) == false) {
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
        }catch (Exception e){System.out.println("出错了");}
       finally {
            return "main";
        }
    }
    @RequestMapping("/addType")
    public String addType(@RequestParam("type") String type,@RequestParam("myUsername")String myUsername) throws IOException{
       try {
           service.addType(myUsername,type);
       }catch (Exception e){
           System.out.println("添加type出错了");
       }
       finally {
           return "redirect:/main/startView/?username="+ URLEncoder.encode(myUsername,"UTF-8");
       }
    }

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

    @RequestMapping("/deleteImage")
    public String deleteImage(@RequestParam("id") int id) throws IOException{
        Study study=null;
        try {
            study = service.getModelByStudy1(id);
            service.deleteModelImage(study);
        }catch (Exception e){
            System.out.println("删除出错");
        }
       finally {
            return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(study.getUsername(),"UTF-8");
       }
    }
    @RequestMapping("/mymain")
    public String getMyStudy(@RequestParam("myUsername")String myUsername) throws IOException {
        return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(myUsername,"UTF-8");
    }
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
