package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/20 21:04
 * @description
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
