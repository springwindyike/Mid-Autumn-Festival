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

package cn.bitflash.dao;

import cn.bitflash.entity.PriceLinechartEntity;
import cn.bitflash.entity.SystemAppBannerEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gaoyuguo
 * @date 2018年9月22日
 */
public interface PriceLinechartDao extends BaseMapper<PriceLinechartEntity> {

    public PriceLinechartEntity selectPriceLinechart();

    public PriceLinechartEntity selectPriceUs();

    public PriceLinechartEntity selectPriceCny();

    List<PriceLinechartEntity> apilineChart(@Param("startd") String startd , @Param("yester")String yester);
}
