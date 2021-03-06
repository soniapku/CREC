    public void simpleMatchedQueryFromTopLevelFilter() throws Exception {

        client().admin().indices().prepareCreate("test").execute().actionGet();
        client().admin().cluster().prepareHealth().setWaitForEvents(Priority.LANGUID).setWaitForGreenStatus().execute().actionGet();

        client().prepareIndex("test", "type1", "1").setSource(jsonBuilder().startObject()
                .field("name", "test")
                .field("title", "title1")
                .endObject()).execute().actionGet();

        client().prepareIndex("test", "type1", "2").setSource(jsonBuilder().startObject()
                .field("name", "test")
                .endObject()).execute().actionGet();

        client().prepareIndex("test", "type1", "3").setSource(jsonBuilder().startObject()
                .field("name", "test")
                .endObject()).execute().actionGet();

        client().admin().indices().prepareRefresh().execute().actionGet();

        SearchResponse searchResponse = client().prepareSearch()
                .setQuery(matchAllQuery())
                .setFilter(orFilter(
                        termFilter("name", "test").filterName("name"),
                        termFilter("title", "title1").filterName("title")))
                .execute().actionGet();

        assertNoFailures(searchResponse);
        assertThat(searchResponse.getHits().totalHits(), equalTo(3l));

        for (SearchHit hit : searchResponse.getHits()) {
            if (hit.id().equals("1")) {
                assertThat(hit.matchedQueries().length, equalTo(2));
                assertThat(hit.matchedQueries(), hasItemInArray("name"));
                assertThat(hit.matchedQueries(), hasItemInArray("title"));
            } else if (hit.id().equals("2") || hit.id().equals("3")) {
                assertThat(hit.matchedQueries().length, equalTo(1));
                assertThat(hit.matchedQueries(), hasItemInArray("name"));
            } else {
                fail("Unexpected document returned with id " + hit.id());
            }
        }

        searchResponse = client().prepareSearch()
                .setQuery(matchAllQuery())
                .setFilter(queryFilter(boolQuery()
                        .should(termQuery("name", "test").queryName("name"))
                        .should(termQuery("title", "title1").queryName("title"))))
                .execute().actionGet();

        assertNoFailures(searchResponse);
        assertThat(searchResponse.getHits().totalHits(), equalTo(3l));

        for (SearchHit hit : searchResponse.getHits()) {
            if (hit.id().equals("1")) {
                assertThat(hit.matchedQueries().length, equalTo(2));
                assertThat(hit.matchedQueries(), hasItemInArray("name"));
                assertThat(hit.matchedQueries(), hasItemInArray("title"));
            } else if (hit.id().equals("2") || hit.id().equals("3")) {
                assertThat(hit.matchedQueries().length, equalTo(1));
                assertThat(hit.matchedQueries(), hasItemInArray("name"));
            } else {
                fail("Unexpected document returned with id " + hit.id());
            }
        }
    }
