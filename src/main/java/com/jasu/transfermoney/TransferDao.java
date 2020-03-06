package com.jasu.transfermoney;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/*****************************************
 * @author hjs
 * @date 2020-02-28 19:28
 *****************************************/
@Mapper
public interface TransferDao {

    @Select("select * from t_account where accountNo = #{accountNo}")
    Account get(Long accountNo);

    @Update("update t_account set money= amt - #{amount} where accountNo = #{accountNo} and amt> #{amount}")
    int update(Long accountNo, Long amount);

    @Insert("insert into t_record" +
            " values (#{record.recordNo},#{record.fromAccountNo},#{record.toAccountNo},#{record.amount})")
    int saveRecord(Record record);
}
