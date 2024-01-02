package com.example.crystalcraze.helper

import com.example.crystalcraze.model.TopUpModel

object TopUpListHelper {
    fun getPointsItems(): Array<TopUpModel> {
        return arrayOf(
            TopUpModel(
                title = "125 Points",
                price = 14543
            ),
            TopUpModel(
                title = "250 Points",
                price = 29086
            ),
            TopUpModel(
                title = "500 Points",
                price = 58172
            ),
            TopUpModel(
                title = "1000 Points",
                price = 116345
            ),
            TopUpModel(
                title = "2000 Points",
                price = 232690
            ),
            TopUpModel(
                title = "5000 Points",
                price = 465381
            ),
            TopUpModel(
                title = "10000 Points",
                price = 930762
            ),
            TopUpModel(
                title = "20000 Points",
                price = 1861524
            )
        )
    }

    fun getDiamondsItems(): Array<TopUpModel> {
        return arrayOf(
            TopUpModel(
                title = "100 Diamonds",
                price = 29287
            ),
            TopUpModel(
                title = "200 Diamonds",
                price = 58574
            ),
            TopUpModel(
                title = "500 Diamonds",
                price = 117549
            ),
            TopUpModel(
                title = "1000 Diamonds",
                price = 235098
            ),
            TopUpModel(
                title = "2000 Diamonds",
                price = 470196
            ),
            TopUpModel(
                title = "5000 Diamonds",
                price = 940392
            ),
            TopUpModel(
                title = "10000 Diamonds",
                price = 1880794
            ),
            TopUpModel(
                title = "20000 Diamonds",
                price = 3761588
            )
        )
    }

    fun getUCGlobalItems(): Array<TopUpModel> {
        return arrayOf(
            TopUpModel(
                title = "60 UC Global",
                price = 16428
            ),
            TopUpModel(
                title = "120 UC Global",
                price = 32856
            ),
            TopUpModel(
                title = "240 UC Global",
                price = 65712
            ),
            TopUpModel(
                title = "480 UC Global",
                price = 130424
            ),
            TopUpModel(
                title = "960 UC Global",
                price = 260848
            ),
            TopUpModel(
                title = "1920 UC Global",
                price = 521696
            ),
            TopUpModel(
                title = "3840 UC Global",
                price = 1043488
            ),
            TopUpModel(
                title = "7680 UC Global",
                price = 2086976
            ),
            TopUpModel(
                title = "15360 UC Global",
                price = 4173952
            )
        )
    }

    fun getRPItems(): Array<TopUpModel> {
        return arrayOf(
            TopUpModel(
                title = "100 RP",
                price = 15853
            ),
            TopUpModel(
                title = "500 RP",
                price = 79265
            ),
            TopUpModel(
                title = "1000 RP",
                price = 158530
            ),
            TopUpModel(
                title = "2000 RP",
                price = 317060
            ),
            TopUpModel(
                title = "5000 RP",
                price = 634121
            ),
            TopUpModel(
                title = "10000 RP",
                price = 1268123
            )
        )
    }
}