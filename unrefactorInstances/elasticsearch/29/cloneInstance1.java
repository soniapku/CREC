    public void testParseMatchSimpleIntegerValue() throws Exception {
        parser = YamlXContent.yamlXContent.createParser(
                "{ field: 10 }"
        );

        MatchParser matchParser = new MatchParser();
        MatchAssertion matchAssertion = matchParser.parse(new ClientYamlTestSuiteParseContext("api", "suite", parser));

        assertThat(matchAssertion, notNullValue());
        assertThat(matchAssertion.getField(), equalTo("field"));
        assertThat(matchAssertion.getExpectedValue(), instanceOf(Integer.class));
        assertThat((Integer) matchAssertion.getExpectedValue(), equalTo(10));
    }
