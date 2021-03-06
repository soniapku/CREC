    {
        doSearching("Kennedy");
//        QueryHighlightExtractor highlighter = new QueryHighlightExtractor(this, query, new StandardAnalyzer());
        Highlighter highlighter =new Highlighter(this,new QueryScorer(query));
        highlighter.setTextFragmenter(new SimpleFragmenter(40));

        for (int i = 0; i < hits.length(); i++)
        {
            String text = hits.doc(i).get(FIELD_NAME);
            TokenStream tokenStream=analyzer.tokenStream(FIELD_NAME,new StringReader(text));
            String result = highlighter.getBestFragment(tokenStream,text);
            System.out.println("\t" + result);
        }
        assertTrue("Failed to find correct number of highlights " + numHighlights + " found", numHighlights == 4);
    }
