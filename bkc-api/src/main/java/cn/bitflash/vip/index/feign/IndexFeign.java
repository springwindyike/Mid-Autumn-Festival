package cn.bitflash.vip.index.feign;

import cn.bitflash.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "bkc-model")
public interface IndexFeign {

    /**
     * user_login表
     */

    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    @PostMapping("/inner/userLogin/selectByMobile")
    UserLoginEntity selectUserLoginEntityByMobile(@RequestParam("id")String mobile);

    /**
     * 插入
     * @param userEntity
     * @return
     */
    @PostMapping("/inner/userLogin/insert")
    String insertUserLoginEntity(@RequestBody UserLoginEntity userEntity);

    /**
     * 更新
     * @param userEntity
     * @return
     */
    @PostMapping("/inner/userLogin/updateById")
    void updateUserLoginById(@RequestBody UserLoginEntity userEntity);


    /**
     * user_info表
     */

    /**
     * 更新
     * @param infoEntity
     * @return
     */
    @PostMapping("/inner/userInfo/updateById")
    Boolean updateUserInfoById(@RequestBody UserInfoEntity infoEntity);


    /**
     * 按上传日期返回最新的三张上传图片
     * selectSystemAdvertisement
     * @return
     */
    @PostMapping("/inner/systemAdvertisement/selectSystemAdvertisement")
    List<SystemAdvertisementEntity> selectSystemAdvertisement();

    /**
     * insert
     *
     * @return
     */
    @PostMapping("/inner/systemAdvertisement/insertSystemAdvertisement")
    Boolean insertSystemAdvertisement(@RequestParam("img")String img);
}
