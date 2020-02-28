package com.example.demo.service;

import com.example.demo.App;
import com.example.demo.model.SqlBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class SqlLogicTest {
/*    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(SqlLogic.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    */
    @Autowired
    SqlLogic sqlLogic;

    @Test
    public void fillBean() throws Exception{
        String host = "localhost";
        String port = "3306";
        String user = "root";
        String pass = "root";
        SqlBean sqlBean = new SqlBean();
        sqlBean.setQuery("select * from province ");
        sqlLogic.fillBean( sqlBean, true, host, port, user, pass);
    }
}
