  public final LinkvarContext linkvar() throws RecognitionException {
    LinkvarContext _localctx = new LinkvarContext(_ctx, getState());
    enterRule(_localctx, 40, RULE_linkvar);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(315);
      identifier();
      setState(318);
      switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
      case 1:
        {
        setState(316);
        linkdot();
        }
        break;
      case 2:
        {
        setState(317);
        linkbrace();
        }
        break;
      }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }
