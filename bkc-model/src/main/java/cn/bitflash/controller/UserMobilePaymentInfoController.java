package cn.bitflash.controller;


import cn.bitflash.entity.UserPaymentMobileEntity;
import cn.bitflash.service.UserMobilePaymentInfoService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author GAOYGUUO
 */
@RestController
public class UserMobilePaymentInfoController {

    @Autowired
    private UserMobilePaymentInfoService userMobilePaymentInfoService;

    /**
     * selectById
     *
     * @return
     */
    @PostMapping("/inner/userMobilePaymentInfo/selectById")
    public UserPaymentMobileEntity selectById(@RequestParam("id") int id) {
        UserPaymentMobileEntity entity = userMobilePaymentInfoService.selectById(id);
        return entity;
    }

    /**
     * updateById
     *
     * @return
     */
    @PostMapping("/inner/userMobilePaymentInfo/updateById")
    public boolean updateById(@RequestBody JSONObject json) throws Exception {
        UserPaymentMobileEntity entity = (UserPaymentMobileEntity) JSONObject.parseObject(json.toString(), UserPaymentMobileEntity.class);
        return userMobilePaymentInfoService.updateById(entity);
    }

    /**
     * insert
     *
     * @return
     */
    @PostMapping("/inner/userMobilePaymentInfo/insert")
    public boolean insert(@RequestBody JSONObject json) throws Exception {
        UserPaymentMobileEntity entity = (UserPaymentMobileEntity) JSONObject.parseObject(json.toString(), UserPaymentMobileEntity.class);
        return userMobilePaymentInfoService.insert(entity);
    }

    /**
     * deleteById
     *
     * @return
     */
    @PostMapping("/inner/userMobilePaymentInfo/deleteById")
    public boolean deleteById(@RequestParam("id") String id) throws Exception {
        return userMobilePaymentInfoService.deleteById(id);
    }

    /**
     *selectPaymentByUidAndType
     * @return
     */
    @PostMapping("/inner/userMobilePaymentInfo/selectPaymentByUidAndType")
    public UserPaymentMobileEntity selectPaymentByUidAndType(@RequestParam("uid")String uid, @RequestParam("type") String type){
        UserPaymentMobileEntity userMobilePaymentInfoEntity = userMobilePaymentInfoService.selectOne(new EntityWrapper<UserPaymentMobileEntity>().eq("uid",uid).eq("type",type));
        return userMobilePaymentInfoEntity;
    }

    /**
     * selectPaymentsByUid
     * @param
     * @return
     */
    @PostMapping("/inner/userMobilePaymentInfo/selectPaymentsByUid")
    List<UserPaymentMobileEntity> selectPaymentsByUid(@RequestParam("uid")String uid){
        List<UserPaymentMobileEntity> userMobilePaymentInfoEntities = userMobilePaymentInfoService.selectList(new EntityWrapper<UserPaymentMobileEntity>().eq("uid",uid));
        return userMobilePaymentInfoEntities;
    }

}
