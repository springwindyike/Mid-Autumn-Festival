package cn.bitflash.vip.index.controller;

import cn.bitflash.entity.UseLoginEntity;
import cn.bitflash.utils.R;
import cn.bitflash.utils.RandomNumUtil;
import cn.bitflash.vip.index.feign.IndexFeign;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/index")
@Api(value = "注册Con", tags = {"用户app注册"})
public class RegisterApp {

    @Autowired
    private IndexFeign indexFeign;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("registerWeb")
    public R register2(@RequestParam String mobile, @RequestParam String pwd,
                       @RequestParam("invitationCode") String invitationCode, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        UseLoginEntity oldUser = indexFeign.selectUserLoginEntityByMobile(mobile);
        if (oldUser != null) {
            return R.error(501, "手机号已经存在");
        }

        String uid = indexFeign.selectUid();
        UseLoginEntity us = new UseLoginEntity();
        us.setMobile(mobile);
        us.setPassword(pwd);
        us.setUid(uid);
        us.setCreateTime(new Date());
        us.setSalt(RandomNumUtil.nBit(4));
        //初始化user_login表
        Boolean flag = indexFeign.insertUserLoginEntity(us);
        if (!flag) {
            indexFeign.delUserEntityByMbile(mobile);
            return R.error("注册失败");
        }
        logger.info("注册手机号");
        return R.ok("注册成功");
    }

    private String generateUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    private String getName() {
        String str = "用户";
        int max = 10000;
        int min = 1000;
        Random random = new Random();
        str += random.nextInt(max) % (max - min + 1) + min;
        return str;
    }


}
