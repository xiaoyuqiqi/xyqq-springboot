package com.xyqq.service.impl;

import com.xyqq.model.Users;
import com.xyqq.dao.UsersMapper;
import com.xyqq.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xyqq
 * @since 2024-11-08
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {


    @Autowired
    private TransactionTemplate transactionTemplate;

    public void createUser() {

        /*
         * 使用 TransactionTemplate 编程式事务
         * TransactionTemplate 优势
         * 事务边界明确
         * 控制更细粒度
         * 无代理问题
         * 适用于嵌套事务和多线程环境
         * */
        transactionTemplate.executeWithoutResult(status -> {
            try {

                // 创建用户
                Users user = new Users();
                user.setUsername("test");
                user.setPassword("123456");
                user.setEnabled(true);
                save(user);

                if (true) throw new RuntimeException("模拟异常");
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }

}
