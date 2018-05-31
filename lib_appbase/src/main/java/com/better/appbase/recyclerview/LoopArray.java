package com.better.appbase.recyclerview;

import java.util.ArrayList;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: LoopArray.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/20 下午4:06
 *
 * Changes (from 2017/12/20)
 * -----------------------------------------------------------------
 * 2017/12/20 : Create LoopArray.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class LoopArray<T> extends ArrayList<T> {
    @Override
    public T get(int index) {
        return super.get(index % size());
    }
}
