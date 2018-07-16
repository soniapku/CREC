	public static final void sort(int[] tab, int lo0, int hi0, int[] result) {
		int lo = lo0;
		int hi = hi0;
		int mid;
		if (hi0 > lo0) {
			/* Arbitrarily establishing partition element as the midpoint of
			  * the array.
			  */
			mid = tab[lo0 + (hi0 - lo0) / 2];
			// loop through the array until indices cross
			while (lo <= hi) {
				/* find the first element that is greater than or equal to 
				 * the partition element starting from the left Index.
				 */
				while ((lo < hi0) && (tab[lo] < mid))
					++lo;
				/* find an element that is smaller than or equal to 
				 * the partition element starting from the right Index.
				 */
				while ((hi > lo0) && (tab[hi] > mid))
					--hi;
				// if the indexes have not crossed, swap
				if (lo <= hi) {
					swap(tab, lo, hi, result);
					++lo;
					--hi;
				}
			}
			/* If the right index has not reached the left side of array
			  * must now sort the left partition.
			  */
			if (lo0 < hi)
				sort(tab, lo0, hi, result);
			/* If the left index has not reached the right side of array
			  * must now sort the right partition.
			  */
			if (lo < hi0)
				sort(tab, lo, hi0, result);
		}
	}
