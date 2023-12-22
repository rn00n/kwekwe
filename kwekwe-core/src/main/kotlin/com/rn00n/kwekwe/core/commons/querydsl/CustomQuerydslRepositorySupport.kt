package com.rn00n.kwekwe.core.commons.querydsl

import com.querydsl.core.types.dsl.*
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.util.ObjectUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class CustomQuerydslRepositorySupport(
    domainClass: Class<*>?
) : QuerydslRepositorySupport(domainClass!!) {

    protected fun `in`(enumPath: EnumPath<*>, values: Array<Any?>): BooleanExpression? {
        if (!ObjectUtils.isEmpty(values)) {
            return enumPath.`in`(values as Nothing?)
        }
        return null
    }

    protected fun `in`(requester: EntityPathBase<*>, requesters: List<*>?): BooleanExpression {
        return requester.`in`(requesters as Nothing?)
    }

    protected fun `in`(requester: EntityPathBase<*>, requesters: Set<*>?): BooleanExpression {
        return requester.`in`(requesters as Nothing?)
    }

    protected fun `in`(name: StringPath, values: Array<String?>): BooleanExpression? {
        return if (ObjectUtils.isEmpty(values) || values.size == 0) {
            null
        } else name.`in`(*values)
    }

    protected fun `in`(numberPath: NumberPath<*>, values: Collection<*>): BooleanExpression? {
        return if (ObjectUtils.isEmpty(values) || values.size == 0) {
            null
        } else numberPath.`in`(values as Nothing?)
    }

    protected fun equals(enumPath: EnumPath<*>, value: Any?): BooleanExpression? {
        return if (!ObjectUtils.isEmpty(value)) {
            enumPath.eq(value as Nothing?)
        } else null
    }

    protected fun equals(path: StringPath, value: String): BooleanExpression? {
        return if (!ObjectUtils.isEmpty(value)) {
            path.eq(value)
        } else null
    }

    protected fun equals(numberPath: NumberPath<*>, value: Any?): BooleanExpression? {
        return if (!ObjectUtils.isEmpty(value)) {
            numberPath.eq(value as Nothing?)
        } else null
    }

    protected fun equals(entityPathBase: EntityPathBase<*>, `object`: Any?): BooleanExpression? {
        return if (!ObjectUtils.isEmpty(`object`)) {
            entityPathBase.eq(`object` as Nothing?)
        } else null
    }

    protected fun equals(date: DatePath<LocalDate?>, targetDate: LocalDate?): BooleanExpression? {
        return if (!ObjectUtils.isEmpty(targetDate)) {
            date.eq(targetDate)
        } else null
    }

    protected fun containsIgnoreCase(path: StringPath, value: String?): BooleanExpression? {
        return if (!Objects.isNull(value)) {
            path.containsIgnoreCase(value)
        } else null
    }

    protected fun containsIgnoreCase(stringTemplate: StringTemplate, value: String?): BooleanExpression? {
        return if (!Objects.isNull(value)) {
            stringTemplate.containsIgnoreCase(value)
        } else null
    }

    protected fun between(
        createdDateTime: DateTimePath<LocalDateTime?>,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?
    ): BooleanExpression {
        return createdDateTime.between(
            if (Objects.isNull(startDateTime)) LocalDateTime.MIN else startDateTime,
            if (Objects.isNull(endDateTime)) LocalDateTime.now() else endDateTime
        )
    }

    protected fun between(targetDate: DatePath<LocalDate?>, startDate: LocalDate?, endDate: LocalDate?): BooleanExpression {
        return targetDate.between(startDate, endDate)
    }

    protected fun notEquals(enumPath: EnumPath<*>, value: Any?): BooleanExpression? {
        return if (!ObjectUtils.isEmpty(value)) {
            enumPath.ne(value as Nothing?)
        } else null
    }
}