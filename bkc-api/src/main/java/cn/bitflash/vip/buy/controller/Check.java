package cn.bitflash.vip.buy.controller;

import cn.bitflash.entity.UserMarketBuyEntity;
import cn.bitflash.utils.R;
import cn.bitflash.vip.buy.Bean.UserBuyBean;
import cn.bitflash.vip.buy.feign.BuyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static cn.bitflash.vip.buy.controller.BuyCommon.*;


@RestController
@RequestMapping("/buy/check")
public class Check {

    private TradeUtil tradeUtil;

    @Autowired
    private BuyFeign feign;

    /**
     * -------------查看交易详情-------------
     *
     * @param id 订单id
     * @return 订单详情
     */
    @PostMapping("buying")
    public R showBuyMessagePage(@RequestParam("id") String id) {
        //订单详情
        UserMarketBuyEntity userMarketBuyEntity = feign.selectBuyById(id);
        //判定订单不存在
        if (userMarketBuyEntity == null || !ORDER_STATE_PUBLISH.equals(userMarketBuyEntity.getState())) {
            return R.ok().put("code", TRADEMISS);
        }
        //获取手续费
        Map<String, Float> map = tradeUtil.poundage(id);
        return R.ok().put("code", SUCCESS).put("userBuy", userMarketBuyEntity).put("poundage", map.get("poundage") * 100)
                .put("totalMoney", map.get("totalMoney")).put("totalQuantity", map.get("totalQuantity"));
    }


    /**
     * --------------查看订单详情-------------------
     *
     * @param id 订单id
     * @return 订单详情
     */
    @PostMapping("order")
    public R checkOrder(@RequestParam("id") String id) {
       UserBuyBean userBuyBean = feign.checkOrder(id);
        if (userBuyBean == null) {
            return R.ok().put("code", "订单不存在");
        }
        Map<String, Float> map = tradeUtil.poundage(id);
        return R.ok().put("userBean", userBuyBean).put("totalQuantity", map.get("totalQuantity"))
                .put("price", map.get("price")).put("buyQuantity", map.get("buyQuantity"))
                .put("totalMoney", map.get("totalMoney"));
    }

}
