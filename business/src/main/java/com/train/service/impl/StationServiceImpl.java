package com.train.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.train.common.exception.BusinessException;
import com.train.common.exception.BusinessExceptionEnum;
import com.train.common.resp.PageResp;
import com.train.common.util.SnowUtil;
import com.train.domain.Station;
import com.train.mapper.StationMapper;
import com.train.req.StationQueryReq;
import com.train.req.StationSaveReq;
import com.train.resp.StationQueryResp;
import com.train.service.StationService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 冉船银
 * @description 针对表【station(车站)】的数据库操作Service实现
 * @createDate 2023-07-29 21:01:30
 */
@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station>
        implements StationService {
    private static final Logger LOG = LoggerFactory.getLogger(StationService.class);

    @Resource
    StationMapper stationMapper;

    public void save(StationSaveReq req) {
        DateTime now = DateTime.now();
        Station station = BeanUtil.copyProperties(req, Station.class);
        if (ObjectUtil.isNull(station.getId())) {

            // 保存之前，先校验唯一键是否存在
            Station stationDB = selectByUnique(req.getName());
            if (Objects.nonNull(stationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_STATION_NAME_UNIQUE_ERROR);
            }

            station.setId(SnowUtil.getSnowflakeNextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            stationMapper.insert(station);
        } else {
            station.setUpdateTime(now);
            stationMapper.updateById(station);
        }
    }

    public PageResp<StationQueryResp> queryList(StationQueryReq req) {
        LambdaQueryWrapper<Station> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Station::getId);


        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Station> stationList = stationMapper.selectList(wrapper);

        PageInfo<Station> pageInfo = new PageInfo<>(stationList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<StationQueryResp> list = BeanUtil.copyToList(stationList, StationQueryResp.class);

        PageResp<StationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    @Override
    public List<StationQueryResp> queryAll() {
        LambdaQueryWrapper<Station> where = new LambdaQueryWrapper<>();
        where.orderByAsc(Station::getNamePinyin);

        List<Station> stationList = stationMapper.selectList(where);
        return BeanUtil.copyToList(stationList, StationQueryResp.class);
    }

    private Station selectByUnique(String name) {
        LambdaQueryWrapper<Station> where = new LambdaQueryWrapper<>();
        where.eq(Station::getName, name);
        Station station = stationMapper.selectOne(where);
        if (Objects.nonNull(station)) {
            return station;
        } else {
            return null;
        }
    }
}




