package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entities.ExamCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamCategoryMapper extends BaseMapper<ExamCategory> {
}
