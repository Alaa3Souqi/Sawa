package com.aspire.sawa.unitls

data class ScreenState(val status: Status, val message: String?) {
    companion object {
        fun success(): ScreenState {
            return ScreenState(Status.SUCCESS, null)
        }

        fun error(msg: String): ScreenState {
            return ScreenState(Status.ERROR, msg)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
}
