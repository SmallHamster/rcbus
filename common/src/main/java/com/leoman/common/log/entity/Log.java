package com.leoman.common.log.entity;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2016/5/22.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    public String message();
}
