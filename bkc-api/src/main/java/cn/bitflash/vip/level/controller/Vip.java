package cn.bitflash.vip.level.controller;

import cn.bitflash.annotation.Login;
import cn.bitflash.entity.*;
import cn.bitflash.exception.RRException;
import cn.bitflash.utils.Common;
import cn.bitflash.utils.R;
import cn.bitflash.vip.level.feign.LevelFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/level")
@Api(value = "提升算力Con", tags = {"加入"})
public class Vip {

    @Autowired
    private LevelFeign levelFeign;

    @Login
    @PostMapping("showVipMess")
    @ApiOperation("显示当前vip等级额度信息")
    public R showVipMess(@RequestAttribute("uid") String uid) {
        UserInfoEntity userInfo = levelFeign.selectUserInfoByUid(uid);
        SystemVipEntity vipEntity = levelFeign.selectSystemVipById(userInfo.getVipLevel());
        Map<String, Object> map = new HashMap<>();
        //目前额度
        map.put("now_amount", vipEntity.getVipCash());
        //赠送比例
        BigDecimal npc = new BigDecimal(levelFeign.getVal("npc_unit_price"));
        //hlb赠送比例
        map.put("vip_give_rate",vipEntity.getHlbGiveRate()) ;
        //当前npc的单价
        map.put("npc_unit_price",npc);
        //算力区间
        BigDecimal min = new BigDecimal(vipEntity.getMinPower() * 100);
        BigDecimal max = new BigDecimal(vipEntity.getMaxPower() * 100);
        map.put("min_max", min.intValue() + "% ~ " + max.intValue() + "%");
        //当前档位的释放额度
        map.put("hlb_amount", vipEntity.getHlbAmount());
        return R.ok(map);
    }


    @Login
    @PostMapping("updateLevel")
    @ApiOperation("提升算力")
    public R updateVipLevel(@RequestAttribute("uid") String uid, @RequestParam("amountId") Integer amountId) {

        //1.验证 邀请码
        UserInfoEntity userInfo = levelFeign.selectUserInfoByUid(uid);
        if (userInfo.getIsInvited().equals(Common.UNAUTHENTICATION) || userInfo.getInvitationCode() == null) {
            return R.ok("没有邀请码用户");
        }

        //2.验证档位是否存在
        SystemVipEntity vipEntity = levelFeign.selectSystemVipById(amountId);
        if (vipEntity == null) {
            return R.error("升级档位信息有误");
        }

        //3.更新npc数量
        //npc单价
        BigDecimal npc = new BigDecimal(levelFeign.getVal("npc_unit_price"));
        //冻结时加入冻结的比例
        BigDecimal freezeRateNpc = new BigDecimal(levelFeign.getVal("freeze_rate_npc"));
        //npc扣除数量
        BigDecimal npcNum = new BigDecimal(vipEntity.getVipCash()).divide(npc, 0);
        //npc冻结数量
        BigDecimal frozenNpc = npcNum.multiply(freezeRateNpc);
        frozenNpc = new BigDecimal(frozenNpc.intValue());
        UserAssetsNpcEntity npcEntity = levelFeign.selectUserAssetsNpcById(uid);
        if (npcEntity.getAvailableAssets().compareTo(npcNum) == -1) {
            return R.error("当前升级所需的NPC数量不足");
        }
        npcEntity.setAvailableAssets(npcEntity.getAvailableAssets().subtract(npcNum));
        npcEntity.setFrozenAssets(npcEntity.getFrozenAssets().add(frozenNpc));
        npcEntity.setNpcPrice(npcEntity.getNpcPrice().add(new BigDecimal(vipEntity.getVipCash())));
        levelFeign.updateUserAssetsNpc(npcEntity);

        //4.更新userinfo信息
        //充值等级>当前等级
        if (amountId > userInfo.getVipLevel()) {
            //提升的vip等级>当前实际的vip等级
            userInfo.setVipLevel(amountId);
            if (vipEntity.getInitPower() > userInfo.getCurrentPower()) {
                //初始算力>实际算力
                userInfo.setCurrentPower(vipEntity.getInitPower());
            }
            levelFeign.updateUserInfo(userInfo);
        }

        //5.更新hlb冻结数量
        BigDecimal hlbAmount = new BigDecimal(vipEntity.getHlbAmount());
        UserAssetsHlbEntity hlbEntity = levelFeign.selectUserAssetsHlbById(uid);
        UserRelationEntity relation = levelFeign.selectRelationByUid(uid);
        //判断是否为老用户(有排点，但是没有冻结 hlb数量)
        boolean flag = false;
        if (hlbEntity.getTotelAssets().doubleValue() == 0 && relation != null) {
            flag = true;
        }
        hlbEntity.setTotelAssets(hlbEntity.getTotelAssets().add(hlbAmount));
        hlbEntity.setFrozenAssets(hlbEntity.getFrozenAssets().add(hlbAmount));
        levelFeign.updateUserAssetsHlb(hlbEntity);

        //6.加入hlb兑换历史
        UserHlbTradeHistoryEntity hlbTradeHistoryEntity = new UserHlbTradeHistoryEntity();
        hlbTradeHistoryEntity.setId("00" + RandomStringUtils.randomNumeric(6));
        hlbTradeHistoryEntity.setUid(uid);
        hlbTradeHistoryEntity.setTotalHlb(new BigDecimal(vipEntity.getHlbAmount()));
        hlbTradeHistoryEntity.setTotalNpc(npcNum);
        hlbTradeHistoryEntity.setFrozenNpc(frozenNpc);
        levelFeign.insertUserHlbTradeHistory(hlbTradeHistoryEntity);

        //7.判断老用户数据(已经排过点但是hlb的总收益为0)
        String invitCode = userInfo.getInvitationCode();
        UserInvitationCodeEntity pCode = levelFeign.selectInvitationCodeByCode(invitCode);
        if (flag) {
            //增加父邀请码人数
            UserInfoEntity f_info = levelFeign.selectUserInfoByUid(pCode.getUid());
            SystemVipEntity f_vip = levelFeign.selectSystemVipById(f_info.getVipLevel());
            if(f_vip.getMaxPower()>f_info.getCurrentPower()){
                Integer inviteNum = f_info.getUpgradeNum() + 1;
                Float f_upPower = ((int) (inviteNum / f_vip.getPeopleStep())) * f_vip.getPowerStep();
                //更新算力值
                f_info.setCurrentPower(f_info.getCurrentPower() + f_upPower);
                //更新最终人数
                Float finalNum = inviteNum % f_vip.getPeopleStep();
                f_info.setUpgradeNum(finalNum.intValue());
                levelFeign.updateUserInfo(f_info);
            }
        }
        //8.验证是否排点
        if (relation != null) {
            return R.ok();
        } else {
            //9.增加父邀请码人数
            UserInfoEntity f_info = levelFeign.selectUserInfoByUid(pCode.getUid());
            SystemVipEntity f_vip = levelFeign.selectSystemVipById(f_info.getVipLevel());
            if(f_vip.getMaxPower()>f_info.getCurrentPower()){
                Integer inviteNum = f_info.getUpgradeNum() + 1;
                Float f_upPower = ((int) (inviteNum / f_vip.getPeopleStep())) * f_vip.getPowerStep();
                //更新算力值
                f_info.setCurrentPower(f_info.getCurrentPower() + f_upPower);
                //更新最终人数
                Float finalNum = inviteNum % f_vip.getPeopleStep();
                f_info.setUpgradeNum(finalNum.intValue());
                levelFeign.updateUserInfo(f_info);
            }

        }

        //10.初始化邀请码
        UserInvitationCodeEntity codeEntity = new UserInvitationCodeEntity();
        codeEntity.setUid(uid);
        codeEntity.setCode(RandomStringUtils.randomAlphanumeric(8).toUpperCase());
        levelFeign.insertInvitation(codeEntity);
        //11.没有排点，进行排点
        List<UserRelationEntity> f_user = levelFeign.selectTreeNodes(pCode.getUid());
        //父类下所有的子类数量（包含父类）
        int size = f_user.size();
        String area = userInfo.getArea();
        switch (area) {
            case "L":
                if (size == 1) {
                    levelFeign.insertTreeNode(pCode.getUid(), uid, invitCode);
                } else if (size == 2) {
                    levelFeign.insertTreeNode(f_user.get(1).getUid(), uid, invitCode);
                } else if (size > 2) {
                    //筛选出左区第一个子节点
                    UserRelationEntity ue = f_user.stream().filter((u) -> u.getLft() == f_user.get(0).getLft() + 1).findFirst().get();
                    List<UserRelationEntity> child2_user = f_user.stream().filter((u) ->
                            u.getLft() >= ue.getLft() && u.getRgt() <= ue.getRgt()).collect(Collectors.toList());
                    if (child2_user.size() == 1) {
                        levelFeign.insertTreeNode(child2_user.get(0).getUid(), uid, invitCode);
                    } else if (child2_user.size() > 1) {
                        levelFeign.insertTreeNode(this.getChildNode(child2_user, new HashMap<>()), uid, invitCode);
                    }
                }
                break;
            case "R":
                if (size == 1) {
                    //等于1 = 没有左区，需要先排左区
                    throw new RRException("邀请码不正确");
                }
                //等于2代表直接父类下面开辟中区,或者左区下面只有一个点
                else if (size == 2) {
                    levelFeign.insertTreeNode(pCode.getUid(), uid, invitCode);
                } else if (size > 2) {
                    if (f_user.get(0).getRgt() == f_user.get(1).getRgt() + 1) {
                        //   o 情况1   实现 o
                        //  o             o o
                        // o             o
                        levelFeign.insertTreeNode(pCode.getUid(), uid, invitCode);
                        return R.ok();
                    }
                    //筛选出右区第一个子节点
                    UserRelationEntity ue = f_user.stream().filter((u) -> u.getLft() == f_user.get(1).getRgt() + 1).findFirst().get();
                    List<UserRelationEntity> child2_user = f_user.stream().filter((u) ->
                            u.getLft() >= ue.getLft() && u.getRgt() <= ue.getRgt()).collect(Collectors.toList());
                    if (child2_user.size() == 1) {
                        //    o  情况2  实现 o
                        //   o o           o  o
                        //                   o
                        levelFeign.insertTreeNode(child2_user.get(0).getUid(), uid, invitCode);
                    } else {
                        levelFeign.insertTreeNode(this.getChildNode(child2_user, new HashMap<>()), uid, invitCode);
                    }
                }
                break;
           /* case "R":
                if (size < 3 || f_user.get(0).getRgt() == f_user.get(1).getRgt() + 1) {
                    //等于2 = 没有左区，中区，需要先排左区和中区
                    throw new RRException("邀请码不正确");
                } else if (size == 3 && f_user.get(0).getRgt() != f_user.get(1).getRgt() + 1) {
                    levelFeign.insertTreeNode(pCode.getUid(), uid, invitCode, "R");
                } else if (size > 3) {

                    UserRelationEntity ue = f_user.stream().filter(u -> f_user.get(1).getRgt() + 1 == u.getLft()).findFirst().get();
                    UserRelationEntity ue2 = f_user.stream().filter(u -> f_user.get(0).getRgt() == u.getRgt() + 1).findFirst().get();
                    if (ue.equals(ue2)) {
                        //    o     情况：不存在右区  实现   o
                        //  o  o                        o  o  o
                        // o                          o
                        levelFeign.insertTreeNode(f_user.get(0).getUid(), uid, invitCode, "R");
                    } else {
                        //     o     情况：不存在右区  实现     o
                        //  o  o  o                       o  o  o
                        // o                                   o

                        List<UserRelationEntity> child2_user = f_user.stream().filter((u) ->
                                u.getLft() >= ue2.getLft() && u.getRgt() <= ue2.getRgt()).collect(Collectors.toList());
                        if(child2_user.size()==1){
                            levelFeign.insertTreeNode(child2_user.get(0).getUid(),uid,invitCode,"R");
                        }else{

                            levelFeign.insertTreeNode(this.getChildNode(child2_user, new HashMap<>()), uid, invitCode, "R");
                        }
                    }
                }
                break;
        }*/
        }
        return R.ok();
    }

    public String getChildNode(List<UserRelationEntity> p1_user, Map<String, UserRelationEntity> map) {
        /**
         * 寻找收益最高的下面的点
         * 实现原理：递归筛选，直到筛选出末尾节点
         */
        UserRelationEntity last_node = null;
        if (p1_user.size() > 1) {
            //左区比右区收益高/相等 添加到左区
            UserRelationEntity p2_user;
            //左节点
            p2_user = p1_user.stream().filter((u) -> u.getLft() == p1_user.get(0).getLft() + 1).findFirst().get();
            List<UserRelationEntity> p3_user = p1_user.stream().filter((u) -> (
                    u.getLft() >= p2_user.getLft()) && u.getRgt() <= p2_user.getRgt()).collect(Collectors.toList());
            if (p3_user.size() == 1) {
                map.put("key", p3_user.get(0));
            } else {
                this.getChildNode(p3_user, map);
            }
        }
        last_node = map.get("key");
        return last_node.getUid();
    }
}
