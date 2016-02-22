package com.huotu.hotagent.service.service.record.impl;

import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.entity.record.WithdrawRecord;
import com.huotu.hotagent.service.repository.record.WithdrawRepository;
import com.huotu.hotagent.service.service.record.WithdrawRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 提现管理
 * Created by cwb on 2016/2/22.
 */
@Service
public class WithdrawRecordServiceImpl implements WithdrawRecordService {

    @Autowired
    WithdrawRepository withdrawRepository;

    @Override
    public Page<WithdrawRecord> searchRecords(int pageNo) {
        return withdrawRepository.findAll(new PageRequest(pageNo-1, SysConstant.DEFAULT_PAGE_SIZE,new Sort(Sort.Direction.ASC,"createTime")));
    }

    @Override
    public WithdrawRecord getSpecifiedRecord(Long id) {
        return withdrawRepository.findOne(id);
    }
}
