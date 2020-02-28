package com.example.demo.controller;

import com.example.demo.model.SqlBean;
import com.example.demo.service.SqlLogic;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sql")
public class SQLConsoleController {

    private SqlLogic sqlLogic;

    SQLConsoleController(SqlLogic sqlLogic) {
        this.sqlLogic = sqlLogic;
    }

    public void queryDb(@RequestBody SqlBean sqlBean) {
        sqlLogic.fillBean(sqlBean, true);
    }
}
