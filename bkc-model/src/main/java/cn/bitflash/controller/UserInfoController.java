package cn.bitflash.controller;

import cn.bitflash.bean.UserInfoBean;
import cn.bitflash.entity.UserInfoEntity;
import cn.bitflash.entity.UserMarketTradeEntity;
import cn.bitflash.service.UserInfoService;
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
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * selectById
     *
     * @return
     */
    @PostMapping("/inner/userInfo/selectById")
    public UserInfoEntity selectById(@RequestParam("id") String id) {
        UserInfoEntity entity = userInfoService.selectById(id);
        return entity;
    }

    /**
     * updateById
     *
     * @return
     */
    @PostMapping("/inner/userInfo/updateById")
    public boolean updateById(@RequestBody JSONObject json) throws Exception {
        UserInfoEntity entity = (UserInfoEntity) JSONObject.parseObject(json.toString(), UserInfoEntity.class);
        return userInfoService.updateById(entity);
    }

    /**
     * insert
     *
     * @return
     */
    @PostMapping("/inner/userInfo/insert")
    public boolean insert(@RequestBody JSONObject json) throws Exception {
        UserInfoEntity entity = (UserInfoEntity) JSONObject.parseObject(json.toString(), UserInfoEntity.class);
        return userInfoService.insert(entity);
    }

    /**
     * deleteById
     *
     * @return
     */
    @PostMapping("/inner/userInfo/deleteById")
    public boolean deleteById(@RequestParam("id") String id) throws Exception {
        return userInfoService.deleteById(id);
    }


    /**
     * selectByMobile
     *
     * @return
     */
    @PostMapping("/inner/userInfo/selectUserInfoByMobile")
    public UserInfoEntity selectUserInfoByMobile(@RequestParam("mobile") String mobile) {
        UserInfoEntity userInfoEntity = userInfoService.selectOne(new EntityWrapper<UserInfoEntity>().eq("mobile",mobile));
        return userInfoEntity;
    }


    @PostMapping("/inner/userinfo/selectUserInfoLikeCode")
    public List<UserInfoBean> selectUserInfoLikeCode(@RequestParam("code")String code){
        return userInfoService.selectUserInfoLikeCode(code);
    }

    @PostMapping("/inner/userinfo/selectUserInfoesLikeCode")
    public List<UserInfoEntity> selectUserInfoesLikeCode(@RequestParam("code")String code){
        return userInfoService.selectList(new EntityWrapper<UserInfoEntity>().like("invitation_code",code));
    }

    @PostMapping("inner/userInfo/updateList")
    public Boolean updateUserInfoList(@RequestBody List<UserInfoEntity> userInfoEntities){
        return userInfoService.updateBatchById(userInfoEntities);
    }

    @PostMapping("inner/userInfo/selectUserInfoByOldUid")
    public UserInfoEntity selectUserInfoByOldUid(@RequestParam("uid")String uid) {
        UserInfoEntity userInfoEntity = userInfoService.selectOne(new EntityWrapper<UserInfoEntity>().eq("old_uid",uid));
        return userInfoEntity;
    }

}
