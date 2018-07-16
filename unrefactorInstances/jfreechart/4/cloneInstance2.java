    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYShapeRenderer)) {
            return false;
        }
        XYShapeRenderer that = (XYShapeRenderer) obj;
        if (!this.paintScale.equals(that.paintScale)) {
            return false;
        }
        if (this.drawOutlines != that.drawOutlines) {
            return false;
        }
        if (this.useOutlinePaint != that.useOutlinePaint) {
            return false;
        }
        if (this.useFillPaint != that.useFillPaint) {
            return false;
        }
        if (this.guideLinesVisible != that.guideLinesVisible) {
            return false;
        }
        if (!this.guideLinePaint.equals(that.guideLinePaint)) {
            return false;
        }
        if (!this.guideLineStroke.equals(that.guideLineStroke)) {
            return false;
        }
        return super.equals(obj);
    }
