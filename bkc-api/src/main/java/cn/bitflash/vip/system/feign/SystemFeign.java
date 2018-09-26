package cn.bitflash.vip.system.feign;

import cn.bitflash.entity.AppStatusEntity;
import cn.bitflash.entity.PriceLinechartEntity;
import cn.bitflash.entity.UserLoginEntity;
import cn.bitflash.vip.system.entity.PriceChart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Api(value = "sys访问数据接口")
@FeignClient(value="bkc-model")
public interface SystemFeign {

    @ApiOperation(value = "根据手机号查询")
    @PostMapping("/inner/appStatus/selectById")
    AppStatusEntity selectAppStatusByAppId(@RequestParam("id")String appid);

    @ApiOperation(value = "根据手机号查询用户是否存在")
    @PostMapping("/inner/userLogin/selectByMobile")
    UserLoginEntity selectUserEntityByMobile(@RequestParam("mobile")String mobile);

    @ApiOperation(value = "查询区间时间内的价格")
    @PostMapping("/inner/priceLinechart/selectLineChartByDate")
    List<PriceChart> selectLineChartByDate(@RequestParam("after") Date after, @RequestParam("yesterday") Date yesterday);

    @ApiOperation(value = "根据主键查询数据")
    @PostMapping("/inner/priceLinechart/selectLineChartById")
    PriceLinechartEntity selectLineChartById(@RequestParam("date")Date date);
}

