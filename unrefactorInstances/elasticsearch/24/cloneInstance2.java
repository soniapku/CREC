    public void testFastVectorHighlighter() throws Exception {
        try {
            client.admin().indices().prepareDelete("test").execute().actionGet();
        } catch (IndexMissingException e) {
            // its ok
        }
        client.admin().indices().prepareCreate("test").addMapping("type1", type1TermVectorMapping()).execute().actionGet();
        client.admin().cluster().prepareHealth("test").setWaitForGreenStatus().execute().actionGet();

        client.prepareIndex("test", "type1")
                .setSource("field1", "this is a test", "field2", "The quick brown fox jumps over the lazy dog")
                .setRefresh(true).execute().actionGet();

        logger.info("--> highlighting and searching on field1");
        SearchSourceBuilder source = searchSource()
                .query(termQuery("field1", "test"))
                .from(0).size(60).explain(true)
                .highlight(highlight().field("field1", 100, 0).order("score").preTags("<xxx>").postTags("</xxx>"));

        SearchResponse searchResponse = client.search(searchRequest("test").source(source).searchType(QUERY_THEN_FETCH).scroll(timeValueMinutes(10))).actionGet();
        assertThat("Failures " + Arrays.toString(searchResponse.shardFailures()), searchResponse.shardFailures().length, equalTo(0));
        assertThat(searchResponse.hits().totalHits(), equalTo(1l));

        // LUCENE 3.1 UPGRADE: Caused adding the space at the end...
        assertThat(searchResponse.hits().getAt(0).highlightFields().get("field1").fragments()[0], equalTo("this is a <xxx>test</xxx> "));

        logger.info("--> searching on _all, highlighting on field1");
        source = searchSource()
                .query(termQuery("_all", "test"))
                .from(0).size(60).explain(true)
                .highlight(highlight().field("field1", 100, 0).order("score").preTags("<xxx>").postTags("</xxx>"));

        searchResponse = client.search(searchRequest("test").source(source).searchType(QUERY_THEN_FETCH).scroll(timeValueMinutes(10))).actionGet();
        assertThat("Failures " + Arrays.toString(searchResponse.shardFailures()), searchResponse.shardFailures().length, equalTo(0));
        assertThat(searchResponse.hits().totalHits(), equalTo(1l));

        // LUCENE 3.1 UPGRADE: Caused adding the space at the end...
        assertThat(searchResponse.hits().getAt(0).highlightFields().get("field1").fragments()[0], equalTo("this is a <xxx>test</xxx> "));

        logger.info("--> searching on _all, highlighting on field2");
        source = searchSource()
                .query(termQuery("_all", "quick"))
                .from(0).size(60).explain(true)
                .highlight(highlight().field("field2", 100, 0).order("score").preTags("<xxx>").postTags("</xxx>"));

        searchResponse = client.search(searchRequest("test").source(source).searchType(QUERY_THEN_FETCH).scroll(timeValueMinutes(10))).actionGet();
        assertThat("Failures " + Arrays.toString(searchResponse.shardFailures()), searchResponse.shardFailures().length, equalTo(0));
        assertThat(searchResponse.hits().totalHits(), equalTo(1l));

        // LUCENE 3.1 UPGRADE: Caused adding the space at the end...
        assertThat(searchResponse.hits().getAt(0).highlightFields().get("field2").fragments()[0], equalTo("The <xxx>quick</xxx> brown fox jumps over the lazy dog "));

        logger.info("--> searching on _all, highlighting on field2");
        source = searchSource()
                .query(prefixQuery("_all", "qui"))
                .from(0).size(60).explain(true)
                .highlight(highlight().field("field2", 100, 0).order("score").preTags("<xxx>").postTags("</xxx>"));

        searchResponse = client.search(searchRequest("test").source(source).searchType(QUERY_THEN_FETCH).scroll(timeValueMinutes(10))).actionGet();
        assertThat("Failures " + Arrays.toString(searchResponse.shardFailures()), searchResponse.shardFailures().length, equalTo(0));
        assertThat(searchResponse.hits().totalHits(), equalTo(1l));

        // LUCENE 3.1 UPGRADE: Caused adding the space at the end...
        assertThat(searchResponse.hits().getAt(0).highlightFields().get("field2").fragments()[0], equalTo("The <xxx>quick</xxx> brown fox jumps over the lazy dog "));
    }
