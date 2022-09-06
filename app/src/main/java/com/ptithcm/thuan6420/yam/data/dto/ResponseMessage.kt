package com.ptithcm.thuan6420.yam.data.dto

data class ResponseMessage<T>(val status: Boolean?, val message: String?, val data: T?)

