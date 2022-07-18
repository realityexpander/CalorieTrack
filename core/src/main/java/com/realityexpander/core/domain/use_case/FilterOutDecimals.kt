package com.realityexpander.core.domain.use_case

class FilterOutDecimals {

    operator fun invoke(input: String): String {
        return input.filter { it.isDigit() || it == '.' }

        //return input.replace("[^0-9]".toRegex(), "") // regex to remove all non-digits
    }
}