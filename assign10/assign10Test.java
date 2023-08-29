package assign10;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A series of tests for the HashTable Class.
 * @author Joseph Porta
 *
 */
class assign10Test {
	BinaryMaxHeap<Integer> heapEmpty;
	BinaryMaxHeap<String> heapEmptyCmp;
	BinaryMaxHeap<Integer> heapBuild;
	BinaryMaxHeap<String> heapBuildCmp;
	ArrayList<Integer> sList1;
	ArrayList<String> sList2;

	@BeforeEach
	void setup() {
		sList1 = new ArrayList<Integer>();
		sList1.add(2);
		sList1.add(4);
		sList1.add(6);
		sList2 = new ArrayList<String>();
		sList2.add("Two");
		sList2.add("Three");
		sList2.add("Four");
		sList2.add("Five");
		heapEmpty = new BinaryMaxHeap<Integer>();
		heapEmptyCmp = new BinaryMaxHeap<String>(new StringComparator());
		heapBuild = new BinaryMaxHeap<Integer>(sList1);
		heapBuildCmp = new BinaryMaxHeap<String>(sList2, new StringComparator());
	}
	
	@Test
	void testSize() {
		assertTrue(0 == heapEmpty.size());;
		assertTrue(0 == heapEmptyCmp.size());;
		assertTrue(3 == heapBuild.size());;
		assertTrue(4 == heapBuildCmp.size());;
	}
	
	@Test
	void testIsEmpty() {
		assertTrue(heapEmpty.isEmpty());;
		assertTrue(heapEmptyCmp.isEmpty());;
		assertTrue(!heapBuild.isEmpty());;
		assertTrue(!heapBuildCmp.isEmpty());;
	}
	
	@Test
	void testClear() {
		heapBuild.clear();
		heapBuildCmp.clear();
		assertTrue(0 == heapBuild.size());;
		assertTrue(0 == heapBuildCmp.size());;
		assertTrue(heapBuild.isEmpty());;
		assertTrue(heapBuildCmp.isEmpty());;
	}
	
	@Test
	void testToArray() {
		Object[] arr1Actual = {6, 2, 4};
		Object[] arr2Actual = {"Three", "Five", "Four", "Two"};
		assertArrayEquals(arr1Actual, heapBuild.toArray());
		assertArrayEquals(arr2Actual, heapBuildCmp.toArray());
	}

	@Test
	void testAdd() {
		Object[] arr1Actual = {8, 6, 4, 2, 5};
		heapBuild.add(8);
		heapBuild.add(5);
		assertArrayEquals(arr1Actual, heapBuild.toArray());
		assertEquals(5, heapBuild.size());
	}
	
	@Test
	void testPeek() {
		assertEquals(heapBuild.peek(), 6);
		heapBuild.add(8);
		assertEquals(heapBuild.peek(), 8);
		heapBuild.add(5);
		assertEquals(heapBuild.peek(), 8);
		assertThrows(NoSuchElementException.class,  () -> {heapEmpty.peek();});
	}
	
	@Test
	void testExtractMax() {
		assertEquals(heapBuild.extractMax(), 6);
		assertEquals(heapBuild.size(), 2);
		assertEquals(heapBuild.extractMax(), 4);
		assertEquals(heapBuild.size(), 1);
		assertEquals(heapBuild.extractMax(), 2);
		assertEquals(heapBuild.size(), 0);
		assertTrue(heapBuild.isEmpty());
	}
	
	@Test
	void TestFindKLargestHeap() {
		ArrayList<Integer> arrList1 = new ArrayList<Integer>();
		arrList1.add(6);
		assertTrue(arrList1.equals(FindKLargest.findKLargestHeap(sList1, 1)));
		arrList1.add(4);
		assertTrue(arrList1.equals(FindKLargest.findKLargestHeap(sList1, 2)));
		arrList1.add(2);
		assertTrue(arrList1.equals(FindKLargest.findKLargestHeap(sList1, 3)));
		assertThrows(IllegalArgumentException.class,  () -> {FindKLargest.findKLargestHeap(sList1, 4);});

		ArrayList<String> arrList2 = new ArrayList<String>();
		ArrayList<String> actualList2 = (ArrayList<String>) FindKLargest.findKLargestHeap(sList2, 1, new StringComparator());
		arrList2.add("Three");
		assertTrue(arrList2.equals(actualList2));
		arrList2.add("Five");
		assertTrue(arrList2.equals(FindKLargest.findKLargestHeap(sList2, 2, new StringComparator())));
		arrList2.add("Four");
		assertTrue(arrList2.equals(FindKLargest.findKLargestHeap(sList2, 3, new StringComparator())));
		arrList2.add("Two");
		assertTrue(arrList2.equals(FindKLargest.findKLargestHeap(sList2, 4, new StringComparator())));
		assertThrows(IllegalArgumentException.class,  () -> {FindKLargest.findKLargestHeap(sList2, 5, new StringComparator());});
	}

}
