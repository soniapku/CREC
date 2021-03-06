    public void testUpdateRequestWithScriptAndShouldUpsertDoc() throws Exception {
        createIndex();
        ClusterHealthResponse clusterHealth = client().admin().cluster().prepareHealth().setWaitForEvents(Priority.LANGUID).setWaitForGreenStatus().execute().actionGet();
        assertThat(clusterHealth.isTimedOut(), equalTo(false));
        assertThat(clusterHealth.getStatus(), equalTo(ClusterHealthStatus.GREEN));
        try {
            client().prepareUpdate("test", "type1", "1")
                    .setScript("ctx._source.field += 1")
                    .setDocAsUpsert(true)
                    .execute().actionGet();
            fail("Should have thrown ActionRequestValidationException");
        } catch (ActionRequestValidationException e) {
            assertThat(e.validationErrors().size(), equalTo(1));
            assertThat(e.validationErrors().get(0), containsString("can't say to upsert doc without providing doc"));
            assertThat(e.getMessage(), containsString("can't say to upsert doc without providing doc"));
        }
    }
