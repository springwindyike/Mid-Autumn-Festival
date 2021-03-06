/**
 * Copyright 2018 贝莱科技 http://www.bitflash.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.bitflash.service.impl;

import cn.bitflash.dao.PriceLinechartDao;
import cn.bitflash.entity.PriceLinechartEntity;
import cn.bitflash.service.PriceLinechartService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("priceLinechartService")
public class PriceLinechartServiceImpl extends ServiceImpl<PriceLinechartDao, PriceLinechartEntity> implements PriceLinechartService {

    @Override
    public PriceLinechartEntity selectPriceLinechart() {
        return baseMapper.selectPriceLinechart();
    }

    @Override
    public PriceLinechartEntity selectPriceUs() {
        return baseMapper.selectPriceUs();
    }

    @Override
    public PriceLinechartEntity selectPriceCny() {
        return baseMapper.selectPriceCny();
    }

    @Override
    public List<PriceLinechartEntity> apilineChart(String startd , String yester){
        return baseMapper.apilineChart(startd,yester);
    }
}
