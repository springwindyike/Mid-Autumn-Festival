package cn.bitflash.controller;

import cn.bitflash.entity.PriceLinechartEntity;
import cn.bitflash.service.PriceLinechartService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author GAOYGUUO
 */
@RestController
public class PriceLinechartController {

    @Autowired
    private PriceLinechartService priceLinechartService;

    /**
     * selectById
     *
     * @return
     */
    @PostMapping("/inner/priceLinechart/selectById")
    public PriceLinechartEntity selectById(@RequestParam("id") String id) {
        PriceLinechartEntity entity = priceLinechartService.selectById(id);
        return entity;
    }

    /**
     * updateById
     *
     * @return
     */
    @PostMapping("/inner/priceLinechart/updateById")
    public boolean updateById(@RequestBody JSONObject json) throws Exception {
        PriceLinechartEntity entity = (PriceLinechartEntity) JSONObject.parseObject(json.toString(), PriceLinechartEntity.class);
        return priceLinechartService.updateById(entity);
    }

    /**
     * insert
     *
     * @return
     */
    @PostMapping("/inner/priceLinechart/insert")
    public boolean insert(@RequestBody JSONObject json) throws Exception {
        PriceLinechartEntity entity = (PriceLinechartEntity) JSONObject.parseObject(json.toString(), PriceLinechartEntity.class);
        return priceLinechartService.insert(entity);
    }

    /**
     * deleteById
     *
     * @return
     */
    @PostMapping("/inner/priceLinechart/deleteById")
    public boolean deleteById(@RequestParam("id") String id) throws Exception {
        return priceLinechartService.deleteById(id);
    }

    /**
     * selectLineChartByDate
     * @param after
     * @param yesterday
     * @return
     */
    @PostMapping("/inner/priceLinechart/selectLineChartByDate")
    public List<PriceLinechartEntity> selectLineChartByDate(@RequestParam("after") Date after, @RequestParam("yesterday") Date yesterday){
        List<PriceLinechartEntity> priceLinechartEntities = priceLinechartService.selectList(new EntityWrapper<PriceLinechartEntity>().between("rate_time", after, yesterday).orderBy("rate_time"));
        return priceLinechartEntities;
    }


    /**
     * selectLineChartYesterDayByDate
     * @param yesterday
     * @return
     */
    @PostMapping("/inner/priceLinechart/selectLineChartYesterDayByDate")
    public List<PriceLinechartEntity> selectLineChartYesterDayByDate(@RequestParam("yesterday") String yesterday){
        List<PriceLinechartEntity> priceLinechartEntitiesList = priceLinechartService.selectList(new EntityWrapper<PriceLinechartEntity>().eq("rate_time", yesterday));
        return priceLinechartEntitiesList;
    }

    /**
     *selectLineChartById
     */
    @PostMapping("/inner/priceLinechart/selectLineChartById")
    public PriceLinechartEntity selectLineChartById(@RequestParam("date")Date date){
        PriceLinechartEntity priceLinechartEntity = priceLinechartService.selectById(date);
        return priceLinechartEntity;
    }


    /**
     * selectPriceLinechart
     */
    @PostMapping("/inner/priceLinechart/selectPriceLinechart")
    public PriceLinechartEntity selectPriceLinechart(){
        PriceLinechartEntity priceLinechartEntity = priceLinechartService.selectPriceLinechart();
        return priceLinechartEntity;
    }

    @PostMapping("/inner/priceLinechart/selectPriceUs")
    public PriceLinechartEntity selectPriceUs(){
        PriceLinechartEntity priceLinechartEntity = priceLinechartService.selectPriceUs();
        return priceLinechartEntity;
    }

    @PostMapping("/inner/priceLinechart/selectPriceCny")
    public PriceLinechartEntity selectPriceCny(){
        PriceLinechartEntity priceLinechartEntity = priceLinechartService.selectPriceCny();
        return priceLinechartEntity;
    }

    /**
     * admin
     * apilineChart
     */
    @GetMapping("/inner/priceLinechart/apilineChart/{startd}/{yester}")
    public List<PriceLinechartEntity> apilineChart(@PathVariable String startd ,@PathVariable String yester){
        return priceLinechartService.apilineChart(startd,yester);
    }

    /**
     * admin
     * addlineChart
     */
    @PostMapping("/inner/priceLinechart/addlineChart")
    public Boolean addlineChart(@RequestBody PriceLinechartEntity entity){
        return priceLinechartService.insert(entity);
    }

}
