package com.ptithcm.thuan6420.yam.util

object Constants {
    const val DEFAULT_INTERVAL = 500
    const val REGEX_PHONE_NUMBER = "^[0]{1}\\d{9}$"
    const val REGEX_CODE_JOIN_IN = "^[^a-z]{6,20}$"
    const val BASE_URL = "https://ket-noi-khach-du-lich-ptithcm.herokuapp.com/"
    const val BASE_URL_MAP = "https://maps.googleapis.com/maps/api/directions/json"
    const val MODE_DIRECTION = "driving"
    const val DESTINATION = "DESTINATION"
    const val ORIGIN = "ORIGIN"
    const val REFRESH_TOKEN_WORKER = "REFRESH_TOKEN_WORKER"
    const val NOTIFICATIONS_WORKER = "NOTIFICATIONS_WORKER"
    const val CHOOSE_TIME = "Choose Time"
    const val CHOOSE_DATE = "Choose Date"

    const val MESSAGE_ERROR_LOGIN = "Login Failed. Please try again"
    const val MESSAGE_FAILED_LOGIN = "Wrong email or password"
    const val MESSAGE_ERROR_LOADING = "Loading failed"
    const val MESSAGE_SUCCESS_LOGIN = "Login Success"
    const val MESSAGE_SUCCESS_REGISTER = "Successfully registered"
    const val MESSAGE_FAILED_REGISTER = "Register failed"
    const val MESSAGE_EMPTY_EMAIL = "Email not null"
    const val MESSAGE_EMPTY_OTP = "OTP not null"
    const val MESSAGE_INVALID_OTP = "Invalid OTP"
    const val MESSAGE_NOT_EXIST_EMAIL = "Email not existed"
    const val MESSAGE_EMPTY_PASSWORD = "Password not null"
    const val MESSAGE_EMPTY_FULL_NAME = "Full name not null"
    const val MESSAGE_EMPTY_PHONE_NUMBER = "Phone number not null"
    const val MESSAGE_EMPTY_ADDRESS = "Address not null"
    const val MESSAGE_INVALID_EMAIL = "Invalid email address"
    const val MESSAGE_INVALID_PASSWORD = "Invalid password"
    const val MESSAGE_INVALID_PHONE_NUMBER = "Invalid phone number"
    const val MESSAGE_EXISTS_EMAIL = "Exists email address"
    const val MESSAGE_LOADING = "Loading ..."
    const val MESSAGE_SENT = "Sent"
    const val MESSAGE_RETRY = "Retry"
    const val MESSAGE_ERROR_VALID = "Dữ liệu nhập vào không hợp lệ"
    const val MESSAGE_DEFAULT_ERROR = "Error Occurred!"
    const val MESSAGE_DELETE_ROOM = "Do you want to delete this room ?"
    const val MESSAGE_DELETE_STATION = "Do you want to delete this station ?"

    const val APP_SHARED_PREFERENCES = "APP_SHARED_PREFERENCES"
    const val PREF_USER = "PREF_USER"
    const val YOUR_LOCATION = "your current location"
    const val TOKEN = "TOKEN"
    const val OTP = "OTP"
    const val ROOM = "ROOM"
    const val ADDRESS = "ADDRESS"
    const val STATION = "STATION"
    const val NOTIFICATION = "NOTIFICATION"
    const val REQUEST_LOCATION_PERMISSION = 33
    const val CHANNEL_ID = "CHANNEL_ID"

    val reasonPhraseCode = mapOf(
        Pair(100, "Continue"),
        Pair(101, "Switching Protocols"),
        Pair(102, "Processing"),
        Pair(103, "Early Hints"),
        Pair(200, "OK"),
        Pair(201, "Created"),
        Pair(202, "Accepted"),
        Pair(203, "Non-Authoritative Information"),
        Pair(204, "No Content"),
        Pair(205, "Reset Content"),
        Pair(206, "Partial Content"),
        Pair(207, "Multi-Status"),
        Pair(208, "Already Reported"),
        Pair(226, "IM Used"),
        Pair(300, "Multiple Choices"),
        Pair(301, "Moved Permanently"),
        Pair(302, "Found"),
        Pair(303, "See Other"),
        Pair(304, "Not Modified"),
        Pair(305, "Use Proxy"),
        Pair(307, "Temporary Redirect"),
        Pair(308, "Permanent Redirect"),
        Pair(400, "Bad Request"),
        Pair(401, "Unauthorized"),
        Pair(402, "Payment Required"),
        Pair(403, "Forbidden"),
        Pair(404, "Not Found"),
        Pair(405, "Method Not Allowed"),
        Pair(406, "Not Acceptable"),
        Pair(407, "Proxy Authentication Required"),
        Pair(408, "Request Timeout"),
        Pair(409, "Conflict"),
        Pair(410, "Gone"),
        Pair(411, "Length Required"),
        Pair(412, "Precondition Failed"),
        Pair(413, "Payload Too Large"),
        Pair(414, "URI Too Long"),
        Pair(415, "Unsupported Media Type"),
        Pair(416, "Range Not Satisfiable"),
        Pair(417, "Expectation Failed"),
        Pair(418, "I'm a Teapot"),
        Pair(421, "Misdirected Request"),
        Pair(422, "Unprocessable Entity"),
        Pair(423, "Locked"),
        Pair(424, "Failed Dependency"),
        Pair(425, "Too Early"),
        Pair(426, "Upgrade Required"),
        Pair(428, "Precondition Required"),
        Pair(429, "Too Many Requests"),
        Pair(431, "Request Header Fields Too Large"),
        Pair(451, "Unavailable For Legal Reasons"),
        Pair(500, "Internal Server Error"),
        Pair(501, "Not Implemented"),
        Pair(502, "Bad Gateway"),
        Pair(503, "Service Unavailable"),
        Pair(504, "Gateway Timeout"),
        Pair(505, "HTTP Version Not Supported"),
        Pair(506, "Variant Also Negotiates"),
        Pair(507, "Insufficient Storage"),
        Pair(508, "Loop Detected"),
        Pair(509, "Bandwidth Limit Exceeded"),
        Pair(510, "Not Extended"),
        Pair(511, "Network Authentication Required")
    ).withDefault {
        "There are something wrong ! "
    }
}