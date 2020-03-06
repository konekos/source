package com.jasu.transfermoney;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/*****************************************
 * @author hjs
 * @date 2020-02-28 19:34
 *****************************************/
@Data
@Builder
public class Record {
    String recordNo;
    Long fromAccountNo;
    Long toAccountNo;
    Long amount;
}
