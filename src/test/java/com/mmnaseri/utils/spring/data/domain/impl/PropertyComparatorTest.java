package com.mmnaseri.utils.spring.data.domain.impl;

import com.mmnaseri.utils.spring.data.error.InvalidArgumentException;
import com.mmnaseri.utils.spring.data.query.NullHandling;
import com.mmnaseri.utils.spring.data.query.SortDirection;
import com.mmnaseri.utils.spring.data.query.impl.ImmutableOrder;
import com.mmnaseri.utils.spring.data.sample.models.*;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (4/11/16)
 */
public class PropertyComparatorTest {

    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testWhenFirstValueDoesNotExist() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "address.city", NullHandling.DEFAULT));
        comparator.compare(new Address(), new Person());
    }

    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testWhenSecondValueDoesNotExist() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "address.city", NullHandling.DEFAULT));
        comparator.compare(new Person(), new Address());
    }

    @Test
    public void testNullFirst() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "address.city", NullHandling.NULLS_FIRST));
        assertThat(comparator.compare(new Person().setAddress(new Address().setCity("A")), new Person()), is(1));
        assertThat(comparator.compare(new Person(), new Person().setAddress(new Address().setCity("A"))), is(-1));
    }

    @Test
    public void testNullLast() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "address.city", NullHandling.NULLS_LAST));
        assertThat(comparator.compare(new Person().setAddress(new Address().setCity("A")), new Person()), is(-1));
        assertThat(comparator.compare(new Person(), new Person().setAddress(new Address().setCity("A"))), is(1));
    }

    @Test
    public void testBothAreNull() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "address.city", NullHandling.NULLS_LAST));
        assertThat(comparator.compare(new Person(), new Person()), is(0));
    }

    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testWhenValuesAreNotComparable() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "address", NullHandling.NULLS_LAST));
        comparator.compare(new Person().setAddress(new Address()), new Person().setAddress(new Address()));
    }

    @Test
    public void testWhenSecondIsSubTypeOfFirst() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "addressZip", NullHandling.NULLS_FIRST));
        final int comparison = comparator.compare(new Person().setAddressZip(new Zip().setPrefix("a")), new Person().setAddressZip(new ChildZip().setPrefix("b")));
        assertThat(comparison, is(-1));
    }

    @Test
    public void testWhenFirstIsSubTypeOfSecond() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "addressZip", NullHandling.NULLS_FIRST));
        final int comparison = comparator.compare(new Person().setAddressZip(new ChildZip().setPrefix("b")), new Person().setAddressZip(new Zip().setPrefix("a")));
        assertThat(comparison, is(1));
    }

    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testWhenValuesAreNotOfTheSameType() throws Exception {
        final PropertyComparator comparator = new PropertyComparator(new ImmutableOrder(SortDirection.ASCENDING, "addressZip", NullHandling.NULLS_FIRST));
        comparator.compare(new Person().setAddressZip(new ChildZip().setPrefix("b")), new Person().setAddressZip(new OtherChildZip().setPrefix("a")));
    }

}