package com.douya.easemob;

import com.douya.easemob.api.impl.EasemobIMUsers;
import io.swagger.client.model.NewPassword;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by easemob on 2017/3/21.
 */
public class easemobUserUtils {

    private static final Logger logger = LoggerFactory.getLogger(easemobUserUtils.class);
    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    /**
     * 根据用户名名/手机号，密码注册环信
     * @param userName
     * @param password
     */
    public void createUser(String userName, String password) {
        RegisterUsers users = new RegisterUsers();
        User user = new User().username(userName).password(password);
        //User user1 = new User().username("aaa123456" + new Random().nextInt(500)).password("123456");
        users.add(user);
        //users.add(user1);
        Object result = easemobIMUsers.createNewIMUserSingle(users);
        //Assert.assertNotNull(result);
    }


    /**
     * 根据用户名获取环信的用户信息
     * @param userName
     */
    public Object getUserByName(String userName) {
        Object result = easemobIMUsers.getIMUserByUserName(userName);
        logger.info(result.toString());
        return result;
    }

    /*@Test
    public void gerUsers() {
        Object result = easemobIMUsers.getIMUsersBatch(5L, null);
        logger.info(result.toString());
    }*/

    /**
     *  根据用户名修改环信登录密码
     * @param userName
     */
    public void changePassword(String userName, String password) {
        NewPassword psd = new NewPassword().newpassword(password);
        Object result = easemobIMUsers.modifyIMUserPasswordWithAdminToken(userName, psd);
        logger.info(result.toString());
    }

    /*@Test
    public void getFriend() {
        String userName = "stringa";
        Object result = easemobIMUsers.getFriends(userName);
        logger.info(result.toString());
    }*/
}
