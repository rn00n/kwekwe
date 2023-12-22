package com.rn00n.kwekwe.core.commons.base

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.io.Serializable

data class BasePage<T>(
    val totalPages: Int,
    val totalElements: Long,
    val hasNext: Boolean,
    val isFirst: Boolean,
    val isLast: Boolean,
    val hasContent: Boolean,
    val content: List<T>,
    val pageable: Pageable,
    val number: Int,
    val size: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val isEmpty: Boolean
) : Serializable {
    companion object {
        fun <T> of(page: Page<T>): BasePage<T> {
            return BasePage(
                totalPages = page.totalPages,
                totalElements = page.totalElements,
                hasNext = page.hasNext(),
                isFirst = page.isFirst,
                isLast = page.isLast,
                hasContent = page.hasContent(),
                content = page.content,
                pageable = page.pageable,
                number = page.number,
                size = page.size,
                sort = page.sort,
                numberOfElements = page.numberOfElements,
                isEmpty = page.isEmpty
            )
        }
    }
}