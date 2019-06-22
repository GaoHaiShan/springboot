package controller.demo.controller;

/**
 * @author 海山
 *
 * @data 2019/5/17
 *
*/


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
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {

    @Resource
    private IService service;

    @RequestMapping(value = "/delete")
    public String Delete(@RequestParam("id")int id) {
        try {
            service.deleteModelByStudyname(id);
        }
        catch (Exception e)
        {
            System.out.println("出错了");
        }
        finally {
            return "redirect:/main/startView";
        }
    }

    @RequestMapping(value = "/tianjia")
    public String AddStudy(Study study) {
        try {
            study.setUsername(UserController.UserName);
            if (service.addModelByStudy(study)) {
                return "redirect:/main/startView";
            } else {
                return "redirect:/main/jilu1";
            }
        } catch (Exception E) {
            System.out.println("添加出错了");
            return "redirect:/main/jilu1";
        }
    }

    @RequestMapping("/jilu1") //添加新知识点
    public String NewJiLu(Model model) {
        List<String> types = service.getModelType(UserController.UserName);
        model.addAttribute("id" ,service.getModelById());
        model.addAttribute("types",types);
        return "jilu1";
    }

    @RequestMapping("/jilu/")//访问旧知识点
    public String SelectJiLu(@RequestParam("studyname") String studyname,
                             @RequestParam("type") String type1, Model model) {
        try {
            Study study = service.getModelByStudyTxt(studyname,type1,UserController.UserName);
            model.addAttribute("study",study);
            return "jilu";
        }catch (Exception e){
            return "redirect:/main/startView";
        }
    }

    @RequestMapping("/view")
    public String selectView(@RequestParam(value = "index",required = true,defaultValue = "0")String index,
                            @RequestParam(value = "type") String type,
                             Model model) {
       try {
           int[] i = new int[2];
           i[0] = Integer.parseInt(index);
            List<String> types = service.getModelType(UserController.UserName);
            List<Study> studys = service.getModelByStudy(UserController.UserName,type,i);
            model.addAttribute("type",type);
            model.addAttribute("types",types);
            model.addAttribute("studys",studys);
            model.addAttribute("view","view");
            model.addAttribute("index0",result(index,i[1])[0]);
            model.addAttribute("index",result(index,i[1])[1]);
      }catch (Exception e){System.out.println("出错了");}
        finally {
            return "main";
       }
    }

    @RequestMapping("/startView")
    public String selectView1(@RequestParam(value = "index",required = true,defaultValue = "0")String index, Model model) {
        try {
            int [] i = new int[2];
            i[0] = Integer.parseInt(index);
            List<String> types = service.getModelType(UserController.UserName);
            List<Study> studies = service.getModelAllByStudy(UserController.UserName,i);
            model.addAttribute("types",types);
            model.addAttribute("studys",studies);
            model.addAttribute("view","startView");
            model.addAttribute("index0",result(index,i[1])[0]);
            model.addAttribute("index",result(index,i[1])[1]);
            model.addAttribute("type","all");
        }catch (Exception e){System.out.println("出错了");}
        finally {
            return "main";
        }
    }

    @RequestMapping("/addType")
    public String addType(@RequestParam("type") String type){
       try {
           service.addType(UserController.UserName,type);
       }catch (Exception e){
           System.out.println("添加type出错了");
       }
       finally {
           return "redirect:/main/startView";
       }
    }

    @RequestMapping(value = "/addImage",method = RequestMethod.POST)
    public String addImage(@RequestParam("image") CommonsMultipartFile image, @RequestParam("id")String id) throws IOException {
        try {
           Study study = service.getModelByStudy1(Integer.parseInt(id));
           study.setUsername(UserController.UserName);
            service.addPhoto(image,study);
        }
        catch (Exception e){
            System.out.println("eorrs");
        }
        return "redirect:/main/startView";
    }

    @RequestMapping("/deleteImage")
    public String deleteImage(@RequestParam("id")int id){
        try {
            Study study = service.getModelByStudy1(id);
            study.setUsername(UserController.UserName);
            service.deleteModelImage(study);
       }catch (Exception e){
            System.out.println("删除出错");
        }
       finally {
            return "redirect:/main/startView";
       }
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
