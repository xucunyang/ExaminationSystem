package com.oranle.es.data.entity

enum class Role(val value: Int) {
    /**
     * 测评用户
     */
    Examinee(0),

    /**
     *  管理员
     */
    Manager(1),
    /**
     *  高级管理员
     */
    Root(2)

}