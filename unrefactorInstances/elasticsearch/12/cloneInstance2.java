  public final LinkstringContext linkstring() throws RecognitionException {
    LinkstringContext _localctx = new LinkstringContext(_ctx, getState());
    enterRule(_localctx, 46, RULE_linkstring);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(344);
      match(STRING);
      setState(347);
      switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
      case 1:
        {
        setState(345);
        linkdot();
        }
        break;
      case 2:
        {
        setState(346);
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
