    public Number getEndValue(int series, int category) {
        if ((series < 0) || (series >= getSeriesCount())) {
            throw new IllegalArgumentException(
                "DefaultIntervalCategoryDataset.getValue(): "
                + "series index out of range.");
        }

        if ((category < 0) || (category >= getCategoryCount())) {
            throw new IllegalArgumentException(
                "DefaultIntervalCategoryDataset.getValue(): "
                + "category index out of range.");
        }

        return this.endData[series][category];
    }
