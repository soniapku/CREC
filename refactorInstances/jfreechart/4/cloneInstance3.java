    public void clearDomainAxes() {
        for (int i = 0; i < this.domainAxes.size(); i++) {
            CategoryAxis axis = (CategoryAxis) this.domainAxes.get(i);
            if (axis != null) {
                axis.removeChangeListener(this);
            }
        }
        this.domainAxes.clear();
        notifyListeners(new PlotChangeEvent(this));
    }
