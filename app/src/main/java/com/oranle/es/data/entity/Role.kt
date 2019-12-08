package com.oranle.es.data.entity

enum class Role(val value: Int) {
    /**
     *  高级管理员
     */
    Root(0),
    /**
     *  管理员
     */
    Manager(1),
    /**
     * 测评用户
     */
    Examinee(2)
}