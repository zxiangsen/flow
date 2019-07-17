package com.douya.service;

import com.douya.base.BaseService;
import com.douya.model.Login;
import com.douya.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by lb on 2017/12/4.
 */
@Service
@Transactional(readOnly = true)
public class LoginService extends BaseService {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("2592000")
    private Integer validDate;


}