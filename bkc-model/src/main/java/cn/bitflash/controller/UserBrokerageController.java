package cn.bitflash.controller;

import cn.bitflash.entity.SystemAppBannerEntity;
import cn.bitflash.entity.UserBrokerageEntity;
import cn.bitflash.entity.UserPaymentBankEntity;
import cn.bitflash.service.UserBrokerageService;
import cn.bitflash.service.UserCashAssetsJoinDictComputingPowerService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 */
@RestController
public class UserBrokerageController {

    @Autowired
    private UserBrokerageService userBrokerageService;


    /**
     * selectUserBrokerageById
     *
     * @return
     */
    @PostMapping("/inner/userBroker/selectUserBrokerageById")
    public UserBrokerageEntity selectUserBrokerageById(@RequestParam("id") Integer id) {
            UserBrokerageEntity entity = userBrokerageService.selectById(id);
        return entity;
    }

    @PostMapping("/inner/userBroker/updateUserBrokerage")
    public boolean updateUserBrokerage(@RequestBody JSONObject json) {
        UserBrokerageEntity entity = (UserBrokerageEntity) JSONObject.parseObject(json.toString(), UserBrokerageEntity.class);
        return userBrokerageService.updateById(entity);
    }

}
