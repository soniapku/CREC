    public void testAutoCreationPatternDisabled() {
        Settings settings = Settings.builder().put(AutoCreateIndex.AUTO_CREATE_INDEX_SETTING.getKey(), "-index*").build();
        AutoCreateIndex autoCreateIndex = new AutoCreateIndex(settings, new IndexNameExpressionResolver(settings));
        ClusterState clusterState = ClusterState.builder(new ClusterName("test")).metaData(MetaData.builder()).build();
        assertThat(autoCreateIndex.shouldAutoCreate("index" + randomAsciiOfLengthBetween(1, 5), clusterState), equalTo(false));
        //default is false when patterns are specified
        assertThat(autoCreateIndex.shouldAutoCreate("does_not_match" + randomAsciiOfLengthBetween(1, 5), clusterState), equalTo(false));
    }
