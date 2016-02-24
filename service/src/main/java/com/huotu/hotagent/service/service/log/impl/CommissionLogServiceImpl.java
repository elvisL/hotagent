package com.huotu.hotagent.service.service.log.impl;

import com.huotu.hotagent.common.constant.StringConstant;
import com.huotu.hotagent.common.utils.StringUtil;
import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.model.LogSearch;
import com.huotu.hotagent.service.repository.log.CommissionLogRepository;
import com.huotu.hotagent.service.service.log.CommissionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 2016/2/24.
 */
@Service
public class CommissionLogServiceImpl implements CommissionLogService{
    @Autowired
    private CommissionLogRepository commissionLogRepository;
    @Override
    public Page<CommissionLog> findAll(int pageIndex, int pageSize, LogSearch logSearch) {
        Specification<CommissionLog> specification = ((root,query,cb)->{
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtils.isEmpty(logSearch.getAgentName())){
                predicates.add(cb.like(root.get("agent").get("name").as(String.class),"%" + logSearch.getAgentName() + "%"));
            }
            /*if(!StringUtils.isEmpty(logSearch.getSupportName())){
                predicates.add(cb.like(root.get("support").get("name").as(String.class),"%" + logSearch.getSupportName() + "%"));
            }*/
            if(!StringUtils.isEmpty(logSearch.getCustomerName())){
                predicates.add(cb.like(root.get("customer").get("name").as(String.class),"%" + logSearch.getCustomerName() + "%"));
            }
            if (!StringUtils.isEmpty(logSearch.getBeginTime())) {
                Date beginDate = StringUtil.DateFormat(logSearch.getBeginTime(), StringConstant.DATE_PATTERN);
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), beginDate));
            }
            if (!StringUtils.isEmpty(logSearch.getEndTime())) {
                LocalDate endDate = LocalDate.parse(logSearch.getEndTime(), DateTimeFormatter.ofPattern(StringConstant.DATE_PATTERN));
                endDate = endDate.plusDays(1);
                Date endTime = Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(endDate );
                predicates.add(cb.lessThan(root.get("createTime").as(Date.class), endTime));
            }
            if(logSearch.getAgent() > 0){
                predicates.add(cb.equal(root.get("agent").get("id").as(Integer.class), logSearch.getAgent()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        return commissionLogRepository.findAll(specification,new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "id")));
    }
}
