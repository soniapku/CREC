    public void noShardSize_double() throws Exception {

        client().admin().indices().prepareCreate("idx")
                .addMapping("type", "key", "type=double")
                .execute().actionGet();

        indexData();

        SearchResponse response = client().prepareSearch("idx").setTypes("type")
                .setQuery(matchAllQuery())
                .addFacet(termsFacet("keys").field("key").size(3).order(TermsFacet.ComparatorType.COUNT))
                .execute().actionGet();

        Facets facets = response.getFacets();
        TermsFacet terms = facets.facet("keys");
        List<? extends TermsFacet.Entry> entries = terms.getEntries();
        assertThat(entries.size(), equalTo(3));
        Map<Integer, Integer> expected = ImmutableMap.<Integer, Integer>builder()
                .put(1, 8)
                .put(3, 8)
                .put(2, 4)
                .build();
        for (TermsFacet.Entry entry : entries) {
            assertThat(entry.getCount(), equalTo(expected.get(entry.getTermAsNumber().intValue())));
        }
    }
