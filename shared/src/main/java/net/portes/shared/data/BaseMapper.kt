package net.portes.shared.data

/**
 * @author amadeus.portes
 */
interface BaseMapper<in IN, out OUT> {

    fun mapFrom(from: IN): OUT

}

interface BaseDefaultMapper<IN> {

    fun toDefault(): IN

}