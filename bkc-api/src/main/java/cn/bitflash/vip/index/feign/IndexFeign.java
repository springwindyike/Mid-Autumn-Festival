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
    UserLoginEntity selectUserLoginEntityByMobile(@RequestParam("mobile")String mobile);

    /**
     * 插入
     * @param userEntity
     * @return
     */
    @PostMapping("/inner/userLogin/registerLogin")
    String registerLogin(@RequestBody UserLoginEntity userEntity);

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
     * insert
     *
     * @return
     */
    @PostMapping("/inner/systemAdvertisement/insertSystemAdvertisement")
    Boolean insertSystemAdvertisement(@RequestParam("img")String img);

    /**
     * 查询轮播图片
     * @return
     */
    @PostMapping("/inner/systemAppBanner/selectAppBanner")
    public List<SystemAppBannerEntity> selectAppBanner();
}
