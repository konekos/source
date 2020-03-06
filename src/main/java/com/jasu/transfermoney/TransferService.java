package com.jasu.transfermoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*****************************************
 * @author hjs
 * @date 2020-02-28 19:41
 *****************************************/
@Service
public class TransferService {

    @Autowired
    TransferDao transferDao;

    Object tieLock = new Object();

    @Transactional(rollbackFor = RuntimeException.class)
    public void transferMoney(String recordNo, Long fromNo, Long toNo, Long amount) {
        Account fromAccount = transferDao.get(fromNo);
        if (fromAccount.getMoney() < amount) {
            throw new ServiceException("金额不足");
        }
        // record 唯一索引 保证幂等性
        transferDao.saveRecord(Record.builder().recordNo(recordNo).fromAccountNo(fromNo).toAccountNo(toNo).amount(amount).build());
        // 防止锁顺序性死锁
        if (fromNo > toNo) {
            transferDao.update(fromNo, amount);
            transferDao.update(toNo, -amount);
        } else if (fromNo < toNo) {
            transferDao.update(toNo, -amount);
            transferDao.update(fromNo, amount);
        }else {
            // 相等不合逻辑
        }
    }
}
