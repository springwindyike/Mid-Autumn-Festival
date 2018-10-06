package cn.bitflash.vip.buy.controller;

import cn.bitflash.entity.BuyPoundageEntity;
import cn.bitflash.entity.UserAssetsNpcEntity;
import cn.bitflash.entity.UserMarketBuyEntity;
import cn.bitflash.utils.R;
import cn.bitflash.vip.buy.feign.BuyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

import static cn.bitflash.vip.buy.controller.BuyCommon.*;


@RestController
@RequestMapping("/buy/pendingPay")
public class PendingPay {

    @Autowired
    private BuyFeign feign;

    /**
     * --------点击已付款(待付款)-----------
     */
    @PostMapping("pay")
    @Transactional(propagation = Propagation.REQUIRED)
    public R payMoney(@RequestParam("id") String id) {
        UserMarketBuyEntity userMarketBuyEntity = feign.selectBuyById(id);
        //设置支付时间,user_buy订单状态
        userMarketBuyEntity.setPayTime(new Date());
        userMarketBuyEntity.setState(ORDER_STATE_STEP2);
        feign.updateBuyById(userMarketBuyEntity);
        return R.ok().put("code", SUCCESS);
    }

    /**
     * -------------点击取消(待付款)------------
     */
    @PostMapping("cancel")
    @Transactional(propagation = Propagation.REQUIRED)
    public R recall(@RequestParam("id") String id) {
        //查询uid
        UserMarketBuyEntity userMarketBuyEntity = feign.selectBuyById(id);
        //获取trade_poundage手续费，并返还，删除该信息
        BuyPoundageEntity buyPoundageEntity = feign.selectPoundageById(id);
        UserAssetsNpcEntity userAssetsNpcEntity = feign.selectAccountById(userMarketBuyEntity.getSellUid());
        userAssetsNpcEntity.setNpcAssets(buyPoundageEntity.getPoundage()+buyPoundageEntity.getQuantity()+userAssetsNpcEntity.getNpcAssets());
        feign.updateAccountById(userAssetsNpcEntity);
        feign.deletePoundage(id);
        //删除求购历史订单
        feign.deleteBuyById(id);
        return R.ok().put("code", SUCCESS);
    }
}