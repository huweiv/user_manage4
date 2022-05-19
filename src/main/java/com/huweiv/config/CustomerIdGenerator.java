package com.huweiv.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.huweiv.util.IdGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName CustomerIdGenerator
 * @Description TODO
 * @CreateTime 2022/5/19 10:27
 */
@Component
public class CustomerIdGenerator implements IdentifierGenerator {
    @Override
    public Long nextId(Object entity) {
        return IdGenerator.generateId();
    }
}
