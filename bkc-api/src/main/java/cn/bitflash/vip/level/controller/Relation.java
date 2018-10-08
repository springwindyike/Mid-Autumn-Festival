package cn.bitflash.vip.level.controller;

import cn.bitflash.annotation.Login;
import cn.bitflash.entity.*;
import cn.bitflash.utils.R;
import cn.bitflash.vip.level.feign.LevelFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Hash;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/level")
@Api(value = "我的社区Con", tags = {"显示柱状图"})
public class Relation {

    @Autowired
    private LevelFeign levelFeign;

    @Login
    @PostMapping("getRelation")
    @ApiOperation("显示社区详情")
    public R getRelation(@RequestAttribute("uid") String uid) {
        UserAssetsHlbEntity hlbEntity =levelFeign.selectUserAssetsHlbById(uid);
        UserInfoEntity userInfo = levelFeign.selectUserInfoByUid(uid);
        Map<String,Object> map =new HashMap<>();
        //左区
        map.put("left",hlbEntity.getLftAchievement());
        //右区
        map.put("right",hlbEntity.getRgtAchievement());
        //释放额度
        SystemVipEntity systemVipEntity = levelFeign.selectSystemVipById(userInfo.getVipLevel());
        map.put("now_amount",systemVipEntity.getVipCash());
        //当前算力
        SystemPowerEntity systemPowerEntity = levelFeign.selectSystemPowerById(userInfo.getPowerLevel());
        map.put("now_power",systemPowerEntity.getPower()*100+"%");
        //HLB冻结数量
        map.put("hlb_frozen",hlbEntity.getFrozenAssets());
        return R.ok(map);
    }

    @Login
    @PostMapping("myPower")
    @ApiOperation("我的算力")
    public R myPower(@RequestAttribute("uid") String uid){
        UserInfoEntity userInfo = levelFeign.selectUserInfoByUid(uid);
        Map<String,Object> map =new HashMap<>();
        //当前算力
        SystemPowerEntity systemPowerEntity = levelFeign.selectSystemPowerById(userInfo.getPowerLevel());
        map.put("now_power",systemPowerEntity.getPower()*100+"%");
        //总邀请人数
        UserInvitationCodeEntity code = levelFeign.selectInvitationCodeByUid(uid);
        List<UserRelationEntity> reles = levelFeign.selectRelationByCode(code.getCode());
        map.put("counts",reles.size());
        return R.ok(map);
    }

    @Login
    @PostMapping("myHLB")
    @ApiOperation("我的HLB")
    public R myHLB(@RequestAttribute("uid") String uid){
        UserAssetsNpcEntity npcEntity = levelFeign.selectUserAssetsNpcById(uid);
        UserAssetsHlbEntity hlbEntity =levelFeign.selectUserAssetsHlbById(uid);
        Map<String,Object> map =new HashMap<>();
        //npc可用
        map.put("available_npc",npcEntity.getAvailableAssets());
        //HLB冻结数量
        map.put("hlb_frozen",hlbEntity.getFrozenAssets());
        //HLB可用量
        map.put("available_hlb",hlbEntity.getAvailableAssets());
        String npc = levelFeign.getVal("npc_unit_price");
        String giveRate = levelFeign.getVal("hlb_give_rate");
        map.put("npc_unit_price",npc);
        map.put("giveRate",giveRate);
        return R.ok(map);
    }


}