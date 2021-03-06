    @BeforeMethod public void createIndex() {
        logger.info("creating index [test]");
        node.client().admin().indices().create(createIndexRequest("test").settings(settingsBuilder().put("index.numberOfReplicas", 0))).actionGet();
        logger.info("Running Cluster Health");
        ClusterHealthResponse clusterHealth = node.client().admin().cluster().health(clusterHealth().waitForGreenStatus()).actionGet();
        logger.info("Done Cluster Health, status " + clusterHealth.status());
        assertThat(clusterHealth.timedOut(), equalTo(false));
        assertThat(clusterHealth.status(), equalTo(ClusterHealthStatus.GREEN));
    }
