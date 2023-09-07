package com.example.vicoba.data.constants

sealed class Membership{
    enum class Request{
        Approved,Unrequested,Rejected,Pending
    }
}
