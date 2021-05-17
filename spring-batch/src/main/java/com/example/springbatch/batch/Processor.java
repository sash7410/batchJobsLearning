package com.example.springbatch.batch;

import com.example.springbatch.models.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<User,User> {
    private static final Map<String,String> DEPT_NAMES=
            new HashMap<>();
    public Processor(){
        DEPT_NAMES.put("001","Technology");
        DEPT_NAMES.put("002","Operations");
        DEPT_NAMES.put("003","Accounts");
    }
    public User process (User user) throws Exception {
        String deptcode =user.getDept();
        String dept=DEPT_NAMES.get(deptcode);
        user.setDept(dept);
        user.setTime(new Date());
        System.out.println(
                String.format("Converted from [%s] to [%s]"
                        ,deptcode,dept));
        return user;
    }

}
