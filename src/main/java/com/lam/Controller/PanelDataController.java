package com.lam.Controller;

import com.lam.Service.PanelDataService;
import com.lam.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class PanelDataController {

    @Autowired
    private PanelDataService panelDataService;
    //获取统计数据
    @PostMapping("/panelData")
    public Result getData() {
        return panelDataService.getData();
    }
}
