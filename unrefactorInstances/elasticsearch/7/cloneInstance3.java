    public void testQueryContextParsingMixed() throws Exception {
        XContentBuilder builder = jsonBuilder().startArray()
                .startObject()
                .field("context", "context1")
                .field("boost", 2)
                .field("prefix", true)
                .endObject()
                .value("context2")
                .endArray();
        XContentParser parser = XContentFactory.xContent(XContentType.JSON).createParser(builder.bytes());
        CategoryContextMapping mapping = ContextBuilder.category("cat").build();
        List<ContextMapping.InternalQueryContext> internalQueryContexts = mapping.parseQueryContext(createParseContext(parser));
        assertThat(internalQueryContexts.size(), equalTo(2));
        assertThat(internalQueryContexts.get(0).context, equalTo("context1"));
        assertThat(internalQueryContexts.get(0).boost, equalTo(2));
        assertThat(internalQueryContexts.get(0).isPrefix, equalTo(true));
        assertThat(internalQueryContexts.get(1).context, equalTo("context2"));
        assertThat(internalQueryContexts.get(1).boost, equalTo(1));
        assertThat(internalQueryContexts.get(1).isPrefix, equalTo(false));
    }
