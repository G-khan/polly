package com.gokhana.polly.model.dto.poll


class PagedResponse<T> {
    var content: List<T>? = null
    var page = 0
    var size = 0
    var totalElements: Long = 0
    var totalPages = 0
    var isLast = false

    constructor() {}
    constructor(content: List<T>?, page: Int, size: Int, totalElements: Long, totalPages: Int, last: Boolean) {
        this.content = content
        this.page = page
        this.size = size
        this.totalElements = totalElements
        this.totalPages = totalPages
        isLast = last
    }
}