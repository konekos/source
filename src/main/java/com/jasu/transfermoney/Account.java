package com.jasu.transfermoney;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/*****************************************
 * @author hjs
 * @date 2020-02-28 19:26
 *****************************************/
@Getter
@Setter
public class Account {
    Long accountNo;
    Long money;
}
