package com.douya.service;

import com.douya.Jpush.JSMSExample;
import com.douya.base.BaseService;
import com.douya.dto.*;
import com.douya.exception.RestException;
import com.douya.model.User;
import com.douya.security.DES;
import com.douya.security.RSA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by lb on 2017/12/4.
 */
@Service
@Transactional(readOnly = false)
public class UserService extends BaseService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @Value("2592000")
    private Integer validDate;





}