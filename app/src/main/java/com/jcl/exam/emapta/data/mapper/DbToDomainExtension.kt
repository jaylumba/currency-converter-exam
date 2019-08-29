package com.jcl.exam.emapta.data.mapper

import com.jcl.exam.emapta.data.db.entities.AccountCurrency

fun AccountCurrency.toDomain() = com.jcl.exam.emapta.domain.Account(
        id = id,
        currency = desc,
        symbol = symbol,
        amount = amount
)