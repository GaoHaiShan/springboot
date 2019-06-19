package controller.demo.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * *@author 海山
 *
 * @data 2019-5-20
 */
public class Paging {
    public static List<Study> page(int index, List<Study> studies){
        int i = 0;
        List<Study> studies1 = new LinkedList<>();
        while (i< 10){
            if(i+index<studies.size()) {
                studies1.add(studies.get(i + index));
            }
           i++;
        }
        return studies1;
    }
}
