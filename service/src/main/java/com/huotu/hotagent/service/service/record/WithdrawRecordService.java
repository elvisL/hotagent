package com.huotu.hotagent.service.service.record;

import com.huotu.hotagent.service.entity.record.WithdrawRecord;
import org.springframework.data.domain.Page;

/**
 * 提现管理
 * Created by cwb on 2016/2/22.
 */
public interface WithdrawRecordService {
     Page<WithdrawRecord> searchRecords(int pageNo);

    WithdrawRecord getSpecifiedRecord(Long id);
}
