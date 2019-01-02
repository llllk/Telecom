package com.powerhf.telecom.web.controller;

import com.powerhf.telecom.web.bean.Calllog;
import com.powerhf.telecom.web.service.CalllogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CalllogController {
    @Autowired
    private CalllogService calllogService;
    @RequestMapping("/queryData")
    public String queryData(String tel, String calltime, Model model) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("tel", tel);
        paraMap.put("calltime", calltime);

            //查询数据
        List<Calllog> calllogs = calllogService.queryDatas(paraMap);
        Calllog calllog = calllogs.get(0);

        model.addAttribute("calllogs", calllogs);
        model.addAttribute("calllog", calllog);
        //跳转页面
        return "view";
    }
}
