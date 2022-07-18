package com.realityexpander.core.domain.use_case

class FilterOutDigits {

    operator fun invoke(input: String): String {
        return input.filter { it.isDigit() }

        //return input.replace("[^0-9]".toRegex(), "") // regex to remove all non-digits
    }
}