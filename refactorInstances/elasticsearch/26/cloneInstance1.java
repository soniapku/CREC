    public void noShardSize_string() throws Exception {
        client().admin().indices().prepareCreate("idx")
                .addMapping("type", "key", "type=string,index=not_analyzed")
                .execute().actionGet();

        indexData();

        SearchResponse response = client().prepareSearch("idx").setTypes("type")
                .setQuery(matchAllQuery())
                .addAggregation(terms("keys").field("key").size(3).order(Terms.Order.count(false)))
                .execute().actionGet();

        Terms  terms = response.getAggregations().get("keys");
        Collection<Terms.Bucket> buckets = terms.getBuckets();
        assertThat(buckets.size(), equalTo(3));
        Map<String, Long> expected = ImmutableMap.<String, Long>builder()
                .put("1", 8l)
                .put("3", 8l)
                .put("2", 4l)
                .build();
        for (Terms.Bucket bucket : buckets) {
            assertThat(bucket.getDocCount(), equalTo(expected.get(bucket.getKeyAsText().string())));
        }
    }
