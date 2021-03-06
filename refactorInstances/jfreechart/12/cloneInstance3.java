                                                    boolean urls) {

        if (orientation == null) {
            throw new IllegalArgumentException("Null 'orientation' argument.");
        }
        CategoryAxis categoryAxis = new CategoryAxis3D(categoryAxisLabel);
        ValueAxis valueAxis = new NumberAxis3D(valueAxisLabel);

        // create the renderer...
        CategoryItemRenderer renderer = new StackedBarRenderer3D();
        if (tooltips) {
            renderer.setBaseToolTipGenerator(
                    new StandardCategoryToolTipGenerator());
        }
        if (urls) {
            renderer.setBaseItemURLGenerator(
                    new StandardCategoryURLGenerator());
        }

        // create the plot...
        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(orientation);
        if (orientation == PlotOrientation.HORIZONTAL) {
            // change rendering order to ensure that bar overlapping is the
            // right way around
            plot.setColumnRenderingOrder(SortOrder.DESCENDING);
        }

        // create the chart...
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;

    }
