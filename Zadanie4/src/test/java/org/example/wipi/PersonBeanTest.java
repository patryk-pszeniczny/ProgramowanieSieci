package org.example.wipi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonBeanTest {
    @Test
    void shouldExposeDefaultAgeAndGenderAndAllowModification() {
        PersonBean personBean = new PersonBean();

        assertEquals(21, personBean.getWiek());
        assertEquals("mezczyzna", personBean.getPlec());

        personBean.setWiek(24);
        personBean.setPlec("kobieta");

        assertEquals(24, personBean.getWiek());
        assertEquals("kobieta", personBean.getPlec());
    }
}
