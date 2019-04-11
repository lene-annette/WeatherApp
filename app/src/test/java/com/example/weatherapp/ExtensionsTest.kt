package com.example.weatherapp

import com.example.weatherapp.extensions.toDateString
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat

class ExtensionsTest {
    @Test
    fun `"longToDateString" returns valid value`(){
        assertEquals("19-10-2015", 1445275635000L.toDateString())
    }

    @Test
    fun `"longToDateString" with full format returns valid value`(){
        assertEquals("19. oktober 2015",1445275635000L.toDateString(DateFormat.FULL))
    }
}