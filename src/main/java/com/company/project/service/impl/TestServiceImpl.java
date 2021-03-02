package com.company.project.service.impl;

import com.company.project.dao.TestMapper;
import com.company.project.model.Test;
import com.company.project.service.TestService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2021/02/28.
 */
@Service
@Transactional
public class TestServiceImpl extends AbstractService<Test> implements TestService {

}
